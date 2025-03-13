
public class ComparisonExpression extends BinaryExpression {

    public enum Operator {
        EQUAL,          // =
        NOT_EQUAL,      // <>
        LESS,           // <
        GREATER,        // >
        LESS_EQUAL,     // <=
        GREATER_EQUAL   // >=
    }

    private final Operator operator;

    public ComparisonExpression(Expression left, Operator operator, Expression right) {
        super(left, right);
        this.operator = operator;
    }

    public Expression evaluate(Environment env) {
        Expression leftExp = left.evaluate(env);
        Expression rightExp = right.evaluate(env);

        switch (operator) {
            case EQUAL:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value == ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value == ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp).value == ((DoubleExpression) rightExp).value);
                return new BooleanExpression(((IntegerExpression) leftExp).value == ((IntegerExpression) rightExp).value);
            case NOT_EQUAL:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value != ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value != ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp).value != ((DoubleExpression) rightExp).value);
                return new BooleanExpression(((IntegerExpression) leftExp).value != ((IntegerExpression) rightExp).value);
            case LESS:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value < ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value < ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp).value < ((DoubleExpression) rightExp).value);
                return new BooleanExpression(((IntegerExpression) leftExp).value < ((IntegerExpression) rightExp).value);
            case GREATER:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value > ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value > ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp).value > ((DoubleExpression) rightExp).value);
                return new BooleanExpression(((IntegerExpression) leftExp).value > ((IntegerExpression) rightExp).value);
            case LESS_EQUAL:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value <= ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value <= ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp).value <= ((DoubleExpression) rightExp).value);
                return new BooleanExpression(((IntegerExpression) leftExp).value <= ((IntegerExpression) rightExp).value);
            case GREATER_EQUAL:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value >= ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp).value >= ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp).value >= ((DoubleExpression) rightExp).value);
                return new BooleanExpression(((IntegerExpression) leftExp).value >= ((IntegerExpression) rightExp).value);
            default: throw new RuntimeException("Unknown operator");
        }

    }
}
