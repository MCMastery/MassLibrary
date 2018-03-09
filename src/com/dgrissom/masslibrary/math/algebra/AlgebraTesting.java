package com.dgrissom.masslibrary.math.algebra;

public class AlgebraTesting {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser("4+2*2");
        Tree tree = parser.parse();
        System.out.println(tree.evaluate());
    }
}
