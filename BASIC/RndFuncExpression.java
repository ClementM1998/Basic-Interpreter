
public class RndFuncExpression extends Expression {
    final int value;
    static long seed = 1;

    public RndFuncExpression(int value) {
        this.value = value;
    }

    public Expression evaluate(Environment env) {
        //return new DoubleExpression( rnd(value) );
        return new DoubleExpression(BasicMath.rnd(value));
    }

    private double rnd(int value) {
        if (value > 0) {
            // value > 0 : Berikan nombor rawak baru
            seed = (seed * 1664525 + 1013904223) & 0xFFFFFFFFL; // LCG formula
            return (double) seed / 0xFFFFFFFF;
        } else if (value == 0) {
            // Value = 0 : Sentiasa beri nombor tetap (contoh: 0.5)
            return 0.5;
        } else {
            // value < 0 : Tetapkan seed baru
            seed = -value; // Seed diubah berdasarkan nilai negatif value
            return rnd(1); // Berikan nombor rawak pertama selepas seed ditetapkan
        }
    }

}
