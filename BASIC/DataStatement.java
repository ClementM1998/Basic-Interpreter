import java.util.ArrayList;

public class DataStatement extends Statement {
    private final ArrayList<Expression> data;

    public DataStatement(ArrayList<Expression> data) {
        this.data = data;
    }

    public void execute(Environment env) {
    }
    
}
