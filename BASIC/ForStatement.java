
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ForStatement extends Statement {
    final String varName;
    final Expression startExpression;
    final Expression endExpression;
    final Expression stepExpression;
    final TreeMap<Integer, Statement> body;
    final ArrayList<Statement> bodyFor;

    public ForStatement(String varName, Expression startExpression, Expression endExpression, Expression stepExpression, TreeMap<Integer, Statement> body) {
        this.varName = varName;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
        this.body = body;
        this.bodyFor = new ArrayList<>(body.values());
    }

    public void execute(Environment env) {
        env.defineForVariable(varName, true);

        //int start = ((IntegerExpression) startExpression.evaluate(env)).value;
        //int end = ((IntegerExpression) endExpression.evaluate(env)).value;
        //int step = ((IntegerExpression) stepExpression.evaluate(env)).value;

        int start = 0;
        int end = 0;
        int step = 0;

        if (startExpression instanceof IntegerExpression) start = ((IntegerExpression) startExpression.evaluate(env)).value;
        else {
            Expression expression = startExpression.evaluate(env);
            start = ((IntegerExpression) expression.evaluate(env)).value;
        }

        if (endExpression instanceof IntegerExpression) end = ((IntegerExpression) endExpression.evaluate(env)).value;
        else {
            Expression expression = endExpression.evaluate(env);
            end = ((IntegerExpression) expression.evaluate(env)).value;
        }

        if (stepExpression instanceof IntegerExpression) step = ((IntegerExpression) stepExpression.evaluate(env)).value;
        else {
            Expression expression = stepExpression.evaluate(env);
            step = ((IntegerExpression) expression.evaluate(env)).value;
        }

        if ((step > 0 && start <= end) || (step < 0 && start >= end)) {
            for (int i = start;(step > 0 ? i <= end : i >= end);i += step) {
                env.defineVariable(varName, new IntegerExpression(i));
                for (Map.Entry<Integer, Statement> stmt : body.entrySet()) stmt.getValue().execute(env);
            }
        }

    }

}
