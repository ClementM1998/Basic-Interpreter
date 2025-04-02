
/*
 * 1. Kata kunci (Keywords)
 *
 *     LET
 *     PRINT
 *     INPUT
 *     IF
 *     THEN
 *     GOTO
 *     FOR
 *     TO
 *     STEP
 *     NEXT
 *     END
 *     STOP
 *     DIM
 *     READ
 *     DATA
 *     RESTORE
 *
 * 2. Operator
 *
 *    Aritmetik
 *     + (Tambah)
 *     - (Tolak)
 *     * (Darab)
 *     / (Bahagi)
 *     ^ (Pangkat)
 *
 *    Perbandingan
 *     = (Sama dengan)
 *     <> (Tidak sama)
 *     > (Lebih besar)
 *     < (Lebih kecil)
 *     >= (Lebih besar atau sama)
 *     <= (Lebih kecil atau sama)
 *
 *    Logik (untuk IF-THEN)
 *     AND
 *     OR
 *     NOT
 *
 * 3. Identifiers (Nama Pemboleh ubah dan Fungsi)
 *
 *    Nama pemboleh ubah biasanya huruf dan nombor
 *     A
 *     X
 *     Y1
 *     Z2
 *
 *    Nama fungsi terbina dalam
 *     SIN
 *     COS
 *     TAN
 *     ABS
 *     SQR
 *     LOG
 *     EXP
 *     RND
 *
 * 4. Nombor (Literals)
 *
 *    Boleh dalam bentuk integer atau floating-point
 *     100
 *     -5
 *     3.14
 *     2.718
 *
 * 5. String (Teks)
 *
 *    10 PRINT "HELLO, WORLD"
 *
 * 6. Simbol Khas
 *
 *     , (pemisah PRINT)
 *     ; (penghubung PRINT)
 *     ( ) (kurungan untuk fungsi
 *
 *
 */
public class Token {
    private Type type; // untuk jenis kod
    private String data; // untuk nilai kod

    public Token(Type type, String data) {
        this.type = type;
        this.data = data;
    }

    public Type type() {
        return this.type;
    }

    public String data() {
        return this.data;
    }

    @Override
    public String toString() {
        return "[" + type + ": " + data + "]";
    }
}
