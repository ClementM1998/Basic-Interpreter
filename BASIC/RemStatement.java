
public class RemStatement extends Statement {
    private final String comment;

    public RemStatement(String comment) {
        this.comment = comment;
    }

    public void execute(Enviroment env) {
        // Tidak melakukan apa-apa, hanya komen
    }
}
