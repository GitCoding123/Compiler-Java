/**
 *
 */
public class SymbolAttributes {
    String type;
    String value;

    /**
     *
     * @param type
     * @param value
     */
    public SymbolAttributes(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     *
     * @return
     */
    public String getValue() {
        return this.value;
    }
}
