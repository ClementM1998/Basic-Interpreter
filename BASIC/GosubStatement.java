
public class GosubStatement extends Statement {
    final int lineNumber;

    public GosubStatement(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void execute(Environment env) {
        env.callSubroutine(lineNumber);
    }

}
