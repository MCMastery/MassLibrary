package com.dgrissom.masslibrary.math.algebra;

public interface AlgebraToken {
    String getToken();
    int getArity();
    int getPrecedence();
    double evaluate(double... operands);
}
