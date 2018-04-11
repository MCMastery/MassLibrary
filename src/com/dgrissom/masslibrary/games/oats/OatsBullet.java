package com.dgrissom.masslibrary.games.oats;

import com.dgrissom.masslibrary.games.GameActor;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.Vector2d;
import com.dgrissom.masslibrary.rendering.Canvas;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

class OatsBullet implements GameActor {
    private static Image SPRITE;

    static {
        try {
            SPRITE = new Image(new FileInputStream(new File("res/games/oats/oats-small.png")));
        } catch (IOException e) {
            SPRITE = null;
            e.printStackTrace();
        }
    }

    private final Vector2d velocity;
    private Point2d position;

    public OatsBullet(Point2d start, Vector2d velocity) {
        this.velocity = velocity;
        this.position = start;
    }

    @Override
    public int getLayer() {
        return -100;
    }
    @Override
    public void update() {
        this.position = this.position.add(this.velocity);
    }
    @Override
    public void render(Canvas canvas) {
        canvas.draw(SPRITE, this.position.subtract(SPRITE.getWidth() / 2, SPRITE.getHeight() / 2));
    }
}
