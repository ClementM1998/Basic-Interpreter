import java.util.ArrayList;
import java.util.List;

public class DimStatement extends Statement {
    final String varname;
    final ArrayList<Expression> expressions;

    public DimStatement(String varname, ArrayList<Expression> expressions) {
        this.varname = varname;
        this.expressions = expressions;
    }

    public void execute(Environment env) {
        if (expressions.size() == 1) { // Contoh: A( 5 )
            Expression expr = expressions.get(0).evaluate(env);
            IntegerExpression x = (IntegerExpression) expr;
            env.defineDimArray(varname, new ArrayExpression(x));
        } else if (expressions.size() == 2) { // Contoh: A( 5 , 5 )
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            IntegerExpression y = (IntegerExpression) expressions.get(1).evaluate(env);
            env.defineDimArray(varname, new ArrayExpression(x, y));
        } else if (expressions.size() == 3) { // Contoh: A( 5 , 5 , 5 )
            IntegerExpression x = (IntegerExpression) expressions.get(0).evaluate(env);
            IntegerExpression y = (IntegerExpression) expressions.get(1).evaluate(env);
            IntegerExpression z = (IntegerExpression) expressions.get(2).evaluate(env);
            env.defineDimArray(varname, new ArrayExpression(x, y, z));
        }
        //ArrayExpression array = new ArrayExpression(new IntegerExpression(0));
        //env.defineDimArray(varname, array);
    }

}
