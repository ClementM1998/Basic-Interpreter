
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

public class BasicProgram {
    public static int PROGRAM_START = 2;
    public static int PROGRAM_RESTART = 1;
    public static int PROGRAM_STOP = 0;

    public static int system = PROGRAM_START;

    public static int PROGRESS_BAR_1 = 1; // versi 1 untuk animasi biasa
    public static int PROGRESS_BAR_2 = 2; // versi 2 untuk progress bar biasa
    public static int PROGRESS_BAR_3 = 3; // versi 3 untuk progress bar yang lebih interaktif

    private Memory memory = new Memory();
    private String filesMemory = "";

    private String getOSname() {
        return System.getProperty("os.name");
    }

    public BasicProgram() {
        //System.out.println("BASIC dimuat ...");
        //loading(1, 10, 1000);
        //clearProgram();
    }

    public void restartProgram() {
        newProgram();
        clearProgram();
    }

    public void clearProgram() {
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
            System.out.println("\rMemuat Selesai!");
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

    public void exitProgram() {
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

    public void listProgram(String stat) {
        /*
         * 1. LIST (tanpa parameter) (/)
         * 2. LIST n (baris tertentu)
         * 3. LIST n-m (julat baris)
         * 4. LIST n- (dari baris ke akhir)
         * 5. LIST -n (dari awal ke baris)
         * 6. LIST [statement] (arahan tertentu)
         * 7. LIST n [statement] (arahan dalam baris tertentu)
         * 8. LIST n-m [statement] (arahan dalam julat baris)
         */
        stat = stat.trim();
        if (memory.empty()) {
            System.out.println("Program kosong");
        } else if (!stat.equals("")) {
            if (checkIfFirstNumber(stat)) {
                for (Map.Entry<Integer, String> me : memory.getAll().entrySet()) {
                    if (Integer.valueOf(stat).equals((int) me.getKey())) System.out.println(me.getKey() + " " + me.getValue());
                }
            } /* else if (stat.startsWith("-")) {
                stat = stat.substring(1, stat.length()).trim();
                if (memory.getAll().containsKey(Integer.valueOf(stat))) {
                    Map.Entry<Integer, String> first = memory.getAll().firstEntry();
                    SortedMap<Integer, String> sorted = memory.subMap(first.getKey(), Integer.valueOf(stat) + 1);
                    for (Map.Entry<Integer, String> sort : sorted.entrySet()) {
                        System.out.println(sort.getKey() + " " + sort.getValue());
                    }
                }
            } else if (stat.endsWith("-")) {
                stat = stat.substring(0, stat.length() - 1).trim();
                if (memory.getAll().containsKey(Integer.valueOf(stat))) {
                    Map.Entry<Integer, String> last = memory.getAll().lastEntry();
                    SortedMap<Integer, String> sorted = memory.subMap(Integer.valueOf(stat), last.getKey() + 1);
                    for (Map.Entry<Integer, String> sort : sorted.entrySet()) {
                        System.out.println(sort.getKey() + " " + sort.getValue());
                    }
                }
            }*/ else if (stat.contains("-")) {
                stat = stat.trim();
                String first = stat.substring(0, stat.indexOf("-")).trim();
                String second = stat.substring(stat.indexOf("-")+1).trim();
                int numFirst = -1;
                int numSecond = -1;
                if (checkIfFirstNumber(first)) numFirst = Integer.valueOf(first.trim());
                if (checkIfFirstNumber(second)) numSecond = Integer.valueOf(second.trim());
                for (Map.Entry<Integer, String> sort : memory.subMap(numFirst, numSecond + 1).entrySet()) {
                    System.out.println(sort.getKey() + " " + sort.getValue());
                }
            }
        } else {
            for (Map.Entry<Integer, String> me : memory.getAll().entrySet()) {
                System.out.println(me.getKey() + " " + me.getValue());
            }
        }
    }

    public void runProgram(String stat) {
        stat = stat.trim();
        if (checkIfFirstNumber(stat)) {
            if (memory.empty()) {
                System.out.println("?UNDEFINED LINE NUMBER ERROR");
            } else if (!memory.getAll().containsKey(Integer.valueOf(stat))) {
                System.out.println("?UNDEFINED STATEMENT ERROR");
            } else {
                Map.Entry<Integer, String> last = memory.getAll().lastEntry();
                SortedMap<Integer, String> sorted = memory.subMap(Integer.valueOf(stat), last.getKey() + 1);
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<Integer, String> map : sorted.entrySet()) builder.append(map.getKey()).append(" ").append(map.getValue()).append("\n");
                Interpreter interpreter = new Interpreter(builder.toString());
                interpreter.run();
            }
        } else {
            if (memory.empty()) {
                System.out.println("?UNDEFINED LINE NUMBER ERROR");
            } else {
                Map.Entry<Integer, String> first = memory.getAll().firstEntry();
                Map.Entry<Integer, String> last = memory.getAll().lastEntry();
                SortedMap<Integer, String> sorted = memory.subMap(first.getKey(), last.getKey() + 1);
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<Integer, String> map : sorted.entrySet()) builder.append(map.getKey()).append(" ").append(map.getValue()).append("\n");
                Interpreter interpreter = new Interpreter(builder.toString());
                interpreter.run();
            }
        }
    }

    public void saveProgram(String stat) {
        stat = stat.trim();
        if (stat.equals("")) {
            System.out.println("Ralat: Nama fail kosong");
            System.out.println("Sila masukkan nama fail yang di simpan");
            return;
        }
        if (!stat.endsWith(".bas")) stat = stat + ".bas";
        if (memory.empty()) {
            System.out.println("Ralat: Fail [" + stat + "] tidak dapat di simpan");
            System.out.println("Memory kosong");
            return;
        }
        try {
            filesMemory = stat;
            memory.save(stat, memory.getAll());
            System.out.println("Berjaya! Fail [" + stat + "] di simpan");
        } catch (IOException e) {
            System.out.println("Ralat: Fail [" + stat + "] tidak dapat di simpan");
        }
    }

    public void loadProgram(String stat) {
        stat = stat.trim();
        if (stat.equals("")) {
            System.out.println("Ralat: Nama fail kosong");
            System.out.println("Sila masukkan nama fail yang ingin di muat");
            return;
        }
        if (!stat.endsWith(".bas")) stat = stat + ".bas";
        File file = new File(memory.getDirectoryBasic(), stat);
        if (file.exists()) {
            try {
                memory.clear();
                filesMemory = stat;
                for (Map.Entry<Integer, String> m : memory.load(stat).entrySet()) {
                    memory.add((int) m.getKey(), (String) m.getValue());
                }
                System.out.println("Berjaya! Fail [" + stat + "] telah di muat");
            } catch (IOException e) {
                System.out.println("Ralat: Fail [" + stat + "] gagal di muat semula");
            }
        } else {
            System.out.println("Ralat: Fail [" + stat + "] tidak di temukan");
        }
    }

    private void reloadProgram() {
        String name = "";
        if (!filesMemory.equals("")) name = filesMemory;
        else {
            System.out.println("Ralat: Nama fail kosong");
            System.out.println("Tiada fail di muat atau di simpan");
            return;
        }
        if (!name.endsWith(".bas")) name = name + ".bas";
        File file = new File(memory.getDirectoryBasic(), name);
        if (file.exists()) {
            try {
                memory.clear();
                for (Map.Entry<Integer, String> m : memory.load(name).entrySet()) {
                    memory.add((int) m.getKey(), (String) m.getValue());
                }
                System.out.println("Berjaya! Fail [" + name + "] telah di muat semula");
            } catch (IOException e) {
                System.out.println("Ralat: Fail [" + name + "] gagal di muat semula");
            }
        } else {
            System.out.println("Ralat: Fail [" + name + "] tidak di temukan");
        }
    }

    public void newProgram() {
        if (!memory.empty()) {
            memory.clear();
            filesMemory = "";
            System.out.println("Program telah di kosongkan");
        } else {
            System.out.println("Program memang kosong");
        }
    }

    public void filesProgram() {
        File file = new File(memory.getDirectoryBasic());
        System.out.println("[main-dir] /" + file.getName());
        for (File files : file.listFiles()) {
            if (files.isDirectory()) {
                System.out.println("     [dir] ---- " + files.getName());
            } else if (files.isFile()) {
                System.out.println("    [file] ---- " + files.getName());
            }
        }
    }

    public void scratchProgram(String stat) {
        stat = stat.trim();
        if (stat.equals("")) {
            System.out.println("Ralat: Nama fail kosong");
            System.out.println("Sila masukkan nama fail yang di ingin di padam");
            return;
        }
        if (!stat.endsWith(".bas")) stat = stat + ".bas";
        File file = new File(memory.getDirectoryBasic(), stat);
        if (file.exists()) {
            file.delete();
            System.out.println("Berjaya! Fail [" + stat + "] telah di padam");
        } else {
            System.out.println("Ralat: Fail [" + stat + "] tidak di temukan");
        }
    }

    public void commandLine(String in) {
        if (checkIfFirstNumber(in)) {
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
                listProgram(stat);
            } else if (checkIfFirstKeyword(in, "run")) {
                String stat = "";
                if (in.contains(" ")) stat = in.substring(in.indexOf(" "));
                runProgram(stat);
            } else if (checkIfFirstKeyword(in, "save")) {
                String name = "";
                if (in.contains(" ")) name = in.substring(in.indexOf(" "));
                saveProgram(name);
            } else if (checkIfFirstKeyword(in, "load")) {
                String name = "";
                if (in.contains(" ")) name = in.substring(in.indexOf(" "));
                loadProgram(name);
            } else if (checkIfFirstKeyword(in, "reload")) {
                reloadProgram();
            } else if (checkIfFirstKeyword(in, "clear")) {
                clearProgram();
            } else if (checkIfFirstKeyword(in, "new")) {
                newProgram();
            } else if (checkIfFirstKeyword(in, "files")) {
                filesProgram();
            } else if (checkIfFirstKeyword(in, "scratch")) {
                String stat = "";
                if (in.contains(" ")) stat = in.substring(in.indexOf(" "));
                scratchProgram(stat);
            } else {
                System.out.println("Ralat Sintaks");
            }
        }
    }

    private boolean checkIfFirstNumber(String line) {
        if (line.equals("")) return false;
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
        if (line.equals("")) return false;
        if (key.equals("")) return false;
        String[] keywords = new String[] {
                "list", "run", "save", "load", "reload", "clear", "new", "files", "scratch", "goto", "gosub", "print", "input"
        };

        if (line.contains(" ")) {
            String first = line.substring(0, line.indexOf(" ")).toUpperCase();
            for (String kw : keywords) if (first.equals(kw.toUpperCase()) && first.equals(key.toUpperCase())) return true;
        } else {
            line = line.toUpperCase();
            for (String kw : keywords) if (line.equals(kw.toUpperCase()) && line.equals(key.toUpperCase())) return true;
        }
        return false;
    }

}
