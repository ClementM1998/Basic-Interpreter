import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BasicTest {
    private String directory;

    public BasicTest(String directory) {
        this.directory = directory;

        writeFile("example1.bas", "10 PRINT \"EXAMPLE 1\"\n20 PRINT \"HELLO, WORLD!\"\n30 GOTO 20\n40 END");
        writeFile("example2.bas", "10   PRINT \" \"\n20   PRINT \" [2][2][2][2]                                    [1]\"\n30   PRINT \" [2]         [2]\"\n40   PRINT \" [2]         [2]       [3][3][3]      [4][4][4]  [5]    [6][6][6]\"\n50   PRINT \" [2][2][2][2][2]               [3]  [4]          [5]  [6]       [6]\"\n60   PRINT \" [2]           [2]    [7][7][7][3]  [4][4]       [5]  [6]\"\n70   PRINT \" [2]           [2]  [7]        [3]       [4][4]  [5]  [6]\"\n80   PRINT \" [2]           [2]  [7]        [3]          [4]  [5]  [6]       [6]\"\n90   PRINT \" [2][2][2][2][2]      [7][7][7][7]  [8][8][4]    [5]    [6][6][6]    1964\"\n100  PRINT \"\"");
        writeFile("example3.bas", "10 READ X, Y, Z\n20 PRINT \"X = \"; X\n30 PRINT \"Y = \"; Y\n40 PRINT \"Z = \"; Z\n50 READ A, B\n60 PRINT \"A = \"; A\n70 PRINT \"B = \"; B\n80 DATA -5, 15, 25, 50, 75");
        writeFile("example4.bas", "10 LET    x = ( 7 + 8 ) / 3\n20 PRINT  X\n30 END");
        writeFile("example5.bas", "10 FOR X = 1 TO 5\n20   FOR Y = 1 TO 5\n30     FOR Z = 1 TO 5\n40       PRINT \"X = \"; X;\" Y = \"; Y;\" Z = \"; Z\n50     NEXT Z\n60   NEXT Y\n70 NEXT X");
        writeFile("example6.bas", "10 LET X = 0\n20 LET X = X + 1\n30 READ X, Y\n40 PRINT \"X = \";X;\" Y = \";Y\n50 IF X > 6 THEN GOTO 70\n60 GOTO 20\n70 DATA 1, 2, 3, 4, 5, 6, 7, 8, 9, 10\n80 END\n");
        writeFile("example7.bas", "5 PRINT \"X VALUE\", \"RESOLUTION\"\n10 READ D\n15 IF D = -1 THEN GOTO 100\n20 FOR X = 0 TO 3\n30 PRINT X, D\n40 NEXT X\n50 GOTO 10\n100 DATA 1, 2, 3, -1\n110 END\n");
        writeFile("example8.bas", "10 LET S = 0\n20 FOR I = 1 TO 4\n30 READ X\n40 LET S = S + X\n50 NEXT I\n60 LET AVG = S / 4\n70 PRINT \"Purata: \"; AVG\n80 DATA 10, 15, 20, 25\n\n100 REM 10 = 10\n200 REM 10 + 15 = 25\n300 REM 25 + 20 = 45\n400 REM 45 + 25 = 70\n500 REM 70 / 4 = 17.5\n");
        writeFile("example9.bas", "10 LET PI = 3.14159\n20 LET R = 5\n30 LET AREA = PI * R * R\n40 PRINT \"Luas bulatan =\", AREA");
        writeFile("example10.bas", "10 PRINT \"MASUKKAN NOMBOR ANDA\"\n20 INPUT X\n30 IF X = 0 THEN GOTO 110\n40 FOR I = 1 TO X\n50 PRINT \"NILAI I ADALAH \",I\n60 IF I < 10 THEN PRINT \"NOMBOR KECIL DARI 10 : \",I\n70 IF I = 10 THEN PRINT \"NOMBOR ADALAH 10\"\n80 IF I > 10 THEN PRINT \"NOMBOR BESAR DARI 10 : \",I\n90 NEXT I\n100 PRINT \"SELESAI\"\n110 END");
        writeFile("example11.bas", "10 LET X = 11\n20 LET X = X - 1\n30 PRINT \"VALUE X:\"; X\n40 IF X = 1 THEN GOTO 100\n50 GOTO 20\n100 PRINT \"DONE!\"\n110 END\n");
        writeFile("example12.bas", "10 LET A = 5\n20 LET B = 3\n30 PRINT \"HASIL TAMBAH:\", A + B\n40 PRINT \"HASIL TOLAK:\", A - B\n50 PRINT \"HASIL DARAB:\", A * B\n60 PRINT \"HASIL BAHAGI:\", A / B\n70 END\n\n80 REM HASIL CETAKKAN\n90 REM HASIL TAMBAH: 8\n100 REM HASIL TOLAK: 2\n110 REM HASIL DARAB: 15\n120 REM HASIL BAHAGI: 1.66667\n");
        writeFile("example13.bas", "10 rem testing for/next\n20 print \"starting for/next\"\n30 for a = 1 to 10\n40 print a,a*a\n50 next a\n60 print \"backwards\"\n70 for x = 10 to 1 step -1\n80 print x,x*x\n90 next x\n100 print \"done\"\n110 end\n");
        writeFile("example14.bas", "10 READ A0, B0\n\n11 LET A = A0\n12 LET B = B0\n\n20 IF A = 0 THEN GOTO 80\n\n30 IF B = 0 THEN GOTO 50\n31 IF A > B THEN GOTO 34\n32 LET B = B - A\n33 GOTO 30\n34 LET A = A - B\n35 GOTO 30\n\n50 PRINT \"GCD(\", A0, \",\", B0, \")=\", A\n51 GOTO 10\n\n80 LET A = B\n81 GOTO 50\n\n999 END\n\n100 REM --------------- Inputs for A0 and B0 ----------------------------------\n\n101 DATA 23, 30\n102 DATA 121, 11\n103 DATA 44, 92\n104 DATA 382, 403\n105 DATA 943, 345\n106 DATA 56, 732\n");
        writeFile("example15.bas", "5 rem test of gosub\n10 print \"at 10\"\n20 gosub 100\n30 print \"at 30\"\n99 end\n100 print \"at 100\"\n110 gosub 200\n120 return\n200 print \"at 200\"\n210 gosub 300\n220 return\n300 print \"at 300\"\n310 gosub 400\n320 return\n400 print \"at 400\"\n420 return");
        writeFile("example16.bas", "10 rem test goto\n20 for a = 1 to 10\n30 goto 100\n35 print \"at 35\"\n40 print a,a*a\n50 next a\n55 print \"done\"\n60 end\n100 goto 200\n110 print \"at 110\"\n200 goto 300\n210 print \"at 210\"\n300 goto 400\n310 print \"at 310\"\n400 goto 40\n410 print \"at 410\"\n");
        writeFile("example17.bas", "10 rem guess a number\n20 let r=int(rnd(1)*10)+1\n30 let c=0\n40 print \"Enter your guess (1-10): \"\n45 input g\n50 if g=r then goto 300\n55 let c=c+1\n60 if c>3 then goto 400\n70 if g > r then goto 100\n80 if g < r then goto 200\n90 goto 40\n100 print \"too high\"\n110 goto 40\n200 print \"too low\"\n210 goto 40\n300 print \"Correct!\"\n310 end\n400 print \"The number was \";r\n410 end");
        writeFile("example18.bas", "10 rem prime.bas\n11 rem run the sieve of erastosthenes program\n12 rem this returns 1899 primes in about 1 second\n13 rem on a raspberry pi 4 running dbasic\n20 let s=8190\n30 dim f(8191)\n40 print \"1 iteration\"\n50 let c=0\n60 for i=0 to 8190\n70 let f(i) = 1\n80 next i\n90 for i=0 to 8190\n100 if f(i) = 0 then 180\n110 let p=i+i+3\n120 let k=i+p\n130 if k > s then 170\n140 let f(k)=0\n150 let k=k+p\n160 goto 130\n170 let c=c+1\n175 rem p is a prime. uncomment 176 to print it\n176 rem print p\n180 next i\n190 print c,\"primes\"\n200 end");
        writeFile("example19.bas", "10 rem This shows an example of the rnd() function\n20 rem When BASIC is invoked, the random number generator is seeded with a\n30 rem random number. This lasts until BASIC is exited, and should be\n40 rem OK for most applications.\n50 rem If the random number generator needs to be reseeded,\n60 rem then put rnd(-1) in a line. This will reseed the random number\n70 rem generator with a new random number.\n80 rem Using rnd(0) or rnd() just returns a random number.\n90 rem Using rnd(1) (or any positive number) will restart the\n100 rem random number generator with a predictable value. Use this\n110 rem when you need a fixed sequence of random numbers.\n200 print \"5 random numbers, seeded at BASIC start\"\n210 for n=1 to 5\n220 print rnd(n)\n230 next n\n240 print \"5 more random numbers, seeded at BASIC start\"\n250 for n=1 to 5\n260 print rnd(n)\n270 next n\n280 print \"reseeding the generator, and 5 random numbers\"\n290 let r=rnd(-1)\n300 for n=1 to 5\n310 print rnd(n)\n320 next n\n330 print \"Seeding the random number generator with a defined value\"\n340 let r=rnd(1)\n350 print \"5 semi-random numbers\"\n360 for n=1 to 5\n370 print rnd(n)\n380 next n\n390 print \"repeat the last sequence\"\n400 let r=rnd(1)\n410 for n=1 to 5\n420 print rnd(n)\n430 next n\n500 print \"done\"\n999 end\n");
        writeFile("example20.bas", "10 READ A1, A2, A3, A4\n15 LET D = A1 * A4 - A3 * A2\n20 IF D = 0 THEN GOTO 65\n30 READ B1, B2\n37 LET X1 = (B1 * A4 - B2 * A2)/D\n42 LET X2 = (A1 * B2 - A3 * B1)/D\n55 PRINT X1, X2\n60 GOTO 30\n65 PRINT \"NO UNIQUE SOLUTION\"\n70 DATA 1,2,4\n80 DATA 2,-7,5\n85 DATA 1,3,4,-7\n90 END");

    }

    public boolean createNewFile(String name) {
        try {
            return new File(directory, name).createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    public boolean exists(String name) {
        return new File(directory, name).exists();
    }

    public void writeFile(String name, String text) {
        if (new File(directory).exists()) {
            try {
                File file = new File(directory, name);
                FileWriter writer = new FileWriter(file);
                for (String str : text.split("\n")) {
                    writer.append(str).append("\n");
                }
                writer.close();
            } catch (IOException e) {
                System.out.println("Warning: File '" + name + " can't write");
            }
        }
    }

}
