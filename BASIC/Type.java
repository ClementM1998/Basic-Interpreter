
public enum Type {
    READLINE,
    NEWLINE,
    LETTER,
    KEYWORD, // PRINT LET IF THEN GOTO GOSUB
    IDENTIFIER, // X Y VAR1
    FUNCTION, // Fungsi math
    NUMBER, // 123 3.14
    STRING, // "HELLO"
    OPERATOR, // + - * / = < > <= >= <>
    SYMBOL, // , ; ( ) [ ]
    ERROR,
    END, // Baris tamat
}
