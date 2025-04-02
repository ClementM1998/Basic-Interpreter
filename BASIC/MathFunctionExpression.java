
public class MathFunctionExpression extends NumberExpression {
    private final String functionName;
    private final Expression expression;

    public MathFunctionExpression(String functionName, Expression expression) {
        this.functionName = functionName.toUpperCase();
        this.expression = expression;
    }

    public Expression evaluate(Environment env) {
        double x = 0.0;
        Expression value = expression.evaluate(env);
        if (value instanceof IntegerExpression) {
            x = new DoubleExpression((double) ((IntegerExpression) value).value).value;
        } else if (value instanceof DoubleExpression) {
            x = ((DoubleExpression) value).value;
        }
        switch (functionName) {
            case "ABS": return new AbsFuncExpression(x).evaluate(env);
            case "ATN": return new AtnFuncExpression(x).evaluate(env);
            case "COS": return new CosFuncExpression(x).evaluate(env);
            case "EXP": return new ExpFuncExpression(x).evaluate(env);
            case "INT": return new IntFuncExpression(x).evaluate(env);
            case "LOG": {
                if (x == 0) x = 1.0;
                return new LogFuncExpression(x).evaluate(env);
            }
            case "RND": return new RndFuncExpression((int) x).evaluate(env);
            case "SIN": return new SinFuncExpression(x).evaluate(env);
            case "SQR": {
                if (x == 0) x = 0.01;
                else x = 0.01;
                return new SqrFuncExpression(x).evaluate(env);
            }
            case "TAN": return new TanFuncExpression(x).evaluate(env);
            default: throw new RuntimeException("Unknown function: " + functionName);
        }
    }
}
