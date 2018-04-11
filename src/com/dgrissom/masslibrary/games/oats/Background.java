package com.dgrissom.masslibrary.games.oats;

import com.dgrissom.masslibrary.games.GameActor;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class Background implements GameActor {
    private static Image SPRITE;

    static {
        try {
            SPRITE = new Image(new FileInputStream(new File("res/games/oats/background-small.png")));
        } catch (IOException e) {
            SPRITE = null;
            e.printStackTrace();
        }
    }

    @Override
    public int getLayer() {
        return -10000;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Canvas canvas) {
        canvas.draw(SPRITE, new Point2d(-SPRITE.getWidth() / 2, -SPRITE.getHeight() / 2));
    }
}
