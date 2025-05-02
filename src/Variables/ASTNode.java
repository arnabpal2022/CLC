package Variables;

import Types.ASTNodeType;

public class ASTNode {
    private ASTNodeType op;
    private ASTNode left;
    private ASTNode right;
    private int intValue;
    private String strValue;

    /** Makes an AST Node */
    public ASTNode(ASTNodeType op, ASTNode left, ASTNode right, int intValue) {
        this.op = op;
        this.left = left;
        this.right = right;
        this.intValue = intValue;
        this.strValue = null;
    }

    public ASTNode(ASTNodeType op, String strValue) {
        this(op, null, null, 0);
        this.strValue = strValue;
    }

    /** Makes an AST Leaf Node */
    public ASTNode(ASTNodeType op, int intValue) {
        this(op, null, null, intValue);
        this.strValue = null;
    }

    /** Makes an AST Unary Node */
    public ASTNode(ASTNodeType op, ASTNode left, int intValue) {
        this.op = op;
        this.left = left;
        this.right = null;
        this.intValue = intValue;
    }

    public String getStrValue() {
        return strValue;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public ASTNodeType getOp() {
        return op;
    }

    public int getIntValue() {
        return intValue;
    }

    public ASTNode getLeft() {
        return left;
    }

    public ASTNode getRight() {
        return right;
    }

    public static void printTree(ASTNode node, int level) {
        if (node == null) return;
        String indent = "  ".repeat(level);

        if (node.isLeaf()) {
            if (node.getOp() == ASTNodeType.A_INTLIT) {
                System.out.println(indent + "Int: " + node.getIntValue());
            } else if (node.getOp() == ASTNodeType.A_IDENT) {
                System.out.println(indent + "Ident: " + node.getStrValue());
            }
        } else {
            System.out.println(indent + "Op: " +  node.getOp());
            printTree(node.getLeft(), level + 1);
            printTree(node.getRight(), level + 1);
        }
    }
}


