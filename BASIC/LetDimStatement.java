import java.util.ArrayList;

public class LetDimStatement extends Statement {
    final String varname;
    final ArrayList<Expression> expressions;
    final NumberExpression value;

    public LetDimStatement(String varname, ArrayList<Expression> expressions, NumberExpression value) {
        this.varname = varname;
        this.expressions = expressions;
        this.value = value;
    }

    public void execute(Environment env) {
        if (expressions.size() == 1) {
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            if (value instanceof DoubleExpression) env.setDimValue(varname, new ArrayExpression(x), (DoubleExpression) value.evaluate(env));
            else env.setDimValue(varname, new ArrayExpression(x), (IntegerExpression) value.evaluate(env));
        } else if (expressions.size() == 2) {
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            IntegerExpression y = (IntegerExpression) expressions.get(1).evaluate(env);
            if (value instanceof DoubleExpression) env.setDimValue(varname, new ArrayExpression(x, y), (DoubleExpression) value.evaluate(env));
            else env.setDimValue(varname, new ArrayExpression(x, y), (IntegerExpression) value.evaluate(env));
        } else if (expressions.size() == 3){
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            IntegerExpression y = (IntegerExpression) expressions.get(1).evaluate(env);
            IntegerExpression z = (IntegerExpression) expressions.get(2).evaluate(env);
            if (value instanceof DoubleExpression) env.setDimValue(varname, new ArrayExpression(x, y, z), (DoubleExpression) value.evaluate(env));
            else env.setDimValue(varname, new ArrayExpression(x, y, z), (IntegerExpression) value.evaluate(env));
        }
        //env.setDimValue(varname, array, value);
    }
}
