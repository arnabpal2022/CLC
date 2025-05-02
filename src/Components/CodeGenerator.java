package Components;

import Variables.ASTNode;
import Variables.ASTNodeType;

import java.io.Console;
import java.io.PrintWriter;
import java.util.Arrays;

public class CodeGenerator {

    private static int[] freereg = new int[4];
    private final String[] reglist = {"%r8", "%r9", "%r10", "%r11"};

    private static void freeAllRegisters() {
        Arrays.fill(freereg, 1);
    }

    private static int alloc_register(){
        for (int i=0; i<4; i++) {
            if (freereg[i]==1) {
                freereg[i]= 0;
                return i;
            }
        }
        System.err.println("Out of registers!");
        System.exit(1);
        return -1;
    }

    private static void freeRegister(int reg) {
        if (reg < 0 || reg >= freereg.length || freereg[reg] != 0) {
            System.err.println("Error trying to free register " + reg);
            System.exit(1);
        }
        freereg[reg] = 1;
    }



    private int cgload(PrintWriter outFile, int value){
        int r = alloc_register();

        outFile.printf("\tmovq\t$%d, %s\n", value, reglist[r]);
        return r;
    }

    private int cgadd(PrintWriter outFile, int r1, int r2){
        outFile.printf("\taddq\t%s, %s\n", reglist[r1], reglist[r2]);
        freeRegister(r1);

        return r2;
    }

    private int cgsub(PrintWriter outFile, int r1, int r2){
        outFile.printf("\tsubq\t%s, %s\n", reglist[r1], reglist[r2]);
        freeRegister(r2);

        return r1;
    }

    private int cgmul(PrintWriter outFile, int r1, int r2){
        outFile.printf("\timulq\t%s, %s\n", reglist[r1], reglist[r2]);
        freeRegister(r1);

        return r2;
    }

    private int cgdiv(PrintWriter outFile, int r1, int r2){

        outFile.printf("\tmovq\t%s, %%rax\n", reglist[r1]);
        outFile.printf("\tcqo\n");
        outFile.printf("\tidivq\t%s\n", reglist[r2]);
        outFile.printf("\tmovq\t%%rax, %s\n", reglist[r1]);

        freeRegister(r2);
        return r1;
    }

    private void cgprintint(PrintWriter outFile, int r){
        outFile.printf("\tmovq\t%s, %%rdi\n", reglist[r]);
        outFile.printf("\tcall\tprintint\n");
        freeRegister(r);
    }

    private static void cgpreamble(PrintWriter outFile) {
        freeAllRegisters();
        String assemblyCode =
                // "\t.text\n" +
                ".LC0:\n" +
                "\t.string\t\"%d\\n\"\n"+
                "printint:\n"+
                "\tpushq\t%rbp\n"+
                "\tmovq\t%rsp, %rbp\n"+
                "\tsubq\t$16, %rsp\n"+
                "\tmovl\t%edi, -4(%rbp)\n"+
                "\tmovl\t-4(%rbp), %eax\n"+
                "\tmovl\t%eax, %esi\n"+
                "\tleaq	.LC0(%rip), %rdi\n"+
                "\tmovl	$0, %eax\n"+
                "\tcall	printf@PLT\n"+
                "\tnop\n"+
                "\tleave\n"+
                "\tret\n"+
                "\n"+
                "\t.globl\tmain\n"+
                "\t.type\tmain, @function\n"+
                "main:\n"+
                "\tpushq\t%rbp\n"+
                "\tmovq	%rsp, %rbp\n";
        outFile.println(assemblyCode);

    }

    private static void cgpostamble(PrintWriter outFile){
        String assemblyCode =
                "\tmovl	$0, %eax\n"+
                "\tpopq	%rbp\n"+
                "\tret\n";
        outFile.println(assemblyCode);

    }


    public int genAST(PrintWriter outFile, ASTNode node){

        if(node.getOp() == ASTNodeType.A_INTLIT){
            return cgload(outFile, node.getIntValue());
        }

        int leftRegister = genAST(outFile, node.getLeft());
        int rightRegister = genAST(outFile, node.getRight());

        return switch (node.getOp()) {
            case A_ADD -> cgadd(outFile, leftRegister, rightRegister);
            case A_SUBTRACT -> cgsub(outFile, leftRegister, rightRegister);
            case A_MULTIPLY -> cgmul(outFile, leftRegister, rightRegister);
            case A_DIVIDE -> cgdiv(outFile, leftRegister, rightRegister);
            default -> throw new IllegalArgumentException("Unknown AST operator: " + node.getOp().ordinal());
        };



    }

    public void generateCode(PrintWriter outFile, ASTNode node){

        cgpreamble(outFile);
        int reg = genAST(outFile,node);
        cgprintint(outFile, reg);
        cgpostamble(outFile);

    }
}
