package com.dgrissom.masslibrary.games.input;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.Vector2d;
import javafx.scene.input.MouseButton;

public interface MouseListener {
    void onMousePress(MouseButton button, Point2d position);
    void onMouseRelease(MouseButton button, Point2d position);
    void onMouseScroll(Vector2d scrollAmount, Point2d position);
}
