
public class ConditionExpression extends Expression {
    private final Expression left;
    private final String operator;
    private final Expression right;

    public ConditionExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression evaluate(Enviroment env) {
        /*
        double leftValue = (double) left.evaluate(env);
        double rightValue = (double) right.evaluate(env);
        boolean condition = false;

        return switch (operator) {
            case ">" -> leftValue > rightValue;
            case "<" -> leftValue < rightValue;
            case "=" -> leftValue == rightValue;
            default -> throw new RuntimeException("Unknown operator: " + operator);
        };
         */
        return null;
    }
}
