
public class VariableExpression extends Expression {
    final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    public Expression evaluate(Enviroment env) {
        return env.get(name);
    }

}
