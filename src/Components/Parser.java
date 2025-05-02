package Components;

import Variables.ASTNode;
import Types.ASTNodeType;
import Variables.Token;
import Types.TokenType;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int pos;
    private Token currentToken;

    public Parser(List<Token> tokens){
        this.tokens = tokens;
        this.pos = 0;
        this.currentToken = tokens.get(pos);
    }

    private void nextToken() {
        if (pos < tokens.size() - 1) {
            currentToken = tokens.get(++pos);
        }
    }

    private ASTNode primary(){
        if (currentToken.token == TokenType.T_LPARENT) {
            nextToken();
            ASTNode node = binaryExpression(0);
            if (currentToken.token != TokenType.T_RPARENT) {
                throw new RuntimeException("Expected ')' but found " + currentToken.token);
            }
            nextToken();
            return node;
        }
        if (currentToken.token != TokenType.T_INTLIT) {
            throw new RuntimeException("Expected INTLIT but found " + currentToken.token);
        }

        ASTNode n = new ASTNode(ASTNodeType.A_INTLIT, currentToken.intValue);
        nextToken();
        return n;
    }

    private ASTNodeType tokenToArithOperator(TokenType token) {
        return switch (token) {
            case TokenType.T_PLUS -> ASTNodeType.A_ADD;
            case TokenType.T_MINUS -> ASTNodeType.A_SUBTRACT;
            case TokenType.T_STAR -> ASTNodeType.A_MULTIPLY;
            case TokenType.T_SLASH -> ASTNodeType.A_DIVIDE;
            default -> throw new RuntimeException("Invalid arithmetic operator: " + token);
        };
    }

    static int[] OpPrecedence = { 0, 10, 10, 20, 20, 0, 1, 1};

    static int op_precedence(TokenType tokentype) {
        int precedence = OpPrecedence[tokentype.ordinal()];
        if (precedence == 0) {
            System.err.println("Syntax Error on Token: " + tokentype);
        }

        return precedence;
    }

    public ASTNode binaryExpression(int ptp){

        ASTNode left = primary();
        TokenType tokentype = currentToken.token;

        if (tokentype == TokenType.T_EOF){
            return left;
        }

        while(op_precedence(tokentype) > ptp){
            nextToken();
            ASTNode right = binaryExpression(OpPrecedence[tokentype.ordinal()]);
            left = new ASTNode(tokenToArithOperator(tokentype), left, right, 0);

            tokentype = currentToken.token;
            if (tokentype == TokenType.T_EOF){
                return left;
            }
        }
        return left;
    }

    /**
    public ASTNode binExpr(){

        ASTNode left = primary();

        if (currentToken.token == TokenType.T_EOF){
            return left;
        }

        ASTNodeType nodeType = arithOp(currentToken.token);
        nextToken();
        ASTNode right = binExpr();

        return new ASTNode(nodeType, left, right, 0);
    }

    */
}
