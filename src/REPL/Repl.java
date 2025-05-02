package REPL;

import Components.ImprovedParser;
import Components.Lexer;
import Variables.ASTNode;
import Variables.Token;

import java.time.Year;
import java.util.List;
import java.util.Scanner;

import static Components.ASTInterpreter.interpretAST;

public class Repl {
    public static void start() {
        Year thisYear = Year.now();
        Scanner scanner = new Scanner(System.in);
        System.out.println("CLC Lang REPL v1.0.0");
        System.out.println("Copyright © 2024-"+ thisYear +", Arnab Pal, All Rights Reserved.");
        System.out.println("(type 'exit' to quit)");

        while (true) {
            System.out.print(">>> ");
            String input = scanner.nextLine().trim();

            // Exit command
            if (input.equalsIgnoreCase("tata")) {
                break;
            }

            // Skip empty input
            if (input.isEmpty()) {
                continue;
            }

            try {
                // Process the input
                List<Token> tokens = Lexer.scan(input);
                ImprovedParser parser = new ImprovedParser(tokens);
                List<ASTNode> statements = parser.parse();

                // Execute and print any output
                for (ASTNode statement : statements) {
                    interpretAST(statement);
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
