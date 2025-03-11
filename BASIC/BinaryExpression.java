
public class BinaryExpression extends Expression {
    final Expression left;
    final String operator;
    final Expression right;

    public BinaryExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression evaluate(Enviroment env) {
        return null;
    }
}
