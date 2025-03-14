import java.util.ArrayList;

public class ReadStatement extends Statement {
    private final ArrayList<String> variablesName;

    public ReadStatement(ArrayList<String> variablesName) {
        this.variablesName = variablesName;
    }

    public void execute(Environment env) {
        for (String name : variablesName) {
            Expression value = env.readNextData(); // Ambil nilai Data
            env.define(name, value); // Simpan dalam variable
        }
    }
    
}
