
public class DoubleExpression extends NumberExpression {
    private final double value;

    public DoubleExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        return this;
    }
    
}
