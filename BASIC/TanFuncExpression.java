
public class TanFuncExpression extends Expression {
    final double value;

    public TanFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        /*
        //double sin = ((SinFuncExpression) new SinFuncExpression(value).evaluate(env)).value;
        //double cos = ((CosFuncExpression) new CosFuncExpression(value).evaluate(env)).value;
        Expression expr1 = new SinFuncExpression(value).evaluate(env);
        double sin = (double) ((DoubleExpression) expr1.evaluate(env)).value;
        Expression expr2 = new CosFuncExpression(value).evaluate(env);
        double cos = (double) ((DoubleExpression) expr2.evaluate(env)).value;
        return new DoubleExpression(sin / cos);
         */
        return new DoubleExpression(BasicMath.tan(value));
    }

}
