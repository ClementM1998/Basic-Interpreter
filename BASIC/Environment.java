import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

public class Environment {
    /*
    private final HashMap<String, Expression> variables = new HashMap<String, Expression>();
    private Integer currentLine = null;
    private TreeMap<Integer, Statement> programLines = new TreeMap<Integer, Statement>();
    private Stack<Integer> returnStack = new Stack<Integer>();
    private Queue<Expression> dataQueue = new LinkedList<Expression>();
    private boolean running = true; // Default: program berjalan

    public void define(String name, Expression value) {
        variables.put(name, value);
    }

    public Expression get(String name) {
        if (!variables.containsKey(name)) throw new RuntimeException("Undefined variable: " + name);
        return variables.get(name);
    }

    public void assign(String name, Expression value) {
        if (!variables.containsKey(name)) throw new RuntimeException("Undefined variable: " + name);
        variables.put(name, value);
    }

    public void setCurrentLine(int line) {
        if (!programLines.containsKey(line)) throw new RuntimeException("Line number " + line + " not found.");
        this.currentLine = line;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void addLine(int line, Statement statement) {
        programLines.put(line, statement);
    }

    public Statement getStatement(int line) {
        return programLines.get(line);
    }

    public Integer getFirstLine() {
        return programLines.isEmpty() ? null : programLines.firstKey();
    }

    public Integer getNextStatement() {
        if (currentLine == null) return getFirstLine();
        return programLines.higherKey(currentLine);
    }

    public void pushReturnAddress(int line) {
        returnStack.push(line);
    }

    public int popReturnAddress() {
        if (returnStack.isEmpty()) throw new RuntimeException("RETURN without GOSUB");
        return returnStack.pop();
    }

    public void addData(ArrayList<Expression> data) {
        dataQueue.addAll(data);
    }

    public Expression getNextData() {
        if (dataQueue.isEmpty()) throw new RuntimeException("Out of Data");
        Expression exp = dataQueue.poll();
        try {
            if (exp instanceof DoubleExpression) return (DoubleExpression) exp;
            return (IntegerExpression) exp;
        } catch (NumberFormatException e) {
            return (StringExpression) exp;
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isRunning() {
        return this.running;
    }
     */

    private final TreeMap<Integer, Statement> programLines;
    private final Map<String, Expression> variables;
    private final Stack<Integer> returnStack;
    private final Queue<Expression> dataQueue;
    private Integer currentLine = null;
    private boolean running;

    public Environment() {
        programLines = new TreeMap<Integer, Statement>();
        variables = new HashMap<String, Expression>();
        returnStack = new Stack<Integer>();
        dataQueue = new LinkedList<Expression>();
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
    public void define(String name, Expression value) {
        variables.put(name, value);
    }

    // * Simpan nilai variable yang dikemas kini
    public void assign(String name, Expression value) {
        if (!variables.containsKey(name)) throw new RuntimeException("Undefined variable: " + name);
        variables.put(name, value);
    }

    // * Ambil nilai variable
    public Expression getVariable(String name) {
        return variables.get(name);
    }

    // * Dapatkan baris seterusnya
    public Integer getNextStatement() {
        if (programLines.isEmpty()) return null;
        if (!running) return null; // Jika END dipanggil, hentikan program
        if (currentLine == null) return programLines.firstKey();
        return programLines.higherKey(currentLine);
    }

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

    // * Kemali dar subrutin (RETURN)
    public void returnFromSubroutine() {
        if (returnStack.isEmpty()) throw new RuntimeException("RETURN without GOSUB");
        currentLine = returnStack.pop(); // Ambil baris terakhir dari stack
    }

    // * Simpan nilai DATA ke dalam queue
    public void storeData(ArrayList<Expression> data) {
        dataQueue.addAll(data);
    }

    // * Ambil nilai DATA seterusnya untuk READ
    public Expression readNextData() {
        if (dataQueue.isEmpty()) throw new RuntimeException("No DATA to READ");
        return dataQueue.poll(); // Ambil nilai pertama dalam queue
    }

    public boolean isRunning() {
        return running;
    }

    // * Hentikan program (END)
    public void stopExecution() {
        running = false; // Hentikan pelaksanaan program
    }

}
