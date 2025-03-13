
public abstract class BinaryExpression extends Expression {
    final Expression left;
    final Expression right;

    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
}
