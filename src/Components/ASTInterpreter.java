package Components;

import Variables.ASTNode;
import Variables.ASTNodeType;

public class ASTInterpreter {

    public static char[] operators = {'+', '-', '*', '/'};

    public static void interpretAST(ASTNode node) {
        // Handle print statements
        if (node.getOp() == ASTNodeType.A_PRINT) {

            int value = evaluateExpression(node.getLeft());
            System.out.println("Result: " + value);
            return;
        }

        int result = evaluateExpression(node);
//        System.out.println(result);
    }

    private static int evaluateExpression(ASTNode node) {

        if(node.getOp() == ASTNodeType.A_INTLIT){
            return node.getIntValue();
        }

        int leftVal = evaluateExpression(node.getLeft());
        int rightVal = evaluateExpression(node.getRight());

        return switch (node.getOp()) {
            case A_ADD -> (leftVal + rightVal);
            case A_SUBTRACT -> (leftVal - rightVal);
            case A_MULTIPLY -> (leftVal * rightVal);
            case A_DIVIDE -> (leftVal / rightVal);
            default -> throw new IllegalArgumentException("Unknown AST operator: " + node.getOp().ordinal());
        };
    }
}
