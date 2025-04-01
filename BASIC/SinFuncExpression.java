
public class SinFuncExpression extends Expression {
    final double value;

    public SinFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        /*
        double term = value;
        double sum = value;
        double num = value;
        double den = 1.0;
        boolean negative = true;
        for (int i = 1;i < 10;i++) {
            num *= value * value;
            den *= (2 * i) * (2 * i + 1);
            term = num / den;
            sum += (negative ? -term : term);
            negative = !negative;
        }
        return new DoubleExpression(sum);
        */
        return new DoubleExpression(BasicMath.sin(value));
    }
}
