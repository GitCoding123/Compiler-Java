import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.*;

import static java.lang.System.exit;

/**
 *
 */
public class Driver{

    /**
     *
     */
    static class VerboseListener extends BaseErrorListener {

        /**
         *
         * @param recognizer
         * @param offendingSymbol
         * @param line
         * @param charPositionInLine
         * @param msg
         * @param e
         */

        //Step 2 output
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                                Object offendingSymbol,
                                int line, int charPositionInLine,
                                String msg,
                                RecognitionException e) {
            List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
            Collections.reverse(stack);
            System.out.println("Not accepted");
            System.exit(0);

//            System.err.println("rule stack: " + stack);
//            System.err.println("line " + line + ":" + charPositionInLine + " at " + offendingSymbol + ": " + msg);
        }

    }

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //Driver dr = new Driver();
//        SymbolTable st = new SymbolTable("Block 1");
        SymbolTable currentTable = null;

        String fileName = "test1.micro";
        File file = new File(fileName);
        FileInputStream fis = null;

        // Open the input file stream
        fis = new FileInputStream(file);

        // Create a CharStream that reads from standard input
        CharStream input = CharStreams.fromStream(fis);


        //Create a lexer that feeds off of input CharStream
        LittleLexer lexer = new LittleLexer(input);

        //Create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        //Create a parser

        LittleParser parser = new LittleParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new VerboseListener());
        ParseTree tree = parser.program();



        //System.out.println("Symbol table " + sr);

        TinyConversion tc = new TinyConversion();
        SymbolRetriever sr = new SymbolRetriever();
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(sr, tree);


//        final LittleParser.ExprContext context = parser.expr();
//
//        System.out.println(context);


        String value;
        Stack<SymbolTable> orderedStack = sr.getNewStack();

        /**
         * Step 3 output
         */
//        while (!orderedStack.isEmpty()) {
//            SymbolTable currentTable = orderedStack.pop();
//            if(currentTable.getScope() == "GLOBAL") {
//                System.out.println("Symbol table " + currentTable.getScope());
//            }
//            else {
//                System.out.println("\nSymbol table " + currentTable.getScope());
//            }
//            ArrayList<String> symbolNames = currentTable.getSymbolNames();
//            HashMap<String, SymbolAttributes> map = currentTable.getSymbolTable();
//            for (int i = 0; i < symbolNames.size(); i++) {
//                String name = symbolNames.get(i);
//                String type = map.get(symbolNames.get(i)).getType();
//                if (type == "STRING") {
//                    value = map.get(symbolNames.get(i)).getValue();
//                    System.out.println("name " + name + " type " + type + " value " + value);
//                } else
//                    System.out.println("name " + name + " type " + type);
//            }
//        }


        System.out.println(";tiny code");
        while (!orderedStack.isEmpty()) {
            currentTable = orderedStack.pop();

            ArrayList<String> symbolNames = currentTable.getSymbolNames();
            HashMap<String, SymbolAttributes> map = currentTable.getSymbolTable();

            if(currentTable.getScope() == "GLOBAL") {
                tc.setGlobalTable(currentTable);
                SymbolTable mainTable = orderedStack.pop();
            }

            for (int i = 0; i < symbolNames.size(); i++) {

                String name = symbolNames.get(i);
                String type = map.get(symbolNames.get(i)).getType();
                if (type == "STRING") {
                    value = map.get(symbolNames.get(i)).getValue();
                    System.out.println("str " + name + " " + value);
                } else {

                    System.out.println("var " + name);
                }
            }
        }
        //Print everything within inner scope (not global)
        for(String s: tc.getTiny(sr.getFuncData())) {
            System.out.println(s);
        }

        /**
         * Step 1 output
         */
//            for(Token token: tokens.getTokens()) {
//                if (token.getType() != -1) {
//                    System.out.println("Token Type: " + lexer.getVocabulary().getSymbolicName(token.getType()));
//                    System.out.println("Value: " + token.getText());
//                }//end if
//            }//end for
    }
}




