package Components;

import Variables.ASTNode;
import Variables.ASTNodeType;
import Variables.Token;
import Variables.TokenType;

import java.util.List;

public class ImprovedParser {
    private final List<Token> tokens;
    private int position = 0;
    private Token currentToken;

    public ImprovedParser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentToken = tokens.get(position);
    }

    private void advance() {
        position++;
        if (position < tokens.size()) {
            currentToken = tokens.get(position);
        } else {
            currentToken = new Token(TokenType.T_EOF); // End of tokens
        }
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

    public ASTNode parse() {
        if (currentToken.token == TokenType.T_PRINT) {
            return printStatement();
        }
        return expression();
    }

    private ASTNode printStatement() {
        advance(); // Skip 'print'

        // Expect a parenthesis after print
        if (currentToken.token != TokenType.T_LPARENT) {
            throw new RuntimeException("Expected '(' after print");
        }
        advance(); // Skip '('

        ASTNode expr = expression();

        // Expect closing parenthesis
        if (currentToken.token != TokenType.T_RPARENT) {
            throw new RuntimeException("Expected ')' after expression in print");
        }
        advance(); // Skip ')'

        return new ASTNode(ASTNodeType.A_PRINT, expr, 0);
    }

    private ASTNode expression() {
        ASTNode node = term();
        while (currentToken.token == TokenType.T_PLUS || currentToken.token == TokenType.T_MINUS) {
            Token token = currentToken;
            advance();
            ASTNode right = term();
            node = new ASTNode(tokenToArithOperator(token.token), node, right, token.intValue);
        }
        return node;
    }

    private ASTNode term() {
        ASTNode node = factor();
        while (currentToken.token == TokenType.T_STAR|| currentToken.token == TokenType.T_SLASH) {
            Token token = currentToken;
            advance();
            ASTNode right = factor();
            node = new ASTNode(tokenToArithOperator(token.token), node, right, token.intValue);
        }
        return node;
    }

    private ASTNode factor() {
        if (currentToken.token == TokenType.T_INTLIT) {
            ASTNode node = new ASTNode(ASTNodeType.A_INTLIT, currentToken.intValue);
            advance();
            return node;
        } else if (currentToken.token == TokenType.T_LPARENT) {
            advance(); // Skip '('
            ASTNode node = expression();
            advance(); // Skip ')'
            return node;
        }
        throw new RuntimeException("Unexpected token: " + currentToken);
    }
}
