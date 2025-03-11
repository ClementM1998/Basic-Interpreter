import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Enviroment {
    private final Map<String, Expression> variables = new HashMap<String, Expression>();
    private int currentLine = 0;
    private Map<Integer, Statement> program = new HashMap<Integer, Statement>();
    private Stack<Integer> returnStack = new Stack<Integer>();

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

    public void setLineNumber(int line) {
        if (!program.containsKey(line)) throw new RuntimeException("Line number " + line + " not found.");
        this.currentLine = line;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void addLine(int line, Statement statement) {
        program.put(line, statement);
    }

    public Statement getStatement(int line) {
        return program.get(line);
    }

    public void pushReturnAddress(int line) {
        returnStack.push(line);
    }

    public int popReturnAddress() {
        if (returnStack.isEmpty()) throw new RuntimeException("RETURN without GOSUB");
        return returnStack.pop();
    }
}
