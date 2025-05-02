package Components;

import Variables.Token;
import Types.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public static List<Token> scan(String input) {
        List<Token> Tokens = new ArrayList<>();
        int i = 0;
        while (i < input.length()) {
            char ch = input.charAt(i);

            if (Character.isWhitespace(ch)) {
                i++;
                continue;
            }

            if (Character.isLetter(ch)) {
                int start = i;
                while (i < input.length() && Character.isLetter(input.charAt(i))) {
                    i++;
                }
                String word = input.substring(start, i);
                if (word.equals("print")) {
                    Tokens.add(new Token(TokenType.T_PRINT));
                } else if (word.equals("let")) {
                    Tokens.add(new Token(TokenType.T_LET));
                } else {
                    Tokens.add(new Token(TokenType.T_IDENT, word));
                }
                continue;
            }

            switch (ch) {
                case '=':
                    Tokens.add(new Token(TokenType.T_EQUALS));
                    i++;
                    break;
                case '(':
                    Tokens.add(new Token(TokenType.T_LPARENT));
                    i++;
                    break;
                case ')':
                    Tokens.add(new Token(TokenType.T_RPARENT));
                    i++;
                    break;
                case '+':
                    Tokens.add(new Token(TokenType.T_PLUS));
                    i++;
                    break;
                case '-':
                    Tokens.add(new Token(TokenType.T_MINUS));
                    i++;
                    break;
                case '*':
                    Tokens.add(new Token(TokenType.T_STAR));
                    i++;
                    break;
                case '/':
                    Tokens.add(new Token(TokenType.T_SLASH));
                    i++;
                    break;
                default:
                    if (Character.isDigit(ch)) {
                        int start = i;
                        while (i < input.length() && Character.isDigit(input.charAt(i))) {
                            i++;
                        }
                        int value = Integer.parseInt(input.substring(start, i));
                        Tokens.add(new Token(TokenType.T_INTLIT, value));
                    } else {
                        throw new RuntimeException("Invalid character: " + ch);
                    }
            }
        }



        return Tokens;
    }
}
