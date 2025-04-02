
public class RemStatement extends Statement {
    private final String comment;

    public RemStatement(String comment) {
        this.comment = comment;
    }

    public void execute(Environment env) {
        // Tidak melakukan apa-apa, hanya komen
    }
}
