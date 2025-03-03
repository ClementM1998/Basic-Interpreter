
import java.util.Scanner;

/*
 * Perihal Basic dari Claymen
 *
 *   Basic merupakan sebuah program yang ditulis oleh saya sendiri yang terinspirasi
 * dari BASIC Darthmouth pada 1964. Saya membuat program Basic untuk tujuan pembelajaran
 * yang ditulis menggunakan bahasa pemprograman Java. Saya menggunakan Java untuk Interpreter Basic
 * ini kerana saya senang untuk buat kerja dengan bahasa Java.
 *
 * Tujuan saya
 *
 * 
 * [ Kata kunci ]
 *
 * 1. Kawalan Program
 *
 *   RUN - Menjalankan program
 *   END - Menamatkan program
 *   NEW - Mengosongkan memori program yang dimuat
 *   RESTART - (New) Memula semula dari awal program
 *   EXIT - (New) Keluar dari program
 *   CLEAR - (New) Bersihkan skrin
 *  STOP - Menghentikan program sementara
 *  GOTO - Melompat ke baris tertentu dalam program
 *  GOSUB - Melompat ke subrutin dan kembali selepas RETURN
 *  RETURN - Kembali ke baris selepas GOSUB
 *  IF ... THEN - Kawalan aliran berdasarkan syarat
 *
 * 2. Input dan Output
 *
 *  PRINT - Mencetak teks atau nilai ke skrin
 *  INPUT - Meminta pengguna memasukkan nilai
 *  READ - Membaca nilai dari senarai DATA
 *  DATA - Menyediakan data untuk digunakan dengan READ
 *  RESTORE - Menetapkan semula pointer READ ke awal DATA
 *
 * 3. Pengiraan dan Perubahan Nilai
 *
 * LET - Menetapkan nilai kepada pemboleh ubah (boleh ditulis tanpa LET)
 * DIM - Mengisytiharkan array (contoh: DIM A(10))
 * REM - Menulis komen dalam kod program
 *
 * 4. Perulangan
 *
 *  FOR ... TO ... STEP - Mulai gelung (loop) dengan julat tertentu
 *  NEXT - Menandakan penghujung gelung FOR
 *  WHILE ... WEND - Gelung yang berjalan selagi syarat dipenuhi
 *
 * 5. Fungsi Matematik
 *
 * ABS(X) - Nilai mutlak
 * INT(X) - Nilai bulat terdekat ke bawah
 * SIN(X), COS(X), TAN(X) - Fungsi trigonometri
 * SQR(X) - Punca kuasa dua
 * RND - Nombor rawak antara 0 dan 1
 *
 * 6. Fungsi String
 *
 * STR$(X) - Menukar nombor ke string
 * LEN(X$) - Mengira panjang teks dalam string
 * MID$(X$, start, length) - Mengambil substring dari teks
 * LEFT$(X$, n) - Mengambil n aksara dari kiri string
 * RIGHT$(X$, n) - Mengambil n aksara dari kanan string
 * VAL(X$) - Menukar string ke nombor
 *
 */

public class Basic {
    
    public Basic() {}
    
    public void launch() {
        Program program = new Program();
        while (program.system != Program.PROGRAM_STOP) {
            if (program.system == Program.PROGRAM_RESTART) {
                program.restart();
            }
            System.out.println("Selamat datang ke Basic");
            while (true) {
                System.out.print("Basic > ");
                String in = new Scanner(System.in).nextLine().trim();
                if (in.equals("exit")) {
                    program.system = Program.PROGRAM_STOP;
                    break;
                } else if (in.equals("restart")) {
                    program.system = Program.PROGRAM_RESTART;
                    break;
                } else if (in.equals("clear")) {
                    program.clear();
                }
            }
        }
        program.clear();
        program.exit();
    }
    
    public static void main(String[] args) {
        
        Basic basic = new Basic();
        basic.launch();
        
    }
}
