
import java.util.Map;
import java.util.TreeMap;

public class Program {
    public static int PROGRAM_START = 2;
    public static int PROGRAM_RESTART = 1;
    public static int PROGRAM_STOP = 0;

    public static int system = PROGRAM_START;

    public static int PROGRESS_BAR_1 = 1; // versi 1 untuk animasi biasa
    public static int PROGRESS_BAR_2 = 2; // versi 2 untuk progress bar biasa
    public static int PROGRESS_BAR_3 = 3; // versi 3 untuk progress bar yang lebih interaktif

    private Memory memory = new Memory();

    private String getOSname() {
        return System.getProperty("os.name");
    }

    public void restart() {
        clear();
    }

    public void clear() {
        try {
            if (getOSname().toLowerCase().contains("win")) {
                // Untuk Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Untuk Linux dan Mac
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Gagal membersihkan skrin!");
        }
    }

    public void sleep(long times) {
        try {
            Thread.sleep(times);
        } catch (Exception e) {}
    }

    public void loading(int style, int total, long times) {
        if (style == PROGRESS_BAR_1) {
            String[] loadingFrames = {"|", "/", "-", "\\"};
            System.out.print("Sedang dimuatkan ");
            for (int i = 0;i < total;i++) {
                System.out.print("\rSedang dimuatkan " + loadingFrames[i % 4]);
                sleep(times);
            }
            System.out.println("\rLoading Selesai!");
        } else if (style == PROGRESS_BAR_2) {
            System.out.print("Sedang dimuatkan: [");
            for (int i = 0;i <= total;i++) {
                System.out.print("=");
                sleep(times);
            }
            System.out.println("] 100% Selesai!");
        } else if (style == PROGRESS_BAR_3) {
            System.out.print("Sedang dimuatkan: [");
            for (int i = 0;i <= total;i++) {
                System.out.print("=");
                int percentage = (i * 100) / total;
                System.out.print("] " + percentage + "%");
                sleep(times);
                System.out.print("\rSedang dimuatkan: [");
            }
            System.out.println("] 100% Selesai!");
        }
    }

    public void exit() {
        System.out.println("Program di tamat.");
        try {
            sleep(1000);
            loading(1, 10, 1000);
            sleep(1000);
            System.out.println("Program tamat");
            sleep(500);
            System.exit(0);
        } catch (Exception e) {
            sleep(1000);
            System.out.println("Program dipaksa tutup");
            loading(1, 10, 1000);
            sleep(1000);
            System.out.println("Program tamat");
            sleep(500);
            System.exit(1);
        }
    }

    public void list(String stat) {
        TreeMap<Integer, String> mem = memory.getAll();
        for (Map.Entry me : mem.entrySet()) {
            System.out.println(me.getKey() + " " + me.getValue());
        }
    }

    public void run(String stat) {
        System.out.println("RUN : " + stat);
    }

    public void save(String stat) {
        if (!stat.endsWith(".bas")) stat = stat + ".bas";
        if (stat.equals("")) {
            System.out.println("Ralat: Nama fail kosong");
            System.out.println("Sila masukkan nama fail yang di simpan");
            return;
        }
        if (memory.empty()) {
            System.out.println("Ralat: Fail [" + stat + "] tidak dapat di simpan");
            System.out.println("Memory kosong");
            return;
        }
        memory.save(stat, memory.getAll());
    }

    public void load(String stat) {
    }

    public void commandLine(String in) {
        if (checkIfFirstLineNumber(in)) {
            String number = "";
            String statement = "";
            if (in.contains(" ")) {
                String first = in.substring(0, in.indexOf(" "));
                char[] chr = first.toCharArray();
                for (char c : chr) if (Character.isDigit(c)) number += c;
                statement = in.substring(first.length(), in.length()).trim();
            } else {
                char[] chr = in.toCharArray();
                for (char c : chr) number += c;
            }
            memory.add(Integer.valueOf(number), statement);
        } else {
            //System.out.println("not to memory: " + in);
            if (checkIfFirstKeyword(in, "list")) {
                String stat = "";
                if (in.contains(" ")) stat = in.substring(in.indexOf(" "));
                list(stat.trim());
            } else if (checkIfFirstKeyword(in, "run")) {
                String stat = "";
                if (in.contains(" ")) stat = in.substring(in.indexOf(" "));
                run(stat.trim());
            } else if (checkIfFirstKeyword(in, "save")) {
                String name = "";
                if (in.contains(" ")) name = in.substring(in.indexOf(" "));
                save(name.trim());
            } else if (checkIfFirstKeyword(in, "load")) {
                String name = "";
                if (in.contains(" ")) name = in.substring(in.indexOf(" "));
                load(name.trim());
            } else if (checkIfFirstKeyword(in, "clear")) {
                clear();
            } else {
                System.out.println("Ralat Sintaks");
            }
        }
    }

    private boolean checkIfFirstLineNumber(String line) {
        if (line.contains(" ")) {
            String first = line.substring(0, line.indexOf(" "));
            char[] chr = first.toCharArray();
            for (char c : chr) {
                if (!Character.isDigit(c)) return false;
            }
            return true;
        } else {
            char[] chr = line.toCharArray();
            for (char c : chr) {
                if (!Character.isDigit(c)) return false;
            }
            return true;
        }
    }

    private boolean checkIfFirstKeyword(String line, String key) {
        String[] keywords = new String[] {
                "list", "run", "save", "load", "clear"
        };
        if (line.contains(" ")) {
            String first = line.substring(0, line.indexOf(" "));
            for (String kw : keywords) if (first.equals(kw) && first.equals(key)) return true;
        } else {
            for (String kw : keywords) if (line.equals(kw) && line.equals(key)) return true;
        }
        return false;
    }

}
