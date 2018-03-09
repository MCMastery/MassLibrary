package com.dgrissom.masslibrary.math.algebra;

public class MultiplicationNode extends Node {
    @Override
    public String getToken() {
        return "*";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public int getPrecedence() {
        return 1;
    }
    @Override
    public double evaluate(double... operands) {
        return operands[0] * operands[1];
    }
}
