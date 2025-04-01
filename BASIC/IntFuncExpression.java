
public class IntFuncExpression extends Expression {
    final double value;

    public IntFuncExpression(double value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        //return new IntegerExpression( value >= 0 ? (int) value : (int) value - 1);
        return new IntegerExpression(BasicMath.intVal(value));
    }

}
