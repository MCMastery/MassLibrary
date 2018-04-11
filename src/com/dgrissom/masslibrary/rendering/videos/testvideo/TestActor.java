package com.dgrissom.masslibrary.rendering.videos.testvideo;

import com.dgrissom.masslibrary.Random;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import com.dgrissom.masslibrary.rendering.videos.VideoActor;

class TestActor implements VideoActor {
    private Rectangle2d bounds;
    private RGBColor color;

    public TestActor() {
        this.bounds = new Rectangle2d(0, 0, 100, 100);
        this.color = Random.rgb();
    }

    @Override
    public int getLayer() {
        return 0;
    }
    @Override
    public void render(Image image) {
        image.fill(this.bounds, this.color);
        this.bounds = this.bounds.translate(1, 0);
    }
}
