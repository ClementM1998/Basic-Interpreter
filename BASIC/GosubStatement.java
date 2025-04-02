
public class GosubStatement extends Statement {
    final int lineNumber;

    public GosubStatement(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void execute(Environment env) {
        Integer line = env.getNextStatement();
        if (line != null) env.pushReturnAddress(line); // Simpan baris asal sebelum lompat
        env.setNextLine(lineNumber);
    }

}
