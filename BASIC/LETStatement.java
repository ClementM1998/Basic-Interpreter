
public class LETStatement extends Statement {
    private final String name;
    private final Expression expression;

    public LETStatement(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public void execute(Enviroment env) {
        Expression value = expression.evaluate(env);
        env.define(name, value);
    }
}
