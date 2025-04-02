
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
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value == ((DoubleExpression) rightExp.evaluate(env)).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value == ((IntegerExpression) rightExp.evaluate(env)).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value == ((DoubleExpression) rightExp.evaluate(env)).value);
                return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value == ((IntegerExpression) rightExp.evaluate(env)).value);
            case NOT_EQUAL:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value != ((DoubleExpression) rightExp.evaluate(env)).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value != ((IntegerExpression) rightExp.evaluate(env)).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value != ((DoubleExpression) rightExp.evaluate(env)).value);
                return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value != ((IntegerExpression) rightExp.evaluate(env)).value);
            case LESS:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value < ((DoubleExpression) rightExp.evaluate(env)).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value < ((IntegerExpression) rightExp.evaluate(env)).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value < ((DoubleExpression) rightExp.evaluate(env)).value);
                return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value < ((IntegerExpression) rightExp.evaluate(env)).value);
            case GREATER:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value > ((DoubleExpression) rightExp.evaluate(env)).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value > ((IntegerExpression) rightExp.evaluate(env)).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value > ((DoubleExpression) rightExp.evaluate(env)).value);
                return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value > ((IntegerExpression) rightExp.evaluate(env)).value);
            case LESS_EQUAL:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value <= ((DoubleExpression) rightExp.evaluate(env)).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value <= ((IntegerExpression) rightExp.evaluate(env)).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value <= ((DoubleExpression) rightExp.evaluate(env)).value);
                return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value <= ((IntegerExpression) rightExp.evaluate(env)).value);
            case GREATER_EQUAL:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value >= ((DoubleExpression) rightExp.evaluate(env)).value);
                else if (leftExp instanceof DoubleExpression) return new BooleanExpression(((DoubleExpression) leftExp.evaluate(env)).value >= ((IntegerExpression) rightExp.evaluate(env)).value);
                else if (rightExp instanceof DoubleExpression) return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value >= ((DoubleExpression) rightExp.evaluate(env)).value);
                return new BooleanExpression(((IntegerExpression) leftExp.evaluate(env)).value >= ((IntegerExpression) rightExp.evaluate(env)).value);
            default: throw new RuntimeException("Unknown operator: " + operator);
        }

    }
}
