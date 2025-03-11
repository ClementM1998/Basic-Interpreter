
public class GotoStatement extends Statement {
    private final int lineNumber;

    public GotoStatement(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void execute(Enviroment env) {
        env.setLineNumber(lineNumber);
    }
}
