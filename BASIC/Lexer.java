import java.util.ArrayList;

public class Lexer {
    private String code;
    private int hasString = 0;

    public Lexer(String code) {
        this.code = code;
    }

    public void test() {
        for (Token token : tokenize()) {
            System.out.print("[" + token.type() + ":" + token.data() + "]");
            System.out.print(" ");
            if (token.type() == Type.NEWLINE) System.out.println();
            if (token.type() == Type.END) {
                System.out.println("END OF LINE");
                break;
            }
        }
    }

    public ArrayList<Token> tokenize() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        Type type = Type.READLINE;
        String data = "";

        for (char c : code.toCharArray()) {
            switch (type) {
                case READLINE:
                    if (isWhitespace(c)) {
                        if (c == '\n') {
                            tokens.add(new Token(Type.NEWLINE, "NEWLINE"));
                            data = "";
                            type = Type.READLINE;
                        } else {
                            continue;
                        }
                    } else if (isDigit(c)) {
                        data += c;
                        type = Type.NUMBER;
                    } else if (isLetter(c)) {
                        data += c;
                        type = Type.LETTER;
                    } else if (isOperator(c)) {
                        data += c;
                        type = Type.OPERATOR;
                    } else if (isSymbol(c)) {
                        data += c;
                        type = Type.SYMBOL;
                    } else if (c == '"') {
                        hasString++;
                        data += c;
                        type = Type.STRING;
                    }
                    break;
                case LETTER:
                    if (isWhitespace(c)) {
                        if (c == '\n') {
                            if (isKeyword(data.toLowerCase())) tokens.add(new Token(Type.KEYWORD, data.toUpperCase()));
                            else if (isMathDef(data.toLowerCase())) tokens.add(new Token(Type.FUNCTION, data.toUpperCase()));
                            else if (isOperatorLogic(data.toLowerCase())) tokens.add(new Token(Type.OPERATOR, data.toUpperCase()));
                            else tokens.add(new Token(Type.IDENTIFIER, data.toUpperCase()));
                            tokens.add(new Token(Type.NEWLINE, "NEWLINE"));
                            data = "";
                            type = Type.READLINE;
                        } else {
                            if (isKeyword(data.toLowerCase())) tokens.add(new Token(Type.KEYWORD, data.toUpperCase()));
                            else if (isMathDef(data.toLowerCase())) tokens.add(new Token(Type.FUNCTION, data.toUpperCase()));
                            else if (isOperatorLogic(data.toLowerCase())) tokens.add(new Token(Type.OPERATOR, data.toUpperCase()));
                            else tokens.add(new Token(Type.IDENTIFIER, data.toUpperCase()));
                            data = "";
                            type = Type.READLINE;
                        }
                    } else if (isLetterOrDigit(c)) {
                        data += c;
                        type = Type.LETTER;
                    } else if (isOperator(c)) {
                        if (isKeyword(data.toLowerCase())) tokens.add(new Token(Type.KEYWORD, data.toUpperCase()));
                        else if (isMathDef(data.toLowerCase())) tokens.add(new Token(Type.FUNCTION, data.toUpperCase()));
                        else if (isOperatorLogic(data.toLowerCase())) tokens.add(new Token(Type.OPERATOR, data.toUpperCase()));
                        else tokens.add(new Token(Type.IDENTIFIER, data.toUpperCase()));
                        data = "";
                        data += c;
                        type = Type.OPERATOR;
                    } else if (isSymbol(c)) {
                        if (isKeyword(data.toLowerCase())) tokens.add(new Token(Type.KEYWORD, data.toUpperCase()));
                        else if (isMathDef(data.toLowerCase())) tokens.add(new Token(Type.FUNCTION, data.toUpperCase()));
                        else if (isOperatorLogic(data.toLowerCase())) tokens.add(new Token(Type.OPERATOR, data.toUpperCase()));
                        else tokens.add(new Token(Type.IDENTIFIER, data.toUpperCase()));
                        data = "";
                        data += c;
                        type = Type.SYMBOL;
                    } else if (c == '"') {
                        if (isKeyword(data.toLowerCase())) tokens.add(new Token(Type.KEYWORD, data.toUpperCase()));
                        else if (isMathDef(data.toLowerCase())) tokens.add(new Token(Type.FUNCTION, data.toUpperCase()));
                        else if (isOperatorLogic(data.toLowerCase())) tokens.add(new Token(Type.OPERATOR, data.toUpperCase()));
                        else tokens.add(new Token(Type.IDENTIFIER, data.toUpperCase()));
                        data = "";
                        hasString++;
                        data += c;
                        type = Type.STRING;
                    } else if (isDigit(c)) {
                        if (isKeyword(data.toLowerCase())) tokens.add(new Token(Type.KEYWORD, data.toUpperCase()));
                        else if (isMathDef(data.toLowerCase())) tokens.add(new Token(Type.FUNCTION, data.toUpperCase()));
                        else if (isOperatorLogic(data.toLowerCase())) tokens.add(new Token(Type.OPERATOR, data.toUpperCase()));
                        else tokens.add(new Token(Type.IDENTIFIER, data.toUpperCase()));
                        data = "";
                        data += c;
                        type = Type.NUMBER;
                    }
                    break;
                case NUMBER:
                    if (isWhitespace(c)) {
                        if (c == '\n') {
                            tokens.add(new Token(Type.NUMBER, data));
                            tokens.add(new Token(Type.NEWLINE, "NEWLINE"));
                            data = "";
                            type = Type.READLINE;
                        } else {
                            tokens.add(new Token(Type.NUMBER, data));
                            data = "";
                            type = Type.READLINE;
                        }
                    } else if (isDigit(c) || c == '.') {
                        data += c;
                        type = Type.NUMBER;
                    } else if (isLetter(c)) {
                        tokens.add(new Token(Type.NUMBER, data));
                        data = "";
                        data += c;
                        type = Type.LETTER;
                    } else if (isOperator(c)) {
                        tokens.add(new Token(Type.NUMBER, data));
                        data = "";
                        data += c;
                        type = Type.OPERATOR;
                    } else if (isSymbol(c)) {
                        tokens.add(new Token(Type.NUMBER, data));
                        data = "";
                        data += c;
                        type = Type.SYMBOL;
                    } else if (c == '"') {
                        tokens.add(new Token(Type.NUMBER, data));
                        data = "";
                        hasString++;
                        data += c;
                        type = Type.STRING;
                    }
                    break;
                case STRING:
                    if (c == '"') {
                        hasString--;
                        data += c;
                        if (hasString == 0) tokens.add(new Token(Type.STRING, data));
                        else throw new RuntimeException("Unexpected string: " + hasString);
                        data = "";
                        type = Type.READLINE;
                    } else {
                        data += c;
                        type = Type.STRING;
                    }
                    break;
                case OPERATOR:
                    if (isWhitespace(c)) {
                        if (c == '\n') {
                            if (isOperator(data)) tokens.add(new Token(Type.OPERATOR, data));
                            else tokens.add(new Token(Type.ERROR, data));
                            tokens.add(new Token(Type.NEWLINE, "NEWLINE"));
                            data = "";
                            type = Type.READLINE;
                        } else {
                            if (isOperator(data)) tokens.add(new Token(Type.OPERATOR, data));
                            else tokens.add(new Token(Type.ERROR, data));
                            data = "";
                            type = Type.READLINE;
                        }
                    } else if (isDigit(c)) {
                        if (isOperator(data)) tokens.add(new Token(Type.OPERATOR, data));
                        else tokens.add(new Token(Type.ERROR, data));
                        data = "";
                        data += c;
                        type = Type.NUMBER;
                    } else if (isLetter(c)) {
                        if (isOperator(data)) tokens.add(new Token(Type.OPERATOR, data));
                        else tokens.add(new Token(Type.ERROR, data));
                        data = "";
                        data += c;
                        type = Type.LETTER;
                    } else if (isOperator(c)) {
                        data += c;
                        type = Type.OPERATOR;
                    } else if (isSymbol(c)) {
                        if (isOperator(data)) tokens.add(new Token(Type.OPERATOR, data));
                        else tokens.add(new Token(Type.ERROR, data));
                        data = "";
                        data += c;
                        type = Type.SYMBOL;
                    } else if (c == '"') {
                        if (isOperator(data)) tokens.add(new Token(Type.OPERATOR, data));
                        else tokens.add(new Token(Type.ERROR, data));
                        data = "";
                        hasString++;
                        data += c;
                        type = Type.STRING;
                    }
                    break;
                case SYMBOL:
                    if (isWhitespace(c)) {
                        if (c == '\n') {
                            tokens.add(new Token(Type.SYMBOL, data));
                            tokens.add(new Token(Type.NEWLINE, "NEWLINE"));
                            data = "";
                            type = Type.READLINE;
                        } else {
                            tokens.add(new Token(Type.SYMBOL, data));
                            data = "";
                            type = Type.READLINE;
                        }
                    } else if (isDigit(c)) {
                        tokens.add(new Token(Type.SYMBOL, data));
                        data = "";
                        data += c;
                        type = Type.NUMBER;
                    } else if (isLetter(c)) {
                        tokens.add(new Token(Type.SYMBOL, data));
                        data = "";
                        data += c;
                        type = Type.LETTER;
                    } else if (isOperator(c)) {
                        tokens.add(new Token(Type.SYMBOL, data));
                        data = "";
                        data += c;
                        type = Type.OPERATOR;
                    } else if (isSymbol(c)) {
                        tokens.add(new Token(Type.SYMBOL, data));
                        data = "";
                        data += c;
                        tokens.add(new Token(Type.SYMBOL, data));
                        data = "";
                        type = Type.READLINE;
                    } else if (c == '"') {
                        tokens.add(new Token(Type.SYMBOL, data));
                        data = "";
                        hasString++;
                        data += c;
                        type = Type.STRING;
                    }
                    break;
            }
        }

        tokens.add(new Token(Type.END, "END"));

        return tokens;
    }

    private boolean isWhitespace(char c) {
        return c == ' ' || c == '\n';
    }

    private boolean isLetterOrDigit(char c) {
        return isLetter(c) || isDigit(c);
    }

    private boolean isLetter(char c) {
        return isUpperCase(c) || isLowerCase(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '=' || c == '>' || c == '<';
    }

    private boolean isSymbol(char c) {
        return c == ',' || c == ';' || c == ':' || c == '?' || c == '(' || c == ')' || c == '[' || c == ']';
    }

    private boolean isKeyword(String line) {
        String[] keywords = {
                "let", "print", "input", "if", "then", "on", "goto", "gosub", "return", "end", "for", "to", "step", "next", "data", "read", "restore", "dim", "rem", "stop", "cont", "def"
        };
        for (String kw : keywords) if (line.equals(kw)) return true;
        return false;
    }

    private boolean isMathDef(String line) {
        String[] defs = {
                "abs",
                "atn",
                "cos",
                "exp",
                "int",
                "log",
                "rnd",
                "sin",
                "sqr",
                "tan"
        };
        for (String def : defs) if (line.equals(def)) return true;
        return false;
    }

    private boolean isCharDef(String line) {
        String[] defs = {
                "chr$", "asc", "left$", "right$", "mid$"
        };
        for (String def : defs) if (line.equals(def)) return true;
        return false;
    }

    private boolean isOperatorLogic(String line) {
        String[] logs = {
                "and", "or", "not"
        };
        for (String log : logs) if (line.equals(log)) return true;
        return false;
    }

    private boolean isOperator(String line) {
        String[] operators = {
                // Operator Aritmetik
                "+", "-", "*", "/", "^",
                // Operator Perbandingan
                "=", "<>", "<", ">", "<=", ">=",
        };
        for (String op : operators) if (line.equals(op)) return true;
        return false;
    }

}
