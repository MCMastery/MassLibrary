package com.dgrissom.masslibrary.math.algebra;

public class Tree {
    private Node topLevelNode;

    public Tree(Node topLevelNode) {
        this.topLevelNode = topLevelNode;
    }

    public Node getTopLevelNode() {
        return this.topLevelNode;
    }

    public double evaluate() {
        return this.topLevelNode.evaluate();
    }

    @Override
    public String toString() {
        return this.topLevelNode.toString();
    }
}
