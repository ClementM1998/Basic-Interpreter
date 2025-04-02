
public class AtnFuncExpression extends Expression {
    final double value;

    public AtnFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        /*
        double sum = 0.0;
        double num = value;
        double den = 1.0;
        boolean negative = false;
        for (int i = 0;i < 10;i++) {
            sum += (negative ? -num / den : num / den);
            num *= value * value;
            den += 2.0;
            negative = !negative;
        }
        return new DoubleExpression(sum);
         */
        return new DoubleExpression(BasicMath.atn(value));
    }

}
