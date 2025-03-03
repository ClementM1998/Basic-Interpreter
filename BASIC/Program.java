
public class Program {
    public static int PROGRAM_START = 2;
    public static int PROGRAM_RESTART = 1;
    public static int PROGRAM_STOP = 0;
    
    public int system = PROGRAM_START;
    
    public static int PROGRESS_BAR_1 = 1; // versi 1 untuk animasi biasa
    public static int PROGRESS_BAR_2 = 2; // versi 2 untuk progress bar biasa
    public static int PROGRESS_BAR_3 = 3; // versi 3 untuk progress bar yang lebih interaktif
    
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
            System.out.print("Loading ");
            for (int i = 0;i < total;i++) {
                System.out.print("\rLoading " + loadingFrames[i % 4]);
                sleep(times);
            }
            System.out.println("\rLoading Selesai!");
        } else if (style == PROGRESS_BAR_2) {
            System.out.print("Loading: [");
            for (int i = 0;i <= total;i++) {
                System.out.print("=");
                sleep(times);
            }
            System.out.println("] 100% Selesai!");
        } else if (style == PROGRESS_BAR_3) {
            System.out.print("Loading: [");
            for (int i = 0;i <= total;i++) {
                System.out.print("=");
                int percentage = (i * 100) / total;
                System.out.print("] " + percentage + "%");
                sleep(times);
                System.out.print("\rLoading: [");
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
}
