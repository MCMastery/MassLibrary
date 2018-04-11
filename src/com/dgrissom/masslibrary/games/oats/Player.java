package com.dgrissom.masslibrary.games.oats;

import com.dgrissom.masslibrary.games.Camera;
import com.dgrissom.masslibrary.games.GameActor;
import com.dgrissom.masslibrary.games.GameEngine;
import com.dgrissom.masslibrary.games.input.Input;
import com.dgrissom.masslibrary.games.input.MouseListener;
import com.dgrissom.masslibrary.math.Matrix;
import com.dgrissom.masslibrary.math.geom.InvertibleCompositeTransform;
import com.dgrissom.masslibrary.math.geom.InvertibleTransform;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.Vector2d;
import com.dgrissom.masslibrary.math.geom.r2.transform.Scale2d;
import com.dgrissom.masslibrary.math.geom.r2.transform.Translate2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class Player implements GameActor, Camera, MouseListener {
    private static Image SPRITE;

    static {
        try {
            SPRITE = new Image(new FileInputStream(new File("res/games/oats/player-small.png")));
        } catch (IOException e) {
            SPRITE = null;
            e.printStackTrace();
        }
    }

    enum Direction {
        LEFT, RIGHT
    }

    private Point2d position;
    private Direction direction;

    public Player() {
        this.position = Point2d.origin();
        this.direction = Direction.RIGHT;
    }

    private Translate2d translation() {
        return new Translate2d(this.position.multiply(-1));
    }

    @Override
    public InvertibleTransform inverse() {
        return translation().inverse();
    }
    @Override
    public Matrix matrix() {
        return translation().matrix();
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public void update() {
        Vector2d movement = new Vector2d(0, 0);
        if (Input.keyDown(KeyCode.W))
            movement = movement.add(0, -1);
        if (Input.keyDown(KeyCode.A))
            movement = movement.add(-1, 0);
        if (Input.keyDown(KeyCode.S))
            movement = movement.add(0, 1);
        if (Input.keyDown(KeyCode.D))
            movement = movement.add(1, 0);
        movement = movement.normalize().multiply(4);
        this.position = this.position.add(movement).round();

        if (movement.getX() != 0)
            this.direction = (movement.getX() > 0) ? Direction.RIGHT : Direction.LEFT;
    }
    @Override
    public void render(Canvas canvas) {
        if (this.direction == Direction.LEFT) {
            InvertibleCompositeTransform flip = new InvertibleCompositeTransform(
                    translation(),
                    new Scale2d(-1, 1),
                    translation().inverse()
            );
            canvas.transform(flip);
            canvas.draw(SPRITE, this.position.subtract(SPRITE.getWidth() / 1.5, SPRITE.getHeight() / 1.5));
            canvas.transform(flip.inverse());
        } else
            canvas.draw(SPRITE, this.position.subtract(SPRITE.getWidth() / 1.5, SPRITE.getHeight() / 1.5));
    }

    @Override
    public void onMousePress(MouseButton button, Point2d position) {
//        Vector2d velocity = Vector2d.direction(this.position, position).multiply(15);
        Vector2d velocity = new Vector2d((this.direction == Direction.RIGHT) ? 1 : -1, 0).multiply(15);
        GameEngine.getInstance().getStage().add(new OatsBullet(this.position, velocity));
    }
    @Override
    public void onMouseRelease(MouseButton button, Point2d position) {

    }
    @Override
    public void onMouseScroll(Vector2d scrollAmount, Point2d position) {

    }
}
