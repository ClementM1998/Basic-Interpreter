
public class BooleanExpression extends Expression {
    final boolean value;

    public BooleanExpression(boolean value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        return this;
    }
  
}
