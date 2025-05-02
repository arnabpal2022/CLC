import Components.*;
import REPL.Repl;
import Variables.ASTNode;
import Variables.Token;
import Types.TokenType;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args){
        Repl.start();
    }

    /*
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: Argument Limit Exceeded");
        }

        String fileName = args[0];
        StringBuilder content = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            List<Token> tokens = Lexer.scan(content.toString());
            tokens.add(new Token(TokenType.T_EOF));

            List<ASTNode> root = new ImprovedParser(tokens).parse();

            ASTInterpreter.interpretProgram(root);

            Repl.start();

        } catch (FileNotFoundException e) {
            System.err.printf("Error: Unable to open %s: %s\n", fileName, e.getMessage());
            System.exit(1);
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/
}