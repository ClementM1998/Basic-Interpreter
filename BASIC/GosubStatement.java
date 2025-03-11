
public class GosubStatement extends Statement {
    private final int lineNumber;

    public GosubStatement(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void execute(Enviroment env) {
        env.pushReturnAddress(env.getCurrentLine()); // Simpan baris semasa sebelum lompat
        env.setLineNumber(lineNumber); // Lompat ke baris subrutin
    }

}
