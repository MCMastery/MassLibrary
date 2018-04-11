package com.dgrissom.masslibrary.rendering.videos.bouncingball;

import com.dgrissom.masslibrary.math.geom.r2.LineSegment2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import com.dgrissom.masslibrary.rendering.videos.VideoActor;

class Floor implements VideoActor {
    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public void render(Image image) {
        image.draw(new LineSegment2d(0, BouncingBallVideo.FLOOR_Y, 1366, BouncingBallVideo.FLOOR_Y), RGBColor.WHITE);
    }
}
