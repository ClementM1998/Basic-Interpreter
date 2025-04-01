
public class CosFuncExpression extends Expression {
    final double value;

    public CosFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        /*
        double term = 1.0;
        double sum = 1.0;
        double num = 1.0;
        double den = 1.0;
        boolean negative = true;
        for (int i = 1;i < 10;i++) {
            num *= value * value;
            den *= (2 * i - 1) * (2 * i);
            term = num / den;
            sum += (negative ? -term : term);
            negative = !negative;
        }
        return new DoubleExpression(sum);
         */
        return new DoubleExpression(BasicMath.cos(value));
    }

}
