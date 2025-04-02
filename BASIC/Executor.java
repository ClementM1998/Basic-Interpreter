import java.util.Map;
import java.util.TreeMap;

public class Executor {
    private final Environment env;
    private final TreeMap<Integer, Statement> parse;

    public Executor(TreeMap<Integer, Statement> parse) {
        this.env = new Environment();
        this.parse = parse;

        for (Map.Entry<Integer, Statement> me : parse.entrySet()) {

            if (me.getValue() instanceof DataStatement) {
                me.getValue().execute(env);
            }

            env.addLine(me.getKey(), me.getValue());
        }
    }

    public void run() {
        Integer currentLine = env.getNextStatement(); // Ambil baris pertama

        while (currentLine != null) {
            Statement stmt = env.getStatement(currentLine);

            if (stmt == null) throw new RuntimeException("Nothing statement in line " + currentLine);

            env.setCurrentLine(currentLine); // Tetapkan baris semasa
            stmt.execute(env); // Jalankan statement

            if (!env.isRunning()) break; // Berhenti jika program telah dihentikan

            currentLine = env.getNextStatement(); // Ambil baris seterusnya

        }
    }
}
