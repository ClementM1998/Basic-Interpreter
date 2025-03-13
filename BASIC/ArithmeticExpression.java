
public class ArithmeticExpression extends BinaryExpression {

    public enum Operator {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        POWER
    }

    final Operator operator;

    public ArithmeticExpression(Expression left, Operator operator, Expression right) {
        super(left, right);
        this.operator = operator;
    }

    public Expression evaluate(Environment env) {
        Expression leftExp = left.evaluate(env);
        Expression rightExp = right.evaluate(env);

        switch (operator) {
            case ADD:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value + ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value + ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new DoubleExpression(((IntegerExpression) leftExp).value + ((DoubleExpression) rightExp).value);
                else return new IntegerExpression(((IntegerExpression) leftExp).value + ((IntegerExpression) rightExp).value);
            case SUBTRACT:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value - ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value - ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new DoubleExpression(((IntegerExpression) leftExp).value - ((DoubleExpression) rightExp).value);
                else return new IntegerExpression(((IntegerExpression) leftExp).value - ((IntegerExpression) rightExp).value);
            case MULTIPLY:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value * ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value * ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new DoubleExpression(((IntegerExpression) leftExp).value * ((DoubleExpression) rightExp).value);
                else return new IntegerExpression(((IntegerExpression) leftExp).value * ((IntegerExpression) rightExp).value);
            case DIVIDE:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value / ((DoubleExpression) rightExp).value);
                else if (leftExp instanceof DoubleExpression) return new DoubleExpression(((DoubleExpression) leftExp).value / ((IntegerExpression) rightExp).value);
                else if (rightExp instanceof DoubleExpression) return new DoubleExpression(((IntegerExpression) leftExp).value / ((DoubleExpression) rightExp).value);
                else return new IntegerExpression(((IntegerExpression) leftExp).value / ((IntegerExpression) rightExp).value);
            case POWER:
                if (leftExp instanceof DoubleExpression && rightExp instanceof DoubleExpression) return new DoubleExpression(Math.pow(((DoubleExpression) leftExp).value , ((DoubleExpression) rightExp).value));
                else if (leftExp instanceof DoubleExpression) return new DoubleExpression(Math.pow(((DoubleExpression) leftExp).value , ((IntegerExpression) rightExp).value));
                else if (rightExp instanceof DoubleExpression) return new DoubleExpression(Math.pow(((IntegerExpression) leftExp).value , ((DoubleExpression) rightExp).value));
                else return new DoubleExpression(Math.pow(((IntegerExpression) leftExp).value , ((IntegerExpression) rightExp).value));
            default: throw new RuntimeException("Unknown operator");
        }

    }

}
