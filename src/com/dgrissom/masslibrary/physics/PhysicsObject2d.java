package com.dgrissom.masslibrary.physics;

import com.dgrissom.masslibrary.math.geom.r2.Vector2d;

public abstract class PhysicsObject2d {
    private final PhysicsSystem2d system;
    private Vector2d velocity;
    // https://en.wikipedia.org/wiki/Coefficient_of_restitution
    private double coefficientOfRestitution;

    public PhysicsObject2d(PhysicsSystem2d system) {
        this.system = system;
        this.velocity = new Vector2d(0, 0);
        // no bounce
        this.coefficientOfRestitution = 0;
    }

    public PhysicsSystem2d getSystem() {
        return this.system;
    }
    public Vector2d getVelocity() {
        return this.velocity;
    }
    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }
    public double getCoefficientOfRestitution() {
        return this.coefficientOfRestitution;
    }
    public void setCoefficientOfRestitution(double coefficientOfRestitution) {
        this.coefficientOfRestitution = coefficientOfRestitution;
    }

    public void gravity() {
        if (this.velocity.getY() > this.system.getTerminalVelocity())
            this.velocity = this.velocity.setY(this.system.getTerminalVelocity());
        this.velocity = this.velocity.add(0, this.system.getGravity());
    }
    public void bounce() {
        setVelocity(getVelocity().multiply(1, -this.coefficientOfRestitution));
    }
}
