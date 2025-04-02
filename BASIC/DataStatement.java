import java.util.ArrayList;

public class DataStatement extends Statement {
    final ArrayList<NumberExpression> data;

    public DataStatement(ArrayList<NumberExpression> data) {
        this.data = data;
    }

    public void execute(Environment env) {
        env.storeData(data);
    }

}
