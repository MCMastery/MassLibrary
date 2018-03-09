package com.dgrissom.masslibrary.math.algebra;

public class SubtractionNode extends Node {
    @Override
    public String getToken() {
        return "-";
    }
    @Override
    public int getArity() {
        return 2;
    }
    @Override
    public int getPrecedence() {
        return 0;
    }
    @Override
    public double evaluate(double... operands) {
        return operands[0] - operands[1];
    }
}
