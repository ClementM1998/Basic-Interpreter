
public class IntegerExpression extends NumberExpression {
    final int value;

    public IntegerExpression(int value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        return this;
    }

}
