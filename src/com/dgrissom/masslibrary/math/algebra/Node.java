package com.dgrissom.masslibrary.math.algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Node implements AlgebraToken {
    private List<Node> children;

    public Node() {
        this.children = new ArrayList<>();
    }

    public List<Node> getChildren() {
        return this.children;
    }
    public void addChildren(Node... children) {
        addChildren(Arrays.asList(children));
    }
    public void addChildren(List<? extends Node> children) {
        this.children.addAll(children);
    }

    public double evaluate() {
        double[] operands = new double[this.children.size()];
        for (int i = 0; i < this.children.size(); i++)
            operands[i] = this.children.get(i).evaluate();
        return evaluate(operands);
    }

    @Override
    public String toString() {
        return getToken() + (this.children.size() > 0 ? String.valueOf(this.children) : "");
    }

    //todo cleaner/scalable way of doing this...
    public static Node lookupOperator(String token) {
        switch (token) {
            case "+":
                return new AdditionNode();
            case "-":
                return new SubtractionNode();
            case "*":
                return new MultiplicationNode();
            default:
                throw new IllegalArgumentException("unknown operator: " + token);
        }
    }
}
