package Variables;

public class Token {
    public TokenType token;
    public int intValue;

    public Token(TokenType token, int intValue) {
        this.token = token;
        this.intValue = intValue;
    }

    public Token(TokenType token) {
        this(token, 0);
    }

    @Override
    public String toString() {
        return "Token {" + "token=" + token + ", value=" + intValue + "}";
    }
}
