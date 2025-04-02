
public class EndStatement extends Statement {

    public EndStatement() {
    }

    public void execute(Environment env) {
        env.stopExecution(); // Hentikan program
    }

}
