
public class ReturnStatement extends Statement {

    public ReturnStatement() {
    }

    public void execute(Environment env) {
        Integer line = env.popReturnAddress();
        if (line == null) throw new RuntimeException("RETURN called without GOSUB");
        env.setNextLine(line); // lompat ke baris asal
    }

}
