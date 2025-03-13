
public class PrintStatement extends Statement {
    private final Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    public void execute(Environment env) {
        Expression value = expression.evaluate(env);

        if (value instanceof DoubleExpression) System.out.println(((DoubleExpression) value).value);
        else if (value instanceof IntegerExpression) System.out.println(((IntegerExpression) value).value);
        else System.out.println(((StringExpression) value).value);
    }

}
