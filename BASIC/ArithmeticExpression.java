
public class ArithmeticExpression extends BinaryExpression {

    public enum Operator {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE
    }

    final Operator operator;

    public ArithmeticExpression(Expression left, Operator operator, Expression right) {
        super(left, right);
        this.operator = operator;
    }

    public Expression evaluate(Environment env) {
        Expression leftExp = left.evaluate(env);
        Expression rightExp = right.evaluate(env);

        double leftValue = extractValue(leftExp, env);
        double rightValue = extractValue(rightExp, env);

        double result;
        switch (operator) {
            case ADD:
                result = leftValue + rightValue;
                break;
            case SUBTRACT:
                result = leftValue - rightValue;
                break;
            case MULTIPLY:
                result = leftValue * rightValue;
                break;
            case DIVIDE:
                if (rightValue == 0) throw new ArithmeticException("Division by zero");
                result = leftValue / rightValue;
                break;
            default:
                throw new RuntimeException("Unknown operator");
        }

        String value = String.valueOf(result);
        if (lastValue(value)) return new DoubleExpression(Double.parseDouble(value));
        else return (leftExp instanceof IntegerExpression && rightExp instanceof IntegerExpression) ? new IntegerExpression((int) result) : new DoubleExpression(result);
    }

    private double extractValue(Expression exp, Environment env) {
        Expression ext = exp.evaluate(env);
        if (ext instanceof DoubleExpression) return ((DoubleExpression) exp.evaluate(env)).value;
        if (ext instanceof IntegerExpression) return ((IntegerExpression) exp.evaluate(env)).value;
        throw new RuntimeException("Unsupported expression type");
    }

    private boolean lastValue(String value) {
        if (value.contains(".")) {
            value = value.substring(value.lastIndexOf(".") + 1);
            if (Long.valueOf(value).equals(0)) return false;
            return true;
        } else return false;
    }

}
