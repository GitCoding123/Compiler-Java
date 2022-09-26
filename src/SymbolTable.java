import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class SymbolTable {

    private String scope;

    private HashMap<String, SymbolAttributes> symbolTable;

    private ArrayList<String> symbolNames;

    /**
     *
     * @param scope
     */
    public SymbolTable(String scope) {
        this.scope = scope;
        this.symbolTable = new HashMap<String, SymbolAttributes>();
        this.symbolNames = new ArrayList<String>();
    }

    /**
     *
     * @return
     */
    public String getScope() {
        return this.scope;
    }

    /**
     *
     * @param symbolName
     * @param attributes
     */
    public void addSymbol(String symbolName, SymbolAttributes attributes) {
        if (this.symbolTable.containsKey(symbolName)) {
            return;
            //System.out.printf("DECLARATION ERROR %s\n", symbolName);
            //System.exit(0);
        }

        this.symbolTable.put(symbolName, attributes);
        this.symbolNames.add(symbolName);
    }

    /**
     *
     * @return
     */
    public HashMap<String, SymbolAttributes> getSymbolTable() {
        return symbolTable;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getSymbolNames() {
        return symbolNames;
    }


}