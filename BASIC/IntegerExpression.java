
public class IntegerExpression extends Expression {
    private final int value;

    public IntegerExpression(int value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        return this;
    }
  
}
