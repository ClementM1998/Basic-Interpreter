
public class Interpreter {
    private final Parser parser;

    public Interpreter(String source) {
        Lexer lexer = new Lexer(source);
        parser = new Parser(lexer.tokenize());
    }

    public void run() {
        Executor executor = new Executor(parser.parse());
        executor.run();
    }

}
