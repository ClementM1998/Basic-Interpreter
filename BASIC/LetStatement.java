
public class LetStatement extends Statement {
    final String name;
    final NumberExpression expression;

    public LetStatement(String name, NumberExpression expression) {
        this.name = name;
        this.expression = expression;
    }

    public void execute(Environment env) {
        Expression value = expression.evaluate(env);
        env.defineVariable(name, value);
    }
}
