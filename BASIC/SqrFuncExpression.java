
public class SqrFuncExpression extends Expression {
    final double value;

    public SqrFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        /*
        if (value < 0) throw new ArithmeticException("Square root of negative number is undefined");
        double guess = value;
        for (int i = 0;i < 10;i++) {
            guess = (guess + value / guess) / 2;
        }
        return new DoubleExpression(guess);
         */
        return new DoubleExpression(BasicMath.sqr(value));
    }

}
