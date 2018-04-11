package com.dgrissom.masslibrary.rendering.videos.particles;

import com.dgrissom.masslibrary.Random;
import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.math.geom.r2.Vector2d;
import com.dgrissom.masslibrary.math.geom.r2.polygon.Rectangle2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.Color;
import com.dgrissom.masslibrary.rendering.videos.VideoActor;

class Particle implements VideoActor {
    private Point2d position;
    private Color color;

    public Particle(Point2d position, Color color) {
        this.position = position;
        this.color = color;
    }

    public Point2d getPosition() {
        return this.position;
    }

    @Override
    public int getLayer() {
        return 0;
    }

    @Override
    public void render(Image image) {
        Vector2d velocity = new Vector2d(0, 0);
        int count = 0;
        for (Particle particle : ParticlesVideo.getParticles()) {
            if (particle == this)
                continue;
            Vector2d dir = Vector2d.direction(this.position, particle.getPosition());
            velocity = velocity.add(dir);
            count++;
        }
        if (count > 0)
            velocity = velocity.divide(count);
        this.position = this.position.add(velocity);
        image.draw(this.position, this.color);
    }

    public static Particle random(Rectangle2d bounds) {
        return new Particle(Random.point2d(bounds), Random.hsv().setV(1));
    }
}
