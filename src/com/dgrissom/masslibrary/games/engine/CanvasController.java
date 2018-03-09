package com.dgrissom.masslibrary.games.engine;

import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;

import java.net.URL;
import java.util.ResourceBundle;

public class CanvasController implements Initializable {
    @FXML
    private javafx.scene.canvas.Canvas fxCanvas;
    private Canvas canvas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.fxCanvas.setFocusTraversable(true);
        this.canvas = new Canvas(this.fxCanvas);
        GameEngine.getInstance().setCanvasController(this);
    }

    public javafx.scene.canvas.Canvas getFXCanvas() {
        return this.fxCanvas;
    }
    public Canvas getCanvas() {
        return this.canvas;
    }
    public void bindDimensions(Scene scene) {
        this.fxCanvas.widthProperty().bind(scene.widthProperty());
        this.fxCanvas.heightProperty().bind(scene.heightProperty());
    }

    public void render() {
        this.canvas.fill(new Rectangle2d(0, 0, this.fxCanvas.getWidth(), this.fxCanvas.getHeight()), RGBColor.BLACK);
        for (GameObject object : GameEngine.getInstance().getObjects())
            object.render(this.canvas);
    }
}
