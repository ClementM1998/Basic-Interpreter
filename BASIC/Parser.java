import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;

public class Parser {
    private final TreeMap<Integer, Statement> programs = new TreeMap<Integer, Statement>();
    private final ArrayList<Token> tokens;
    private int currentTokenIndex = 0;
    private int lineInNumber;
    private Stack<ForStatement> forStatementStack = new Stack<>();
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Expression> datas = new ArrayList<>();

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;

        //for (Token token : tokens) System.out.println(token);
    }

    public TreeMap<Integer, Statement> parse() {
        try {
            while (!match(Type.END)) {
                if (skipNewline()) continue;
                int lineNumber = Integer.parseInt(consume(Type.NUMBER).data());
                setCurrentLine(lineNumber);
                if (skipNewline()) continue;
                Statement statement = parseStatement();
                if (statement != null) programs.put(lineNumber, statement);
                if (skipNewline()) continue;
            }
        } catch (RuntimeException e) {
            System.out.println("SYNTAX ERROR IN LINE " + getCurrentLine() + ": " + e.getMessage());
            programs.clear();
            return programs;
        }
        return programs;
    }

    public void addNames(String name) {
        names.add(name);
    }

    public boolean hasNames(String name) {
        return names.contains(name);
    }

    public void addData(Expression data) {
        datas.add(data);
    }

    public boolean isDataEmpty() {
        return datas.isEmpty();
    }

    private Statement parseStatement() {
        Token token = consume(Type.KEYWORD);
        switch (token.data().toUpperCase()) {
            case "REM": return parseRemStatement();
            case "LET": return parseLetStatement();
            case "PRINT": return parsePrintStatement();
            case "GOTO": return parseGotoStatement();
            case "IF": return parseIfStatement();
            case "GOSUB": return parseGosubStatement();
            case "RETURN": return parseReturnStatement();
            case "END": return parseEndStatement();
            case "INPUT": return parseInputStatement();
            case "READ": return parseReadStatement();
            case "DATA": return parseDataStatement();
            case "FOR": return parseForStatement();
            case "NEXT": return parseNextStatement();
            case "DIM": return parseDimStatement();
            default: throw new RuntimeException("Unknown statement: " + token.data());
        }
    }

    private Statement parseRemStatement() {
        while (!match(Type.NEWLINE)) {
            Token token = consume(Type.NUMBER, Type.KEYWORD, Type.IDENTIFIER, Type.FUNCTION, Type.STRING, Type.OPERATOR, Type.SYMBOL, Type.ERROR);
        }
        return new RemStatement("");
    }

    private Statement parseLetStatement() {
        int lineNumber = getCurrentLine();
        if (!match(Type.IDENTIFIER)) throw new RuntimeException("Missing variable name in LET statement");
        String varname = consume(Type.IDENTIFIER).data();
        addNames(varname);
        if (match(Type.SYMBOL) && peek().data().equals("(")) {
            consume(Type.SYMBOL);
            ArrayList<Expression> dimensions = new ArrayList<Expression>();
            while (true) {
                if (match(Type.NUMBER) || match(Type.IDENTIFIER)) dimensions.add(parseIntegerExpression());
                else {
                    throw new RuntimeException("Expected array index, but found: " + peek());
                }
                if (match(Type.SYMBOL) && peek().data().equals(",")) consume(Type.SYMBOL); // Ambil simbol ','
                else break;
            }
            if (match(Type.SYMBOL) && peek().data().equals(")")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected ')' after dimensions, but found: " + peek());
            }
            if (match(Type.OPERATOR) && peek().data().equals("=")) consume(Type.OPERATOR); // Ambil simbol '='
            else {
                throw new RuntimeException("Expected '=' after array assignment, but found: " + peek());
            }
            NumberExpression value = parseLetNumberExpression();
            //NumberExpression value = (NumberExpression) parseDoubleExpression();
            return new LetDimStatement(varname, dimensions, value);
        } else {
            if (match(Type.OPERATOR) && peek().data().equals("=")) consume(Type.OPERATOR); // '='
            else {
                throw new RuntimeException("Expected '=' in LET statement");
            }
            NumberExpression expression = parseLetNumberExpression();
            //NumberExpression expression = (NumberExpression) parseDoubleExpression();
            if (expression instanceof DoubleExpression) return new LetStatement(varname, (DoubleExpression) expression);
            else return new LetStatement(varname, expression);
        }
    }

    private Statement parsePrintStatement() {
        int lineNumber = getCurrentLine();
        ArrayList<Expression> expressions = new ArrayList<Expression>();
        while (!match(Type.NEWLINE)) { // Loop sehingga jumpa baris baru
            if (match(Type.STRING) ||
                    match(Type.NUMBER) ||
                    match(Type.IDENTIFIER) ||
                    match(Type.FUNCTION) ||
                    match(Type.OPERATOR) ||
                    (match(Type.SYMBOL) && !(peek().data().equals(";") || peek().data().equals(",")))) {
//            if (match(Type.STRING) || match(Type.NUMBER) || match(Type.IDENTIFIER)) {
                Expression expression = parsePrintExpression();
                expressions.add(expression);
            } else if (match(Type.SYMBOL) && peek().data().equals(";")) {
                consume(Type.SYMBOL); // Pastikan token dipadam
                expressions.add(new SeparatorExpression(";"));
            } else if (match(Type.SYMBOL) && peek().data().equals(",")) {
                consume(Type.SYMBOL); // Pastikan token dipadam
                expressions.add(new SeparatorExpression(","));
            } else {
                throw new RuntimeException("Unexpected token: PRINT STATEMENT " + peek());
            }
        }
        return new PrintStatement(expressions);
    }

    public Expression parsePrintExpression() {
        return parsePrintTerm();
    }

    private Expression parsePrintTerm() {
        Expression left = parsePrintFactor();
        while (match(Type.OPERATOR) && (peek().data().equals("+") || peek().data().equals("-"))) {
            String operator = consume(Type.OPERATOR).data();
            Expression right = parsePrintFactor();
            if (operator.equals("+")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.ADD, right);
            else if (operator.equals("-")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.SUBTRACT, right);
        }
        return left;
    }

    private Expression parsePrintFactor() {
        Expression left = parsePrintPrimary();
        while (match(Type.OPERATOR) && (peek().data().equals("*") || peek().data().equals("/"))) {
            String operator = consume(Type.OPERATOR).data();
            Expression right = parsePrintPrimary();
            if (operator.equals("*")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.MULTIPLY, right);
            else if (operator.equals("/")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.DIVIDE, right);
        }
        return left;
    }

    private Expression parsePrintPrimary() {
        int lineNumber = getCurrentLine();
        if (match(Type.SYMBOL) && peek().data().equals("(")) {
            consume(Type.SYMBOL); // (
            Expression expression = parsePrintExpression();
            if (!match(Type.SYMBOL) || !peek().data().equals(")")) {
                throw new RuntimeException("Expected ')' but found: " + peek().data());
            }
            consume(Type.SYMBOL); // )
            return expression;
        }
        if (match(Type.FUNCTION)) {
            String functionName = consume(Type.FUNCTION).data();
            if (match(Type.SYMBOL) && peek().data().equals("(")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected '(' after function name: " + functionName);
            }
            NumberExpression expression = parseLetNumberExpression();
            if (match(Type.SYMBOL) && peek().data().equals(")")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected ')' after function argument: " + functionName);
            }
            return new MathFunctionExpression(functionName, expression);
        }
        // Sokongan untuk nombor negatif
        boolean isNegative = false;
        if (match(Type.OPERATOR) && peek().data().equals("-")) {
            consume(Type.OPERATOR);
            isNegative = true;
        }
        Token token = consume(Type.NUMBER, Type.STRING, Type.IDENTIFIER);
        switch (token.type()) {
            case NUMBER:
                NumberExpression number;
                if (token.data().contains(".")) number = new DoubleExpression(Double.parseDouble(token.data()));
                else number = new IntegerExpression(Integer.parseInt(token.data()));
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, number) : number;
            case STRING:
                String string = token.data();
                if (string.startsWith("\"")) string = string.substring(1);
                if (string.endsWith("\"")) string = string.substring(0, string.length()-1);
                else throw new RuntimeException("Missing '\"' for string");
                return new StringExpression(string);
            case IDENTIFIER:
                // Sokong DIM: Periksa jika selepas identifier ada "(" -> array DIM
                if (!hasNames(token.data())) throw new RuntimeException("Unexception token: PRINT PRIMARY " + token.data());
                if (match(Type.SYMBOL) && peek().data().equals("(")) {
                    consume(Type.SYMBOL);
                    ArrayList<Expression> dimensions = new ArrayList<Expression>();
                    dimensions.add(parseIntegerExpression());
                    while (match(Type.SYMBOL) && peek().data().equals(",")) {
                        consume(Type.SYMBOL);
                        dimensions.add(parseIntegerExpression());
                    }
                    if (!match(Type.SYMBOL) && !peek().data().equals(")")) {
                        throw new RuntimeException("Expected ')' but found: " + peek().data());
                    }
                    consume(Type.SYMBOL); // Ambil ')'
                    return new VariableDimExpression(token.data(), dimensions);
                }
                Expression variable = new VariableExpression(token.data());
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, variable) : variable;
                //return new VariableExpression(token.data());
            default:
                throw new RuntimeException("Invalid primary expression: " + token.data());
        }
    }

    private Statement parseGotoStatement() {
        int lineNumber = Integer.parseInt(consume(Type.NUMBER).data()); // Ambil nombor baris
        if (lineNumber == -1) return null;
        return new GotoStatement(lineNumber); // Kembalikan objek GotoStatement
    }

    private Statement parseIfStatement() {
        int lineNumber = getCurrentLine();
        if (!match(Type.NUMBER) && !match(Type.IDENTIFIER)) {
            throw new RuntimeException("Missing condition in IF");
        }
        Expression condition = parseIfConditionExpression(); // Parse untuk condition
        if (!(match(Type.KEYWORD) && peek().data().equals("THEN"))) {
            throw new RuntimeException("Expected IF without THEN");
        }
        consume(Type.KEYWORD); // Ambil 'THEN'
        if (match(Type.NEWLINE) || match(Type.END)) {
            throw new RuntimeException("Missing statement after THEN");
        }
        Statement thenStatement = parseStatement(); // Arahan selepas THEN
        return new IfStatement(condition, thenStatement);
    }

    private Expression parseIfConditionExpression() {
        int lineNumber = getCurrentLine();
        Expression left = parseLetNumberExpression();
        Token operator = consume(Type.OPERATOR);
        Expression right = parseLetNumberExpression();
        if (operator.data().equals("=")) return new ComparisonExpression(left, ComparisonExpression.Operator.EQUAL, right);
        else if (operator.data().equals("<")) return new ComparisonExpression(left, ComparisonExpression.Operator.LESS, right);
        else if (operator.data().equals(">")) return new ComparisonExpression(left, ComparisonExpression.Operator.GREATER, right);
        else if (operator.data().equals("<>")) return new ComparisonExpression(left, ComparisonExpression.Operator.NOT_EQUAL, right);
        else if (operator.data().equals("<=")) return new ComparisonExpression(left, ComparisonExpression.Operator.LESS_EQUAL, right);
        else if (operator.data().equals(">=")) return new ComparisonExpression(left, ComparisonExpression.Operator.GREATER_EQUAL, right);
        else {
            throw new RuntimeException("Expected condition operator: " + operator);
        }
    }

    private Statement parseGosubStatement() {
        int lineNumber = Integer.parseInt(consume(Type.NUMBER).data());
        return new GosubStatement(lineNumber);
    }

    private Statement parseReturnStatement() {
        return new ReturnStatement();
    }

    private Statement parseEndStatement() {
        return new EndStatement();
    }

    private Statement parseInputStatement() {
        if (!match(Type.IDENTIFIER)) {
            throw new RuntimeException("Missing variable name");
        }
        ArrayList<String> variables = new ArrayList<String>();
        do {
            if (!match(Type.IDENTIFIER)) throw new RuntimeException("Missing variable name after ','");
            String varName = consume(Type.IDENTIFIER).data();
            addNames(varName);
            variables.add(varName);
        } while (match(Type.SYMBOL) && consume(Type.SYMBOL).data().equals(","));
        return new InputStatement(variables);
    }

    private Statement parseReadStatement() {
        ArrayList<String> variables = new ArrayList<String>();
        boolean firstValue = true; // Pastikan variable pertama bukan koma
        do {
            if (match(Type.IDENTIFIER)) {
                String varname = consume(Type.IDENTIFIER).data();
                addNames(varname);
                variables.add(varname);
                firstValue = false;
            } else {
                if (firstValue) {
                    if (match(Type.SYMBOL) && peek().data().equals(",")) throw new RuntimeException("DATA cannot start with a comma");
                }
            }
        } while (match(Type.SYMBOL) && consume(Type.SYMBOL).data().equals(",")); // Baca lebih dari satu variable jika ada koma
        return new ReadStatement(variables);
    }

    private Statement parseDataStatement() {
        ArrayList<NumberExpression> values = new ArrayList<NumberExpression>();
        boolean firstValue = true; // Pastikan nilai pertama bukan koma
        do {
            if (match(Type.NUMBER) || (match(Type.OPERATOR) && peek().data().equals("-"))) {
                NumberExpression value = parseNumberOnlyExpression();
                values.add(value);
                addData(value);
                firstValue = false;
            } else {
                if (firstValue) {
                    if (match(Type.SYMBOL) && peek().data().equals(",")) throw new RuntimeException("DATA cannot start with a comma");
                    else if (match(Type.IDENTIFIER)) throw new RuntimeException("DATA value must be constant number");
                } else if (match(Type.IDENTIFIER)) throw new RuntimeException("DATA value must be constant number");
            }
        } while (match(Type.SYMBOL) && consume(Type.SYMBOL).data().equals(",")); // Baca lebih dari satu nilai jika ada koma
        return new DataStatement(values);
    }

    private Statement parseForStatement() {
        int lineNumber = getCurrentLine();
        if (!match(Type.IDENTIFIER)) {
            throw new RuntimeException("Expected variable name after FOR");
        }
        String varName = consume(Type.IDENTIFIER).data(); // Ambil nama pembolehubah
        addNames(varName);
        if (match(Type.OPERATOR) && peek().data().equals("=")) consume(Type.OPERATOR); // Ambil simbol '='
        else {
            throw new RuntimeException("Expected '=' after variable");
        }
        if (!match(Type.NUMBER)) throw new RuntimeException("Expected start value before TO must be constant number");
        Expression startExpression = parseIntegerExpression();
        if ((match(Type.KEYWORD) && peek().data().equals("TO"))) consume(Type.KEYWORD); // Ambil TO
        else {
            throw new RuntimeException("Expected 'TO' before end value");
        }
        if (!match(Type.NUMBER)) throw new RuntimeException("Expected end value after TO must be constant number");
        Expression endExpression = parseIntegerExpression();
        // Periksa STEP jika ada
        Expression stepExpression = new IntegerExpression(1); // Default STEP = 1
        if (match(Type.KEYWORD) && peek().data().equals("STEP")) {
            consume(Type.KEYWORD); // Ambil STEP
            //if (!match(Type.NUMBER)) throw new RuntimeException("Expected step value after STEP must be constant number");
            stepExpression = parseIntegerExpression();
            int value = ((IntegerExpression) stepExpression.evaluate(null)).value;
            if (value == 0) throw new RuntimeException("Expected step value cannot be zero");
        }
        skipNewline();
        if (match(Type.END)) {
            throw new RuntimeException("Missing 'NEXT' statement for FOR-LOOP");
        }
        TreeMap<Integer, Statement> bodyForStatement = new TreeMap<Integer, Statement>();
        boolean ForStopping = true;
        while (ForStopping) {
            if (skipNewline()) continue;
            int lineNumberFor = Integer.parseInt(consume(Type.NUMBER).data());
            setCurrentLine(lineNumberFor);
            if (skipNewline()) continue; // Jika baris nombor tiada arahan
            Token token = peek();
            if (match(Type.KEYWORD) && token.data().equals("NEXT")) {
                consume(Type.KEYWORD);
                if (match(Type.IDENTIFIER) && peek().data().equals(varName)) ForStopping = false;
                ForStopping = false;
            } else {
                if (skipNewline()) continue;
                Statement statement = parseStatement();
                if (statement != null) bodyForStatement.put(lineNumberFor, statement);
                if (skipNewline()) continue;
            }
        }
        if (match(Type.IDENTIFIER) && peek().data().equals(varName)) consume(Type.IDENTIFIER);
        //skipNewline();
        ForStatement forStmt = new ForStatement(varName, startExpression, endExpression, stepExpression, bodyForStatement);
        forStatementStack.push(forStmt); // Simpan dalam stack untuk rujukan NEXT
        return forStmt;
    }

    public Statement parseNextStatement() {
        if (forStatementStack.isEmpty()) throw new RuntimeException("NEXT without FOR");
        String varName = "";
        if (match(Type.IDENTIFIER)) varName = consume(Type.IDENTIFIER).data();
        ForStatement forStmt = forStatementStack.peek();
        if (varName != null && !forStmt.varName.equals(varName)) throw new RuntimeException("NEXT variable does not match FOR");
        forStatementStack.pop(); // Tutup loop
        return new NextStatement(varName);
    }

    public Statement parseDimStatement() {
        int lineNumber = getCurrentLine();
        if (!match(Type.IDENTIFIER)) throw new RuntimeException("Expected array name after 'DIM'");
        String varName = consume(Type.IDENTIFIER).data();
        addNames(varName);
        ArrayList<Expression> dimensions = new ArrayList<Expression>();
        if (match(Type.SYMBOL) && peek().data().equals("(")) consume(Type.SYMBOL); // Ambil simbol '('
        else {
            throw new RuntimeException("Expected '(' after array name");
        }
        if (match(Type.NUMBER)) {
            dimensions.add(new IntegerExpression(Integer.parseInt(consume(Type.NUMBER).data())));
        } else {
            throw new RuntimeException("Expected array size must be a constant number");
        }
        if (match(Type.SYMBOL) && peek().data().equals(",")) {
            consume(Type.SYMBOL); // Ambil simbol ','
            if (match(Type.NUMBER)) {
                dimensions.add(new IntegerExpression(Integer.parseInt(consume(Type.NUMBER).data())));
            } else {
                throw new RuntimeException("Expected array size must be a constant number");
            }
        }
        if (match(Type.SYMBOL) && peek().data().equals(",")) {
            consume(Type.SYMBOL);
            if (match(Type.NUMBER)) {
                dimensions.add(new IntegerExpression(Integer.parseInt(consume(Type.NUMBER).data())));
            } else {
                throw new RuntimeException("Expected array size must be a constant number");
            }
        }
        if (match(Type.SYMBOL) && peek().data().equals(",")) {
            throw new RuntimeException("Unexpected DIM dimension only 3 value, but found : " + peek());
        }
        if (match(Type.SYMBOL) && peek().data().equals(")")) consume(Type.SYMBOL); // Ambil simbol ')'
        else {
            throw new RuntimeException("Expected ')' after array size");
        }
        return new DimStatement(varName, dimensions);
    }

    public Expression parseExpression() {
        return parseTerm();
    }

    private Expression parseTerm() {
        Expression left = parseFactor();
        while (match(Type.OPERATOR) && (peek().data().equals("+") || peek().data().equals("-"))) {
            String operator = consume(Type.OPERATOR).data();
            Expression right = parseFactor();
            if (operator.equals("+")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.ADD, right);
            else if (operator.equals("-")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.SUBTRACT, right);
        }
        return left;
    }

    private Expression parseFactor() {
        Expression left = parsePrimary();
        while (match(Type.OPERATOR) && (peek().data().equals("*") || peek().data().equals("/"))) {
            String operator = consume(Type.OPERATOR).data();
            Expression right = parsePrimary();
            if (operator.equals("*")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.MULTIPLY, right);
            else if (operator.equals("/")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.DIVIDE, right);
        }
        return left;
    }

    private Expression parsePrimary() {
        int lineNumber  = getCurrentLine();
        if (match(Type.SYMBOL) && peek().data().equals("(")) {
            consume(Type.SYMBOL); // (
            Expression expression = parseExpression();
            if (!match(Type.SYMBOL) || !peek().data().equals(")")) {
                throw new RuntimeException("Expected ')' but found: " + peek().data());
            }
            consume(Type.SYMBOL); // )
            return expression;
        }
        if (match(Type.FUNCTION)) {
            String functionName = consume(Type.FUNCTION).data();
            if (match(Type.SYMBOL) && peek().data().equals("(")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected '(' after function name: " + functionName);
            }
            Expression expression = parseExpression();
            if (match(Type.SYMBOL) && peek().data().equals(")")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected ')' after function argument: " + functionName);
            }
            return new MathFunctionExpression(functionName, expression);
        }
        // Sokongan untuk nombor negatif
        boolean isNegative = false;
        if (match(Type.OPERATOR) && peek().data().equals("-")) {
            consume(Type.OPERATOR);
            isNegative = true;
        }
        Token token = consume(Type.NUMBER, Type.STRING, Type.IDENTIFIER);
        switch (token.type()) {
            case NUMBER:
                NumberExpression number;
                if (token.data().contains(".")) number = new DoubleExpression(Double.parseDouble(token.data()));
                else number = new IntegerExpression(Integer.parseInt(token.data()));
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, number) : number;
            case STRING:
                String string = token.data();
                if (string.startsWith("\"")) string = string.substring(1);
                if (string.endsWith("\"")) string = string.substring(0, string.length()-1);
                return new StringExpression(string);
            case IDENTIFIER:
                Expression variable = new VariableExpression(token.data());
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, variable) : variable;
                //return new VariableExpression(token.data());
            default:
                throw new RuntimeException("Invalid primary expression: " + token.data());
        }
    }

    private NumberExpression parseLetNumberExpression() {
        return parseLetAdditionSubtraction();
    }

    private NumberExpression parseLetAdditionSubtraction() {
        NumberExpression left = parseLetMultiplyDivide();
        while (match(Type.OPERATOR) && (peek().data().equals("+") || peek().data().equals("-"))) {
            Token operator = consume(Type.OPERATOR);
            NumberExpression right = parseLetMultiplyDivide();
            if (operator.data().equals("+")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.ADD, right);
            else if (operator.data().equals("-")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.SUBTRACT, right);
        }
        return left;
    }

    private NumberExpression parseLetMultiplyDivide() {
        NumberExpression left = parseLetNumberPrimary();
        while (match(Type.OPERATOR) && (peek().data().equals("*") || peek().data().equals("/"))) {
            Token operator = consume(Type.OPERATOR);
            NumberExpression right = parseLetMultiplyDivide();
            if (operator.data().equals("*")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.MULTIPLY, right);
            else if (operator.data().equals("/")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.DIVIDE, right);
        }
        return left;
    }

    private NumberExpression parseLetNumberPrimary() {
        int lineNumber = getCurrentLine();
        if (match(Type.SYMBOL) && peek().data().equals("(")) {
            consume(Type.SYMBOL); // (
            NumberExpression expression = parseLetNumberExpression();
            if (!match(Type.SYMBOL) || !peek().data().equals(")")) {
                throw new RuntimeException("Expected ')' but found: " + peek().data());
            }
            consume(Type.SYMBOL); // )
            return expression;
        }
        if (match(Type.FUNCTION)) {
            String functionName = consume(Type.FUNCTION).data();
            if (match(Type.SYMBOL) && peek().data().equals("(")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected '(' after function name: " + functionName);
            }
            NumberExpression expression = parseLetNumberExpression();
            if (match(Type.SYMBOL) && peek().data().equals(")")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected ')' after function argument: " + functionName);
            }
            return new MathFunctionExpression(functionName, expression);
        }
        // Sokongan untuk nombor negatif
        boolean isNegative = false;
        if (match(Type.OPERATOR) && peek().data().equals("-")) {
            consume(Type.OPERATOR);
            isNegative = true;
        }
        Token token = consume(Type.NUMBER, Type.IDENTIFIER);
        switch (token.type()) {
            case NUMBER:
                NumberExpression number;
                if (token.data().contains(".")) number = new DoubleExpression(Double.parseDouble(token.data()));
                else number = new IntegerExpression(Integer.parseInt(token.data()));
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, number) : number;
            case IDENTIFIER:
                VariableNumberExpression variable = new VariableNumberExpression(token.data());
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, variable) : variable;
                //return new VariableNumberExpression(token.data());
            default:
                throw new RuntimeException("Invalid number expression: " + token.data());
        }
    }

    private Expression parseIntegerExpression() {
        return parseIntegerAdditionSubtraction();
    }

    private Expression parseIntegerAdditionSubtraction() {
        Expression left = parseIntegerMultiplyDivide();
        while (match(Type.OPERATOR) && (peek().data().equals("+") || peek().data().equals("-"))) {
            Token operator = consume(Type.OPERATOR);
            Expression right = parseIntegerMultiplyDivide();
            if (operator.data().equals("+")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.ADD, right);
            else if (operator.data().equals("-")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.SUBTRACT, right);
        }
        return left;
    }

    private Expression parseIntegerMultiplyDivide() {
        Expression left = parseIntegerPrimary();
        while (match(Type.OPERATOR) && (peek().data().equals("*") || peek().data().equals("/"))) {
            Token operator = consume(Type.OPERATOR);
            Expression right = parseIntegerMultiplyDivide();
            if (operator.data().equals("*")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.MULTIPLY, right);
            else if (operator.data().equals("/")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.DIVIDE, right);
        }
        return left;
    }

    private Expression parseIntegerPrimary() {
        int lineNumber = getCurrentLine();
        if (match(Type.SYMBOL) && peek().data().equals("(")) {
            consume(Type.SYMBOL); // (
            Expression expression = parseIntegerExpression();
            if (!match(Type.SYMBOL) || !peek().data().equals(")")) {
                throw new RuntimeException("Expected ')' but found: " + peek().data());
            }
            consume(Type.SYMBOL); // )
            return expression;
        }
        if (match(Type.FUNCTION)) {
            String functionName = consume(Type.FUNCTION).data();
            if (match(Type.SYMBOL) && peek().data().equals("(")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected '(' after function name: " + functionName);
            }
            Expression expression = parseIntegerExpression();
            if (match(Type.SYMBOL) && peek().data().equals(")")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected ')' after function argument: " + functionName);
            }
            return new MathFunctionExpression(functionName, expression);
        }
        // Sokongan untuk nombor negatif
        boolean isNegative = false;
        if (match(Type.OPERATOR) && peek().data().equals("-")) {
            consume(Type.OPERATOR);
            isNegative = true;
        }
        Token token = consume(Type.NUMBER, Type.IDENTIFIER);
        switch (token.type()) {
            case NUMBER:
                NumberExpression number = new IntegerExpression(Integer.parseInt(token.data()));
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, number) : number;
            case IDENTIFIER:
                Expression variable = new VariableExpression(token.data());
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, variable) : variable;
                //return new VariableNumberExpression(token.data());
            default:
                throw new RuntimeException("Invalid number expression: " + token.data());
        }
    }

    private Expression parseDoubleExpression() {
        return parseDoubleAdditionSubtraction();
    }

    private Expression parseDoubleAdditionSubtraction() {
        Expression left = parseDoubleMultiplyDivide();
        while (match(Type.OPERATOR) && (peek().data().equals("+") || peek().data().equals("-"))) {
            Token operator = consume(Type.OPERATOR);
            Expression right = parseDoubleMultiplyDivide();
            if (operator.data().equals("+")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.ADD, right);
            else if (operator.data().equals("-")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.SUBTRACT, right);
        }
        return left;
    }

    private Expression parseDoubleMultiplyDivide() {
        Expression left = parseDoublePrimary();
        while (match(Type.OPERATOR) && (peek().data().equals("*") || peek().data().equals("/"))) {
            Token operator = consume(Type.OPERATOR);
            Expression right = parseDoubleMultiplyDivide();
            if (operator.data().equals("*")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.MULTIPLY, right);
            else if (operator.data().equals("/")) left = new ArithmeticExpression(left, ArithmeticExpression.Operator.DIVIDE, right);
        }
        return left;
    }

    private Expression parseDoublePrimary() {
        int lineNumber = getCurrentLine();
        if (match(Type.SYMBOL) && peek().data().equals("(")) {
            consume(Type.SYMBOL); // (
            Expression expression = parseDoubleExpression();
            if (!match(Type.SYMBOL) || !peek().data().equals(")")) {
                throw new RuntimeException("Expected ')' but found: " + peek().data());
            }
            consume(Type.SYMBOL); // )
            return expression;
        }
        if (match(Type.FUNCTION)) {
            String functionName = consume(Type.FUNCTION).data();
            if (match(Type.SYMBOL) && peek().data().equals("(")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected '(' after function name: " + functionName);
            }
            Expression expression = parseDoubleExpression();
            if (match(Type.SYMBOL) && peek().data().equals(")")) consume(Type.SYMBOL);
            else {
                throw new RuntimeException("Expected ')' after function argument: " + functionName);
            }
            return new MathFunctionExpression(functionName, expression);
        }
        // Sokongan untuk nombor negatif
        boolean isNegative = false;
        if (match(Type.OPERATOR) && peek().data().equals("-")) {
            consume(Type.OPERATOR);
            isNegative = true;
        }
        Token token = consume(Type.NUMBER, Type.IDENTIFIER);
        switch (token.type()) {
            case NUMBER:
                NumberExpression number;
                number = new DoubleExpression(Double.parseDouble(token.data()));
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, number) : number;
            case IDENTIFIER:
                Expression variable = new VariableNumberExpression(token.data());
                return isNegative ? new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, variable) : variable;
                //return new VariableNumberExpression(token.data());
            default:
                throw new RuntimeException("Invalid number expression: " + token.data());
        }
    }

    public NumberExpression parseNumberOnlyExpression() {
        // Sokongan untuk nombor negatif
        Environment env = new Environment();
        boolean isNegative = false;
        if (match(Type.OPERATOR) && peek().data().equals("-")) {
            consume(Type.OPERATOR);
            isNegative = true;
        }
        Token token = consume(Type.NUMBER);
        if (token.data().contains(".")) {
            if (isNegative) {
                DoubleExpression number = new DoubleExpression(Double.parseDouble(token.data()));
                return ((DoubleExpression) new ArithmeticExpression(new DoubleExpression(0), ArithmeticExpression.Operator.SUBTRACT, number).evaluate(env));
            } else return new DoubleExpression(Double.parseDouble(token.data()));
        } else {
            if (isNegative) {
                IntegerExpression number = new IntegerExpression(Integer.parseInt(token.data()));
                return ((DoubleExpression) new ArithmeticExpression(new IntegerExpression(0), ArithmeticExpression.Operator.SUBTRACT, number).evaluate(env));
            } else return new IntegerExpression(Integer.parseInt(token.data()));
        }
    }

    private Token consume(Type ... types) {
        if (currentTokenIndex >= tokens.size()) throw new RuntimeException("Unexpected end of tokens");
        Token token = tokens.get(currentTokenIndex++);
        //System.out.println("Consuming token: " + token); // untuk debugging
        for (Type type : types) {
            if (token.type() == type) return token;
        }
        throw new RuntimeException("Unexpected token: CONSUME " + token);
    }

    private Token peek() {
        if (currentTokenIndex >= tokens.size()) return new Token(Type.END, "");
        return tokens.get(currentTokenIndex);
    }

    private boolean match(Type type) {
        return currentTokenIndex < tokens.size() && tokens.get(currentTokenIndex).type() == type;
    }

    private boolean matchAndConsume(Type type) {
        if (match(type)) {
            currentTokenIndex++;;
            return true;
        }
        return false;
    }

    private boolean skipNewline() {
        return matchAndConsume(Type.NEWLINE);
    }

    public int getCurrentLine() {
        return lineInNumber;
    }

    public void setCurrentLine(int line) {
        this.lineInNumber = line;
    }

}
