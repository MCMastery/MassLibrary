package com.dgrissom.masslibrary.games.input;

import com.dgrissom.masslibrary.games.GameActor;
import com.dgrissom.masslibrary.games.GameEngine;
import com.dgrissom.masslibrary.games.GameRenderer;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.Vector2d;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Input {
    private static Set<KeyCode> keysDown;
    private static Set<MouseButton> buttonsDown;
    private static ControlsConfig controls;
    private static Point2d mousePosition;

    private Input() {}

    public static boolean keyDown(KeyCode key) {
        return keysDown.contains(key);
    }
    public static boolean keyUp(KeyCode key) {
        return !keysDown.contains(key);
    }

    public static boolean controlDown(Control control) {
        return keysDown.contains(controls.getControl(control));
    }
    public static boolean controlUp(Control control) {
        return !keysDown.contains(controls.getControl(control));
    }

    public static boolean buttonDown(MouseButton button) {
        return buttonsDown.contains(button);
    }
    public static boolean buttonUp(MouseButton button) {
        return !buttonsDown.contains(button);
    }

    public static Point2d getMousePosition() {
        return mousePosition.transform(GameRenderer.getInstance().centerScreenTranslation().inverse())
                .transform(GameEngine.getInstance().getStage().getCamera().inverse());
    }
    public static Point2d getMouseScreenPosition() {
        return mousePosition;
    }

    public static ControlsConfig getControls() {
        return controls;
    }
    public static void setControls(ControlsConfig controls) {
        Input.controls = controls;
    }



    public static void init() {
        keysDown = new HashSet<>();
        buttonsDown = new HashSet<>();
        controls = new ControlsConfig();
        mousePosition = Point2d.origin();
        Canvas canvas = GameRenderer.getInstance().getFXCanvas();
        bindKeyEvents(canvas);
        bindMouseEvents(canvas);
    }

    private static void bindKeyEvents(Canvas canvas) {
        canvas.setOnKeyPressed(event -> {
            if (keysDown.contains(event.getCode()))
                return;
            keysDown.add(event.getCode());
            for (GameActor actor : new ArrayList<>(GameEngine.getInstance().getStage().getActors())) {
                if (actor instanceof KeyListener)
                    ((KeyListener) actor).onKeyPress(event.getCode());
                if (actor instanceof ControlListener) {
                    ControlListener cl = (ControlListener) actor;
                    for (Control control : controls.getControls().keySet())
                        if (controls.getControls().get(control) == event.getCode())
                            cl.onControlPress(control);
                }
            }
        });
        canvas.setOnKeyReleased(event -> {
            if (!keysDown.contains(event.getCode()))
                return;
            keysDown.remove(event.getCode());
            for (GameActor actor : new ArrayList<>(GameEngine.getInstance().getStage().getActors())) {
                if (actor instanceof KeyListener)
                    ((KeyListener) actor).onKeyRelease(event.getCode());
                if (actor instanceof ControlListener) {
                    ControlListener cl = (ControlListener) actor;
                    for (Control control : controls.getControls().keySet())
                        if (controls.getControls().get(control) == event.getCode())
                            cl.onControlRelease(control);
                }
            }
        });
    }

    private static void bindMouseEvents(Canvas canvas) {
        canvas.setOnMouseMoved(event -> mousePosition = new Point2d(event.getX(), event.getY()));
        canvas.setOnMouseDragged(event -> mousePosition = new Point2d(event.getX(), event.getY()));

        canvas.setOnMousePressed(event -> {
            buttonsDown.add(event.getButton());
            Point2d mousePos = getMousePosition();
            for (GameActor actor : new ArrayList<>(GameEngine.getInstance().getStage().getActors()))
                if (actor instanceof MouseListener)
                    ((MouseListener) actor).onMousePress(event.getButton(), mousePos);
        });
        canvas.setOnMouseReleased(event -> {
            buttonsDown.remove(event.getButton());
            Point2d mousePos = getMousePosition();
            for (GameActor actor : new ArrayList<>(GameEngine.getInstance().getStage().getActors()))
                if (actor instanceof MouseListener)
                    ((MouseListener) actor).onMouseRelease(event.getButton(), mousePos);
        });

        canvas.setOnScroll(event -> {
            Vector2d scrollAmount = new Vector2d(event.getDeltaX(), event.getDeltaY());
            Point2d mousePos = getMousePosition();
            for (GameActor actor : new ArrayList<>(GameEngine.getInstance().getStage().getActors()))
                if (actor instanceof MouseListener)
                    ((MouseListener) actor).onMouseScroll(scrollAmount, mousePos);
        });
    }
}
