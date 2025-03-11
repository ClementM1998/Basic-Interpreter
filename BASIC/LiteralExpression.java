
public class LiteralExpression extends Expression {
    private final Expression value;

    public LiteralExpression(Expression value) {
        this.value = value;
    }

    public Expression evaluate(Enviroment env) {
        return value;
    }
}
