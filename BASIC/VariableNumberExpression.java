
public class VariableNumberExpression extends NumberExpression {
    final String name;

    public VariableNumberExpression(String name) {
        this.name = name;
    }

    public Expression evaluate(Environment env) {
        return env.getVariable(name);
    }

}
