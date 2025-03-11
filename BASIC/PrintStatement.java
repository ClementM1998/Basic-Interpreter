
public class PrintStatement extends Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    public void execute(Enviroment env) {
        Object value = expression.evaluate(env);
        System.out.println(value);
    }
}
