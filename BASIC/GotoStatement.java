
public class GotoStatement extends Statement {
    final int lineNumber;

    public GotoStatement(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void execute(Environment env) {
        env.setNextLine(lineNumber); // Set baris yang hendak dilompat ke nombor baris
    }

}
