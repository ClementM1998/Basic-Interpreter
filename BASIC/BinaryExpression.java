
public abstract class BinaryExpression extends NumberExpression {
    final Expression left;
    final Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

}
