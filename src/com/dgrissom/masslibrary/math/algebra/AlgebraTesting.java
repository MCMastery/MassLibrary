package com.dgrissom.masslibrary.math.algebra;

public class AlgebraTesting {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser("1+2*3");
        Tree tree = parser.parse();
        System.out.println(tree.evaluate());
    }
}
