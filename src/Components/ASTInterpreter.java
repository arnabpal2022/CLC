package Components;

import Variables.ASTNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ASTInterpreter {
    private static final Map<String, Integer> variables = new HashMap<>();

    public static void interpretProgram(List<ASTNode> statements) {
        for (ASTNode statement : statements) {
            interpretAST(statement);
        }
    }

    public static void interpretAST(ASTNode node) {
        if(node == null) return;

        switch (node.getOp()) {
            case A_PRINT:
                int value = evaluateExpression(node.getLeft());
                System.out.println("Result: " + value);
                break;
            case A_LET:
                handleLetStatement(node);
                break;
            case A_ASSIGN:
                handleAssignment(node);
                break;
            default:
                int result = evaluateExpression(node);
                System.out.println(result);
        }

    }

    private static void handleLetStatement(ASTNode node) {
        String varName = node.getLeft().getStrValue();
        int value = evaluateExpression(node.getRight());
        variables.put(varName, value);
    }

    private static void handleAssignment(ASTNode node) {
        String varName = node.getLeft().getStrValue();
        if (!variables.containsKey(varName)) {
            throw new RuntimeException("Variable not declared: " + varName);
        }
        int value = evaluateExpression(node.getRight());
        variables.put(varName, value);
    }

    private static int evaluateExpression(ASTNode node) {
        if (node == null) {
            throw new RuntimeException("Null node in expression");
        }

        switch (node.getOp()) {
            case A_INTLIT:
                return node.getIntValue();
            case A_IDENT:
                if (!variables.containsKey(node.getStrValue())) {
                    throw new RuntimeException("Undefined variable: " + node.getStrValue());
                }
                return variables.get(node.getStrValue());
            case A_ADD:
            case A_SUBTRACT:
            case A_MULTIPLY:
            case A_DIVIDE:
                int leftVal = evaluateExpression(node.getLeft());
                int rightVal = evaluateExpression(node.getRight());
                return switch (node.getOp()) {
                    case A_ADD -> leftVal + rightVal;
                    case A_SUBTRACT -> leftVal - rightVal;
                    case A_MULTIPLY -> leftVal * rightVal;
                    case A_DIVIDE -> leftVal / rightVal;
                    default -> throw new IllegalArgumentException("Unknown operator");
                };
            default:
                throw new RuntimeException("Unknown node type in expression: " + node.getOp());
        }
    }
}
