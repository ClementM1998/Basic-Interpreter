
public class NextStatement extends Statement {
    private final String varName;

    public NextStatement(String varName) {
        this.varName = varName;
    }

    public void execute(Environment env) {
        if (env.hasForVariable(varName)) {
            env.defineForVariable(varName, false);
        } else if (!env.hasVariable(varName)) {
            System.out.println("Expected NEXT variable, but found: " + varName);
        }
    }

}
