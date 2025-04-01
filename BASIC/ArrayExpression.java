import java.util.ArrayList;

public class ArrayExpression extends Expression {
    private int X;
    private int Y;
    private int Z;
    private int size = 0;

    public ArrayExpression(IntegerExpression x) {
        this.X = x.value;
        this.Y = 0;
        this.Z = 0;
        this.size = 1;
    }

    public ArrayExpression(IntegerExpression x, IntegerExpression y) {
        this.X = x.value;
        this.Y = y.value;
        this.Z = 0;
        this.size = 2;
    }

    public ArrayExpression(IntegerExpression x, IntegerExpression y, IntegerExpression z) {
        this.X = x.value;
        this.Y = y.value;
        this.Z = z.value;
        this.size = 3;
    }

    public int X() {
        return this.X;
    }

    public int Y() {
        return this.Y;
    }

    public int Z() {
        return this.Z;
    }

    public static int of(IntegerExpression x) {
        return new IntegerExpression(x.value).value;
    }

    public static int of(IntegerExpression x, IntegerExpression y) {
        return new IntegerExpression((x.value + y.value)).value;
    }

    public static int of(IntegerExpression x, IntegerExpression y, IntegerExpression z) {
        return new IntegerExpression((x.value + y.value + z.value)).value;
    }

    public int size() {
        return this.size;
    }

    public Expression evaluate(Environment env) {
        return this;
    }

}
