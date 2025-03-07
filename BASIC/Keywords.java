
public enum Keywords {
    // Kata Kunci Sistem dalam BASIC Darthmouth
    // dan Kata Kunci Basic

    // Kawalan Aliran (Control Flow)
    LET, // Mengisytiharkan dan menetapkan nilai kepada pembolehubah
    IF, // Untuk menguji syarat jika (TRUE) atau (FALSE). Operator hanya menyokong =, <>, <, >, <=, >=
    THEN, // Menentukan apa perlu di lakukan jika (TRUE) atau (FALSE)
    GOTO, // Lompat ke nombor baris tertentu
    GOSUB, // Panggil subrutin
    RETURN, // Kembali daripada subrutin
    END, // Hentikan program

    // Input dan Output
    PRINT, // Cetak teks atau nilai pembolehubah
    INPUT, // Ambil input daripada senarai data
    READ, // Ambil nilai daripada senarai data
    DATA, // Simpan senarai data dalam program
    RESTORE, // Tetapkan semula penunjuk DATA

    // Pengiraan Matematik
    ABS, // Nilai mutlak
    SQR, // Punca kuasa dua
    SIN, // Sine
    COS, // Cosine
    TAN, // Tangent
    ATN, // Arc tangent
    EXP, // Eksponen
    LOG, // Logaritma semula jadi
    RND, // Nombor rawak
    INT, // Pembulatan ke bawah
    SGN, // Tanda nombor(-1, 0, 1)

    // Pengurusan Data dan Pembolehubah
    DIM, // Buat array
    DEF, // Definisi fungsi tersuai

    // Gelung (Looping)
    FOR, // Memulakan gelung dengan nilai awal
    TO, // Menentukan nilai akhir dalam gelung
    STEP, // Menentukkan langkah kenaikkan atau pengurangan (nilai lalai: 1)
    NEXT, // Menamatkan satu lelaran dan kembali ke FOR

    // Komen
    REM, // Ulasan/Komen dalam kod

    // Kawalan Program (Sistem)
    RUN, // Jalankan kod dalam memori
    LIST, // Senaraikan kod dalam memori
    NEW, // Kosongkan memori dan mula program baru
    STOP, // Hentikan program tetapi boleh disambung semula
    CONT, // Sambung semula program selepas STOP
    CLEAR, // (Basic) Kosongkan skrin
    SAVE, // (Basic) simpan fail dalam direktori
    LOAD, // (Basic) memuat naik fail dari direktori ke memori
    FILES, // (Basic) Menunjukkan fail dalam penyimpanan direktori

}
