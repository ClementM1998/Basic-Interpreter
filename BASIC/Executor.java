
public class Executor {
    private final Environment env;

    public Executor(Environment env) {
        this.env = env;
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
