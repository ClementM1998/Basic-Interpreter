
public class SeparatorExpression extends Expression {
    final String separator;

    public SeparatorExpression(String separator) {
        this.separator = separator;
    }

    public Expression evaluate(Environment env) {
        return this;
    }

}
