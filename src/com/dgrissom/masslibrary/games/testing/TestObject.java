package com.dgrissom.masslibrary.games.testing;

import com.dgrissom.masslibrary.games.engine.GameObject;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import com.dgrissom.masslibrary.rendering.color.RGBColor;

public class TestObject implements GameObject {
    private Rectangle2d bounds;

    public TestObject() {
        this.bounds = new Rectangle2d(0, 0, 10, 10);
    }

    @Override
    public int getLayer() {
        return 0;
    }
    @Override
    public void update() {
        this.bounds = this.bounds.translate(1, 1);
    }
    @Override
    public void render(Canvas canvas) {
        canvas.fill(this.bounds, RGBColor.BLUE);
    }
}
