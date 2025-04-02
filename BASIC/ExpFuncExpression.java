
public class ExpFuncExpression extends Expression {
    final double value;

    public ExpFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        /*
        double sum = 1.0;
        double term = 1.0;
        for (int i = 1;i < 15;i++) {
            term *= value / i;
            sum += term;
        }
        return new DoubleExpression(sum);
         */
        return new DoubleExpression(BasicMath.exp(value));
    }

}
