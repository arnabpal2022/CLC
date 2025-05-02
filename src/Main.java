import Components.*;
import Variables.ASTNode;
import Variables.Token;
import Variables.TokenType;

import java.io.*;
import java.util.List;

public class Main {

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

            ASTNode root = new ImprovedParser(tokens).parse();

            // ASTNode root = new Parser(tokens).binaryExpression(0);
            ASTNode.printTree(root, 0);
            ASTInterpreter.interpretAST(root);

            /*
            try (PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter("output.s")));){
                CodeGenerator codeGenerator = new CodeGenerator();
                codeGenerator.generateCode(outFile, root);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            */

        } catch (FileNotFoundException e) {
            System.err.printf("Error: Unable to open %s: %s\n", fileName, e.getMessage());
            System.exit(1);
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}