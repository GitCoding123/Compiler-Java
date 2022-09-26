import java.util.ArrayList;
import java.util.HashMap;

public class TinyConversion {
    private int registerCounter = 0;
    private ArrayList<String> tinyCode = new ArrayList<String>();
    private SymbolTable globalTable;
    SymbolRetriever sr = new SymbolRetriever();


    public ArrayList<String> getTiny(ArrayList<String> instructionList) {

        HashMap<String, SymbolAttributes> map = globalTable.getSymbolTable();

        for(int i=0; i<instructionList.size(); i++) {
            String instruction = instructionList.get(i);

            if(instruction.contains("+")) {
                String var1 = instruction.substring(0, 1);
                String var2 = "";
                String var3 = "";
                if(instruction.contains("(")) {
                    var2 = instruction.substring(4, 5);
                    var3 = instruction.substring(6, 7);
                }
                else{
                    var2 = instruction.substring(3, 4);
                    var3 = instruction.substring(5, 6);
                }

                if(map.get(var1).getType().equals("FLOAT")) {
                    tinyCode.add("move " + var2 + " r" + registerCounter);
                    tinyCode.add("addr " + var3 + " r" + registerCounter);
                    tinyCode.add("move " + "r" + registerCounter + " " + var1);
                } else {
                    tinyCode.add("move " + var2 + " r" + registerCounter);
                    tinyCode.add("addi " + var3 + " r" + registerCounter);
                    tinyCode.add("move " + "r" + registerCounter + " " + var1);
                }
                registerCounter++;
            }
            else if(instruction.contains("-")) {
                String var1 = instruction.substring(0, 1);
                String var2 = "";
                String var3 = "";
                if(instruction.contains("(")) {
                    var2 = instruction.substring(4, 5);
                    var3 = instruction.substring(6, 7);
                }
                else{
                    var2 = instruction.substring(3, 4);
                    var3 = instruction.substring(5, 6);
                }

                if(map.get(var1).getType().equals("FLOAT")) {
                    tinyCode.add("move " + var2 + " r" + registerCounter);
                    tinyCode.add("addr " + var3 + " r" + registerCounter);
                    tinyCode.add("move " + "r" + registerCounter + " " + var1);
                } else {
                    tinyCode.add("move " + var2 + " r" + registerCounter);
                    tinyCode.add("addi " + var3 + " r" + registerCounter);
                    tinyCode.add("move " + "r" + registerCounter + " " + var1);
                }
                registerCounter++;
            }
            else if(instruction.contains("*")) {
                //Multiplicatiopn expression
                String var1 = instruction.substring(0, 1);
                String var2 = "";
                String var3 = "";
                if(instruction.contains("(")) {
                    var2 = instruction.substring(4, 5);
                    var3 = instruction.substring(6, 7);
                }
                else{
                    var2 = instruction.substring(3, 4);
                    var3 = instruction.substring(5, 6);
                }

                if(map.get(var1).getType().equals("FLOAT")) {
                    tinyCode.add("move " + var2 + " r" + registerCounter);
                    tinyCode.add("addr " + var3 + " r" + registerCounter);
                    tinyCode.add("move " + "r" + registerCounter + " " + var1);
                } else {
                    tinyCode.add("move " + var2 + " r" + registerCounter);
                    tinyCode.add("addi " + var3 + " r" + registerCounter);
                    tinyCode.add("move " + "r" + registerCounter + " " + var1);
                }
                registerCounter++;
            }
            else if(instruction.contains("/")) {
                //Division expression
                String var1 = instruction.substring(0, 1);
                String var2 = "";
                String var3 = "";
                String line1 = "";
                String line2 = "";
                String line3 = "";
                String line4 = "";
                if(instruction.contains("(")) {
                    var2 = instruction.substring(4, 5);
                    var3 = instruction.substring(6, instruction.length() - 1);
                }
                else{
                    var2 = instruction.substring(3, 4);
                    var3 = instruction.substring(5, instruction.length());
                }
                int reg1 = registerCounter;
                int reg2 = registerCounter + 1;
                if(map.get(var1).getType() == "FLOAT") {
                    if (instruction.contains(".")) {
                        line1 = "move " + var3 + " r" + reg1;
                        line2 = "move " + var2 + " r" + reg2;
                        line3 = "divr " + "r" + reg1 + " r" + reg2;
                        line4 = "move " + "r" + reg2 + " " + var1;
                        tinyCode.add(line1);
                        tinyCode.add(line2);
                        tinyCode.add(line3);
                        tinyCode.add(line4);
                        //registerCounter++;
                    } else {
                        line1 = "move " + var2 + " r" + reg2;
                        line2 = "divr " + var3 + " r" + reg2;
                        line3 = "move " + " r" + reg2 + " " + var1;
                        tinyCode.add(line1);
                        tinyCode.add(line2);
                        tinyCode.add(line3);
                        registerCounter++;
                    }
                } else {
                    if (instruction.contains(".")) {
                        line1 = "move " + var3 + " r" + reg1;
                        line2 = "move " + var2 + " r" + reg2;
                        line3 = "divr " + "r" + reg1 + " r" + reg2;
                        line4 = "move " + "r" + reg2 + " " + var1;
                        tinyCode.add(line1);
                        tinyCode.add(line2);
                        tinyCode.add(line3);
                        tinyCode.add(line4);
                        //registerCounter++;
                    } else {
                        line1 = "move " + var2 + " r" + reg2;
                        line2 = "divi " + var3 + " r" + reg2;
                        line3 = "move " + " r" + reg2 + " " + var1;
                        tinyCode.add(line1);
                        tinyCode.add(line2);
                        tinyCode.add(line3);
                        registerCounter++;
                    }
                }

                registerCounter++;
            }
            else if(instruction.contains("READ")) {
                //Read statement
                String instructionTrim = instruction.substring(5, instruction.length() - 2);
                for(String s: instructionTrim.split(",")) {
                    if (map.get(s).getType() == "FLOAT") {
                        tinyCode.add("sys readr " + s);
                    } else{
                        tinyCode.add("sys readi " + s);
                    }
                }
            }
            else if(instruction.contains("WRITE")) {
                //Write statement

                String instructionTrim = instruction.substring(6, instruction.length() - 2);
                for(String s: instructionTrim.split(",")) {
                    if(map.get(s).getType().equals("FLOAT")){
                        tinyCode.add("sys writer " + s);
                    }
                    else if(map.get(s).getType().equals("STRING")){
                        tinyCode.add("sys writes " + s);
                    }
                    else{
                        tinyCode.add("sys writei " + s);
                    }
                }
//                String var = instruction.substring(6, 7);
//                String line1 = "sys writei " + var;
//                tinyCode.add(line1);

            }
            else{
                //Simple assignments
                String data = instruction.substring(3);
                String var = instruction.substring(0, 1);
                String line1 = "move " + data + " r" + registerCounter;
                String line2 = "move " + "r" + registerCounter + " " + var;
                tinyCode.add(line1);
                tinyCode.add(line2);
                registerCounter++;
            }


//            if(instruction.contains("+")) {
//                tinyCode.add(instruction);
//            }
        }//end for

        tinyCode.add("sys halt");

        return tinyCode;
    }

    public void setGlobalTable(SymbolTable globalTable) {
        this.globalTable = globalTable;
    }
}
