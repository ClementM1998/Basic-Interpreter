import java.util.*;

public class Environment {
    private final TreeMap<Integer, Statement> programLines;
    private final Map<String, Expression> variables;
    private final Map<String, Boolean> forVariables; // true (active) or false (non-active)
    private final Map<String, ArrayExpression> dimVariables;
    private final Map<Integer, NumberExpression> dimValues;
    private final Stack<Integer> returnStack;
    private final Queue<NumberExpression> dataQueue;
    private Integer currentLine = null;
    private Integer nextLine = null; // Tambah variable untuk GOTO
    private boolean running;

    public Environment() {
        programLines = new TreeMap<Integer, Statement>();
        variables = new HashMap<String, Expression>();
        forVariables = new HashMap<String, Boolean>();
        dimVariables = new HashMap<String, ArrayExpression>();
        dimValues = new HashMap<Integer, NumberExpression>();
        returnStack = new Stack<Integer>();
        dataQueue = new LinkedList<NumberExpression>();
        currentLine = null;
        running = true;
    }

    // * Tambah baris kod ke program
    public void addLine(int lineNumber, Statement statement) {
        programLines.put(lineNumber, statement);
    }

    // * Ammbil statement pada baris tertentu
    public Statement getStatement(int lineNumber) {
        return programLines.get(lineNumber);
    }

    public void setCurrentLine(int line) {
        if (!programLines.containsKey(line)) throw new RuntimeException("Line number " + line + " not found.");
        this.currentLine = line;
    }

    // * Simpan nilai variable baru
    public void defineVariable(String name, Expression value) {
        variables.put(name, value);
    }

    // * Simpan nilai variable yang dikemas kini
    public void assignVariable(String name, Expression value) {
        //if (!variables.containsKey(name)) throw new RuntimeException("Undefined variable: " + name);
        if (!variables.containsKey(name)) defineVariable(name, value); // Jika tidak wujud, terus define
        else variables.put(name, value);
    }

    // * Ambil nilai variable
    public Expression getVariable(String name) {
        return variables.get(name);
    }

    // * Cek sama ada variable wujud atau tidak wujud
    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    public void defineForVariable(String name, boolean value) {
        forVariables.put(name, value);
    }

    public boolean getForVariable(String name) {
        if (!hasForVariable(name)) throw new RuntimeException("Expected variable NEXT, but found: " + name);
        return forVariables.get(name);
    }

    public boolean hasForVariable(String name) {
        return forVariables.containsKey(name);
    }

    // fungsi: define DIM
    public void defineDimArray(String name, ArrayExpression array) {
        dimVariables.put(name, array);
        if (array.size() == 1) {
            for (int i = 1;i < array.X() + 1;i++) {
                dimValues.put(ArrayExpression.of(new IntegerExpression(i)), new IntegerExpression(0));
            }
        } else if (array.size() == 2) {
            for (int x = 1;x < array.X() + 1;x++) {
                for (int y = 1;y < array.Y() + 1;y++) {
                    dimValues.put(ArrayExpression.of(new IntegerExpression(x), new IntegerExpression(y)), new IntegerExpression(0));
                }
            }
        } else if (array.size() == 3) {
            for (int x = 1;x < array.X() + 1;x++) {
                for (int y = 1;y < array.Y() + 1;y++) {
                    for (int z = 1;z < array.Z() + 1;z++) {
                        dimValues.put(ArrayExpression.of(new IntegerExpression(x), new IntegerExpression(y), new IntegerExpression(z)), new IntegerExpression(0));
                    }
                }
            }
        } else {
            //if (array.size() == 1) throw new RuntimeException("Value index '" + array.X() + "' not defined");
            //else if (array.size() == 2) throw new RuntimeException("Value index '" + array.X() + "," + array.Y() + "' not defined");
            //else throw new RuntimeException("Value index '" + array.X() + "," + array.Y() + "," + array.Z() + "' not defined");
        }
    }

    // fungsi: setValue DIM
    public void setDimValue(String name, ArrayExpression array, NumberExpression value) {
        if (!hasDimArray(name)) throw new RuntimeException("Variable name '" + name + "' not defined");
        ArrayExpression index = dimVariables.get(name);
        if (array.size() == 1 && index.size() == 1) dimValues.put(ArrayExpression.of(new IntegerExpression(array.X())), value);
        else if (array.size() == 2 && index.size() == 2) dimValues.put(ArrayExpression.of(new IntegerExpression(array.X()), new IntegerExpression(array.Y())), value);
        else if (array.size() == 3 && index.size() == 3) dimValues.put(ArrayExpression.of(new IntegerExpression(array.X()), new IntegerExpression(array.Y()), new IntegerExpression(array.Z())), value);
        else {
            //if (array.size() == 1) throw new RuntimeException("Value index '" + array.X() + "' not defined");
            //else if (array.size() == 2) throw new RuntimeException("Value index '" + array.X() + "," + array.Y() + "' not defined");
            //else throw new RuntimeException("Value index '" + array.X() + "," + array.Y() + "," + array.Z() + "' not defined");
        }
    }

    // fungsi: getDimValue
    public NumberExpression getDimValue(String name, ArrayExpression array) {
        if (!hasDimArray(name)) throw new RuntimeException("Variable name '" + name + "' not defined");
        ArrayExpression index = dimVariables.get(name);
        if (array.size() == 1 && index.size() == 1) {
            return dimValues.get(ArrayExpression.of(new IntegerExpression(array.X())));
        } else if (array.size() == 2 && index.size() == 2) {
            return dimValues.get(ArrayExpression.of(new IntegerExpression(array.X()), new IntegerExpression(array.Y())));
        } else if (array.size() == 3 && index.size() == 3) {
            return dimValues.get(ArrayExpression.of(new IntegerExpression(array.X()), new IntegerExpression(array.Y()), new IntegerExpression(array.Z())));
        } else {
            //if (array.size() == 1) throw new RuntimeException("Value index '" + array.X() + "' not defined");
            //else if (array.size() == 2) throw new RuntimeException("Value index '" + array.X() + "," + array.Y() + "' not defined");
            //else throw new RuntimeException("Value index '" + array.X() + "," + array.Y() + "," + array.Z() + "' not defined");
            return null;
        }
    }

    public boolean hasDimArray(String name) {
        return dimVariables.containsKey(name);
    }

    public boolean hasDimValue(Expression index) {
        return dimValues.containsKey(index);
    }

    // * Dapatkan baris seterusnya
    public Integer getNextStatement() {
        if (programLines.isEmpty()) return null;
        if (!running) return null; // Jika END dipanggil, hentikan program
        if (nextLine != null) { // Jika ada GOTO, gunakan nilai ini
            Integer temp = nextLine;
            nextLine = null; // Reset selepas digunakan
            return temp;
        }
        if (currentLine == null) return programLines.firstKey();
        return programLines.higherKey(currentLine);
    }

    /*
    // * Jalankan program dari awal
    public void execute() {
        currentLine = programLines.firstKey();
        running = true; // Pastikan program berjalan
        while (currentLine != null) {
            Statement statement = programLines.get(currentLine);
            statement.execute(this);
            currentLine = getNextStatement();
        }
    }
     */

    // * Lompat ke baris tertentu (GOTO)
    public void jumpTo(int lineNumber) {
        if (!programLines.containsKey(lineNumber)) throw new RuntimeException("Line " + lineNumber + " not exist");
        currentLine = lineNumber;
    }

    // * Lompat ke subrutin (GOSUB)
    public void callSubroutine(int lineNumber) {
        if (!programLines.containsKey(lineNumber)) throw new RuntimeException("Line " + lineNumber + " not exist");
        returnStack.push(currentLine); // Simpan baris semasa sebelum lompat
        currentLine = lineNumber;
    }

    // * Kembali dar subrutin (RETURN)
    public void returnFromSubroutine() {
        if (returnStack.isEmpty()) throw new RuntimeException("RETURN without GOSUB");
        currentLine = returnStack.pop(); // Ambil baris terakhir dari stack
    }

    // * Simpan nilai DATA ke dalam queue
    public void storeData(ArrayList<NumberExpression> data) {
        dataQueue.addAll(data);
    }

    // * Ambil nilai DATA seterusnya untuk READ
    public Expression readNextData() {
        if (dataQueue.isEmpty()) {
            throw new RuntimeException("No DATA to READ");
            //System.out.println("Warning: No DATA available, retunring null");
            //return null;
        }
        dataQueue.element();
        return dataQueue.poll(); // Ambil nilai pertama dalam queue
    }

    public boolean isDataEmpty() {
        return dataQueue.isEmpty();
    }

    public boolean isRunning() {
        return running;
    }

    // * Hentikan program (END)
    public void stopExecution() {
        running = false; // Hentikan pelaksanaan program
    }

    // * Fungsi untuk GOTO
    public void setNextLine(Integer lineNumber) {
        this.nextLine = lineNumber;
    }

    // * Fungsi untuk GOSUB
    public void pushReturnAddress(Integer line) {
        returnStack.push(line);
    }

    // * Fungsi untuk RETURN
    public Integer popReturnAddress() {
        return returnStack.isEmpty() ? null : returnStack.pop();
    }

}
