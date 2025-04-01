
public class AbsFuncExpression extends Expression {
    final double value;

    public AbsFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        //return new DoubleExpression(value < 0 ? -value : value);
        return new DoubleExpression(BasicMath.abs(value));
    }

}
