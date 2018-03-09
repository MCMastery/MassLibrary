package com.dgrissom.masslibrary.math.algebra;

// a node representing a constant value (a number)
// token is just value.toString
public class ConstantNode extends Node {
    private final double value;

    public ConstantNode(double value) {
        this.value = value;
    }

    @Override
    public String getToken() {
        return String.valueOf(this.value);
    }
    // this stuff doesn't affect anything
    @Override
    public int getArity() {
        return 0;
    }
    @Override
    public int getPrecedence() {
        return 0;
    }
    @Override
    public double evaluate(double... operands) {
        return this.value;
    }
}
