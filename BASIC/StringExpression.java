
public class StringExpression extends Expression {
    private final String value;

    public StringExpression(String value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        return this;
    }
    
}
