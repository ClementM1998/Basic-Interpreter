
public class REMStatement extends Statement {
    private final String comment;

    public REMStatement(String comment) {
        this.comment = comment;
    }

    public void execute(Enviroment env) {
        // Tidak melakukan apa-apa, hanya komen
    }
}
