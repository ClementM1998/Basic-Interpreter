
public class LogFuncExpression extends Expression {
    final double value;

    public LogFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        /*
        if (value <= 0) throw new ArithmeticException("Log undefined for x <= 0");
        double y = (value - 1) / (value + 1);
        double sum = 0.0;
        double num = y;
        for (int i = 1;i < 15;i += 2) {
            sum += num / i;
            num *= y * y;
        }
        return new DoubleExpression(2 * num);
         */
        return new DoubleExpression(BasicMath.log(value));
    }

}
