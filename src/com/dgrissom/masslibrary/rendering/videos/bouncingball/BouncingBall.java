package com.dgrissom.masslibrary.rendering.videos.bouncingball;

import com.dgrissom.masslibrary.math.geom.r2.Point2d;
import com.dgrissom.masslibrary.physics.PhysicsObject2d;
import com.dgrissom.masslibrary.rendering.Image;
import com.dgrissom.masslibrary.rendering.color.RGBColor;
import com.dgrissom.masslibrary.rendering.videos.Actor;

public class BouncingBall extends PhysicsObject2d implements Actor {
    private Point2d center;
    private double width, height;
    private RGBColor color;

    public BouncingBall() {
        super(BouncingBallVideo.SYSTEM);
        this.center = new Point2d(400, 0);
        this.width = 50;
        this.height = 50;
        this.color = RGBColor.BLUE;
        setCoefficientOfRestitution(0.85);
    }

    @Override
    public int getLayer() {
        return 0;
    }
    @Override
    public void render(Image image) {
        image.setColor(this.color);
        image.getGraphics().fillOval((int) Math.round(this.center.getX() - this.width / 2),
                (int) Math.round(this.center.getY() - this.height / 2), (int) Math.round(this.width), (int) Math.round(this.height));

        gravity();
        this.center = this.center.add(getVelocity());

        if (this.center.getY() + this.height / 2 >= BouncingBallVideo.FLOOR_Y) {
            this.center = this.center.setY(BouncingBallVideo.FLOOR_Y - this.height / 2);
            bounce();
        }
    }
}
