package com.dgrissom.masslibrary.physics;

public class PhysicsSystem2d {
    private double gravity, terminalVelocity;

    public PhysicsSystem2d() {
        this.gravity = 0.15;
        this.terminalVelocity = 10;
    }

    public double getGravity() {
        return this.gravity;
    }
    public void setGravity(double gravity) {
        this.gravity = gravity;
    }
    public double getTerminalVelocity() {
        return this.terminalVelocity;
    }
    public void setTerminalVelocity(double terminalVelocity) {
        this.terminalVelocity = terminalVelocity;
    }
}
