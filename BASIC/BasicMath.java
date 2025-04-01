
public class BasicMath {

    // SIN(x) menggunakan deret Taylor
    public static double sin(double x) {
        x = toRadian(x);
        double result = 0;
        double term = x;
        int n = 1;

        for (int i = 1; i <= 10; i++) { // 10 iterasi cukup untuk ketepatan asas
            result += term;
            term *= -x * x / ((2 * n) * (2 * n + 1));
            n++;
        }
        return result;
    }

    // COS(x) menggunakan deret Taylor
    public static double cos(double x) {
        x = toRadian(x);
        double result = 1;
        double term = 1;
        int n = 1;

        for (int i = 1; i <= 10; i++) {
            term *= -x * x / ((2 * n - 1) * (2 * n));
            result += term;
            n++;
        }
        return result;
    }

    // TAN(x) = SIN(x) / COS(x)
    public static double tan(double x) {
        return sin(x) / cos(x);
    }

    // ATN(x) menggunakan siri Gregory-Leibniz
    public static double atn(double x) {
        double result = 0;
        double term = x;
        int sign = 1;

        for (int i = 1; i <= 15; i += 2) {
            result += sign * term / i;
            term *= x * x;
            sign *= -1;
        }
        return result;
    }

    // EXP(x) = e^x menggunakan deret Taylor
    public static double exp(double x) {
        double result = 1;
        double term = 1;

        for (int i = 1; i <= 15; i++) {
            term *= x / i;
            result += term;
        }
        return result;
    }

    // LOG(x) menggunakan siri Taylor (x > 0)
    public static double log(double x) {
        if (x <= 0) throw new IllegalArgumentException("LOG(x) requires x > 0");

        double y = (x - 1) / (x + 1);
        double y2 = y * y;
        double result = 0;
        double term = y;

        for (int i = 1; i <= 15; i += 2) {
            result += term / i;
            term *= y2;
        }
        return result * 2;
    }

    // SQR(x) menggunakan kaedah Babylonian
    public static double sqr(double x) {
        if (x < 0) throw new IllegalArgumentException("SQR(x) requires x >= 0");
        double guess = x;
        for (int i = 0; i < 10; i++) {
            guess = (guess + x / guess) / 2;
        }
        return guess;
    }

    // ABS(x) = Nilai mutlak
    public static double abs(double x) {
        return (x < 0) ? -x : x;
    }

    // INT(x) = Bulatkan ke bawah
    public static int intVal(double x) {
        return (x >= 0) ? (int) x : (int) (x - 1);
    }

    // RND() = Nombor rawak antara 0 dan 1 menggunakan LCG
    private static long seed = 1;
    public static double rnd(int x) {
        if (x < 0) {
            seed = x; // Set seed jika negatif
            return rnd(1);
        } else if (x == 0) {
            return rndValue(seed); // Hasil tetap untuk RND(0)
        } else {
            seed = (1664525 * seed + 1013904223) % (1L << 32); // LCG formula
            return (seed & 0x7FFFFFFF) / (double) 0x7FFFFFFF;
        }
    }

    // Tukar darjah kepada radian
    private static double toRadian(double degree) {
        return degree * 3.14159265358979 / 180.0;
    }

    private static double rndValue(long s) {
        return ((1664525 * s + 1013904223) % (1L << 32) & 0x7FFFFFFF) / (double) 0x7FFFFFFF;
    }

}
