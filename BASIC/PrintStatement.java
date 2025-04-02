import java.util.ArrayList;
import java.util.List;

public class PrintStatement extends Statement {
    private final ArrayList<Expression> expressions;

    public PrintStatement(ArrayList<Expression> expression) {
        this.expressions = expression;
    }

    public void execute(Environment env) {
        for (int i = 0; i < expressions.size(); i++) {
            Expression value = expressions.get(i).evaluate(env);

            // Papar nilai berdasarkan jenis data
            if (value instanceof DoubleExpression) System.out.print(((DoubleExpression) value).value);
            else if (value instanceof IntegerExpression) System.out.print(((IntegerExpression) value).value);
            else if (value instanceof StringExpression) System.out.print(((StringExpression) value).value);
            else if (value instanceof ArithmeticExpression) {
                Expression expr = value.evaluate(env);
                if (expr instanceof DoubleExpression) System.out.print(((DoubleExpression) expr).value);
                else if (expr instanceof IntegerExpression) System.out.print(((IntegerExpression) expr).value);
            }

            // Jika ada elemen seterusnya, periksa pemisah
            if (i < expressions.size()) {
                Expression next = expressions.get(i);

                if (next instanceof SeparatorExpression) {
                    SeparatorExpression separator = (SeparatorExpression) next;
                    if ((separator.separator.equals(";"))) continue; // ';' tidak menambah ruang
                    else if (separator.separator.equals(",")) System.out.print(" "); // ',' menambah tab
                }
            }
        }
        System.out.println();
    }

}
