
public class IfStatement extends Statement {
    final Expression condition;
    final Statement thenBranch;

    public IfStatement(Expression condition, Statement thenBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
    }

    public void execute(Environment env) {
        if (((BooleanExpression) condition.evaluate(env)).value) thenBranch.execute(env);
    }
}
