
public class ReadStatement extends Statement {
    private final String name;

    public ReadStatement(String name) {
        this.name = name;
    }

    public void execute(Environment env) {
        Expression value = env.getNextData();
        env.assign(name, value);
    }
    
}
