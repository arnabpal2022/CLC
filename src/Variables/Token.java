package Variables;

import Types.TokenType;

public class Token {
    public TokenType token;
    public int intValue;
    public String varName;

    public Token(TokenType token, int intValue) {
        this.token = token;
        this.intValue = intValue;
        this.varName = null;
    }

    public Token(TokenType token, String varName){
        this.token = token;
        this.intValue = 0;
        this.varName = varName;
    }

    public Token(TokenType token) {
        this(token, 0);
        this.varName = null;
    }

    @Override
    public String toString() {
        return "Token {" + "token=" + token + ", value=" + intValue + "}";
    }
}
