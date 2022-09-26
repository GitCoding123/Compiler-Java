import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 */
public class SymbolRetriever extends LittleBaseListener {

    private int counter = 0;
    private Stack<SymbolTable> symbolTableStack;
    private SymbolTable currentTable;
    private ArrayList<String> funcData = new ArrayList<String>();
    private boolean check = true;
    private String tailType = "INT";

    /**
     *
     */
    public SymbolRetriever() {
        this.symbolTableStack = new Stack<SymbolTable>();
        this.currentTable = null;
    }


    /**
     *
     * @param ctx
     */
    @Override
    public void enterProgram(LittleParser.ProgramContext ctx) {
        this.symbolTableStack.push(new SymbolTable("GLOBAL"));
        this.currentTable = this.symbolTableStack.peek();
        String scope = this.currentTable.getScope();


    }

    /**
     *
     * @param ctx
     */
    @Override
    public void exitProgram(LittleParser.ProgramContext ctx) {
    }

    /**
     *
     * @param ctx
     */
    @Override public void enterIf_stmt(LittleParser.If_stmtContext ctx) {
        counter++;
        this.symbolTableStack.push(new SymbolTable("BLOCK " + counter));
        this.currentTable = this.symbolTableStack.peek();
        String scope = this.currentTable.getScope();

    }

    /**
     *
     * @param ctx
     */
    @Override public void exitIf_stmt(LittleParser.If_stmtContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override public void enterElse_part(LittleParser.Else_partContext ctx) {
        if(ctx.getChildCount() != 0) {
            counter++;
            this.symbolTableStack.push(new SymbolTable("BLOCK " + counter));
            this.currentTable = this.symbolTableStack.peek();
            String scope = this.currentTable.getScope();

        }
    }

    /**
     *
     * @param ctx
     */
    @Override public void exitElse_part(LittleParser.Else_partContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override public void enterAssign_expr(LittleParser.Assign_exprContext ctx) {
        String name = ctx.getText();
        funcData.add(name);
    }

    /**
     *
     * @param ctx
     */

    @Override public void exitAssign_expr(LittleParser.Assign_exprContext ctx) {

    }
    /**
     *
     * @param ctx
     */
    @Override public void enterWrite_stmt(LittleParser.Write_stmtContext ctx) {
        check = false;

        String name = ctx.getText();
        funcData.add(name);
        //String type = ctx.id_list().id_tail().id().IDENTIFIER().getText();
        //this.currentTable.addSymbol(name, new SymbolAttributes(type, null));
        //output.add("name " + name + " type " + type);
    }

    /**
     *
     * @param ctx
     */
    @Override public void exitWrite_stmt(LittleParser.Write_stmtContext ctx) {
        check = true;
    }

    /**
     *
     * @param ctx
     */
    @Override public void enterRead_stmt(LittleParser.Read_stmtContext ctx) {
        check = false;
            String name = ctx.getText();
            funcData.add(name);
//            String type = tailType;
//            this.currentTable.addSymbol(name, new SymbolAttributes(type, null));

    }

    /**
     *
     * @param ctx
     */
    @Override public void exitRead_stmt(LittleParser.Read_stmtContext ctx) {
        check = true;
    }

    /**
     *
     * @param ctx
     */
    @Override public void enterReturn_stmt(LittleParser.Return_stmtContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override public void exitReturn_stmt(LittleParser.Return_stmtContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override
    public void enterString_decl(LittleParser.String_declContext ctx) {
        this.currentTable.addSymbol(ctx.id().IDENTIFIER().getText(),
                new SymbolAttributes("STRING", ctx.str().STRINGLITERAL().getText()));
    }

    /**
     *
     * @param ctx
     */
    @Override
    public void exitString_decl(LittleParser.String_declContext ctx) {
        String name = ctx.id().IDENTIFIER().getSymbol().getText();
        String value = ctx.str().STRINGLITERAL().getText();
    }

    /**
     *
     * @param ctx
     */
    @Override
    public void enterVar_decl(LittleParser.Var_declContext ctx) {
        String name = ctx.id_list().id().IDENTIFIER().getText();
        String type = ctx.var_type().getText();
        this.currentTable.addSymbol(name, new SymbolAttributes(type, null));
        tailType = ctx.var_type().getText();
    }

    /**
     *
     * @param ctx
     */
    @Override
    public void exitVar_decl(LittleParser.Var_declContext ctx) {

    }

    @Override public void enterId(LittleParser.IdContext ctx) {

    }

    @Override public void exitId(LittleParser.IdContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override public void enterId_tail(LittleParser.Id_tailContext ctx) {
        if (ctx.id() != null && check) {
            String name = ctx.id().IDENTIFIER().getText();
            this.currentTable.addSymbol(name, new SymbolAttributes(tailType, null));
        }
    }

    /**
     *
     * @param ctx
     */
    @Override public void exitId_tail(LittleParser.Id_tailContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override public void enterFunc_decl(LittleParser.Func_declContext ctx) {
        this.symbolTableStack.push(new SymbolTable(ctx.id().IDENTIFIER().getText()));
        this.currentTable = this.symbolTableStack.peek();
        String scope = this.currentTable.getScope();

    }

    /**
     *
     * @param ctx
     */
    @Override public void exitFunc_decl(LittleParser.Func_declContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override public void enterParam_decl(LittleParser.Param_declContext ctx) {
        String name = ctx.id().IDENTIFIER().getText();
        String type = ctx.var_type().getText();
        this.currentTable.addSymbol(name, new SymbolAttributes(type, null));

    }

    /**
     *
     * @param ctx
     */
    @Override public void exitParam_decl(LittleParser.Param_declContext ctx) {

    }

    /**
     *
     * @param ctx
     */
    @Override public void enterWhile_stmt(LittleParser.While_stmtContext ctx) {
        counter++;
        this.symbolTableStack.push(new SymbolTable("BLOCK " + counter));
        this.currentTable = this.symbolTableStack.peek();
        String scope = this.currentTable.getScope();

    }

    /**
     *
     * @param ctx
     */
    @Override public void exitWhile_stmt(LittleParser.While_stmtContext ctx) {


    }

    //Step3 arraylist output
//    public List<String> getOutput() {
//        return output;
//    }

    /**
     *
     * @return
     */
    public Stack<SymbolTable> getNewStack() {

        Stack<SymbolTable> newStack = new Stack<SymbolTable>();

        int size = this.symbolTableStack.size();

        for(int i = 0; i < size; i++) {
            newStack.add(this.symbolTableStack.pop());
        }
        return newStack;
    }

    public ArrayList<String> getFuncData() {
        return funcData;
    }


}
