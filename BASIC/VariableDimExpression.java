import java.util.ArrayList;

public class VariableDimExpression extends Expression {
    final String varname;
    final ArrayList<Expression> expressions;

    public VariableDimExpression(String varname, ArrayList<Expression> expressions) {
        this.varname = varname;
        this.expressions = expressions;
    }

    public Expression evaluate(Environment env) {
        if (expressions.size() == 1) {
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            return env.getDimValue(varname, new ArrayExpression(x));
        } else if (expressions.size() == 2) {
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            IntegerExpression y = (IntegerExpression) expressions.get(1).evaluate(env);
            return env.getDimValue(varname, new ArrayExpression(x, y));
        } else { // if (expressions.size() == 3) {
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            IntegerExpression y = (IntegerExpression) expressions.get(1).evaluate(env);
            IntegerExpression z = (IntegerExpression) expressions.get(2).evaluate(env);
            return env.getDimValue(varname, new ArrayExpression(x, y, z));
        }
    }

}
