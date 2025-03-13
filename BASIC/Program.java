import java.util.ArrayList;

public class Program extends ASTNode {
    final ArrayList<Statement> statements;

    public Program(ArrayList<Statement> statements) {
        this.statements = statements;
    }
    
}
