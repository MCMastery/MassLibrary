package com.dgrissom.masslibrary.games;

import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.math.geom.r2.transform.Translate2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class GameRenderer implements Initializable {
    private static GameRenderer instance;

    @FXML
    private javafx.scene.canvas.Canvas fxCanvas;
    private Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fxCanvas.setFocusTraversable(true);
        this.canvas = new Canvas(this.fxCanvas);
        instance = this;
    }

    public javafx.scene.canvas.Canvas getFXCanvas() {
        return this.fxCanvas;
    }

    public void bindCanvasSize(Scene scene) {
        this.fxCanvas.widthProperty().bind(scene.widthProperty());
        this.fxCanvas.heightProperty().bind(scene.heightProperty());
    }

    public Translate2d centerScreenTranslation() {
        return new Translate2d(this.fxCanvas.getWidth() / 2, this.fxCanvas.getHeight() / 2);
    }

    public void render(GameStage stage) {
        Translate2d translate = centerScreenTranslation();
        this.canvas.fill(new Rectangle2d(this.fxCanvas.getWidth(), this.fxCanvas.getHeight()), RGBColor.BLACK);
        // translate so center is (0, 0)
        this.canvas.transform(translate);
        this.canvas.transform(stage.getCamera());
        for (GameActor actor : stage)
            actor.render(this.canvas);
        this.canvas.transform(stage.getCamera().inverse());
        this.canvas.transform(translate.inverse());
    }

    public static GameRenderer getInstance() {
        return instance;
    }
}
