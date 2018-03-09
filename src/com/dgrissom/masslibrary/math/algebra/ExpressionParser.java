package com.dgrissom.masslibrary.math.algebra;

import com.dgrissom.masslibrary.Strings;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser {
    private final String expression;

    public ExpressionParser(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }

    private String formatExpression() {
        return this.expression.replace(" ", "");
    }
    private List<String> tokenize() {
        String expression = formatExpression();
        List<String> tokens = new ArrayList<>();
        String currentNumber = "";
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (Strings.isDigit(ch) || ch == '.')
                currentNumber += ch;
            else {
                if (currentNumber.endsWith("."))
                    throw new IllegalArgumentException("found number ending with a decimal");

                if (!currentNumber.isEmpty()) {
                    tokens.add(currentNumber);
                    currentNumber = "";
                }
                tokens.add(String.valueOf(ch));
            }
        }
        if (!currentNumber.isEmpty())
            tokens.add(currentNumber);

        return tokens;
    }

    // order nodes based on operator precedence
    // *[+[4.0, 2.0], 2.0] (4+2*2) should become
    // +[*[2.0, 2.0], 4.0] (2*2+4)
    private Tree order(Tree tree) {
        System.out.println("Unordered: " + tree);

        return tree;
    }

    // loop through all tokens
    public Tree parse() {
        List<String> tokens = tokenize();
        System.out.println(tokens);

        Node topLevelNode = null;
        List<ConstantNode> operands = new ArrayList<>();
        Node operator = null;
        for (String token : tokens) {
            if (Strings.isDouble(token)) {
                ConstantNode constantNode = new ConstantNode(Double.parseDouble(token));
                operands.add(constantNode);
            } else {
                if (operator != null) {
                    // we already have an operator! previous operator was not given enough operands (constants)
                    throw new IllegalArgumentException("not enough operands for operator " + operator.getToken());
                }
                // operator
                operator = Node.lookupOperator(token);
            }

            if (operator != null) {
                // we don't have a full node yet
                if (topLevelNode == null && operands.size() == operator.getArity()) {
                    operator.addChildren(operands);
                    topLevelNode = operator;

                    operands = new ArrayList<>();
                    operator = null;
                }
                // we have a full node
                // example: 4+5+2
                // this is the +2 part
                else if (topLevelNode != null && operands.size() == operator.getArity() - 1) {
                    // +2 is the new top level node
                    // and its children are the first + (4+5) and 2
                    operator.addChildren(topLevelNode);
                    operator.addChildren(operands);
                    topLevelNode = operator;

                    operands = new ArrayList<>();
                    operator = null;
                }
            }
        }

        // there's an operator left that wasn't given enough operands!
        if (operator != null)
            throw new IllegalArgumentException("not enough operands for operator " + operator.getToken());

        Tree tree = new Tree(topLevelNode);
        return order(tree);
    }
}
