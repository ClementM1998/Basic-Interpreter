
public class GotoStatement extends Statement {
    final int lineNumber;

    public GotoStatement(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void execute(Environment env) {
        env.jumpTo(lineNumber);
    }

}
