import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Memory {

    private String dirCurrent = System.getProperty("user.dir");
    private String dirBasic = "Basic";
    private TreeMap<Integer, String> memory;

    public Memory() {
        System.out.println("Path : " + getDirectoryBasic());
        if (!new File(getDirectoryBasic()).exists()) {
            new File(getDirectoryBasic()).mkdir();
        }
        memory = new TreeMap<Integer, String>();
    }

    public String getDirectoryBasic() {
        return new File(dirCurrent, dirBasic).getPath();
    }

    public void add(int num, String line) {
        if (memory == null) {
            System.err.println("Ralat: memory tidak boleh menyimpan");
            return;
        }
        memory.put(num, line);
    }

    public String get(int num) {
        if (memory == null) {
            System.err.println("Ralat: memory tidak boleh diambil");
            return null;
        }
        return memory.get(num);
    }

    public boolean check(int num) {
        if (memory == null) {
            System.err.println("Ralat: memory tidak boleh cek");
            return false;
        }
        return memory.containsKey(num);
    }

    public void clear() {
        if (memory == null) {
            System.err.println("Ralat: memory tidak boleh di bersihkan");
            return;
        }
        memory.clear();
    }

    public boolean empty() {
        if (memory == null) {
            System.err.println("Ralat: memory adalah null");
            return false;
        }
        return memory.isEmpty();
    }

    public TreeMap<Integer, String> getAll() {
        if (memory == null) {
            System.err.println("Ralat: memory tidak boleh di akses");
            return null;
        }
        return memory;
    }

    public void save(String name, TreeMap<Integer, String> data) {
        if (new File(getDirectoryBasic()).exists()) {
            File file = new File(getDirectoryBasic(), name);
            try {
                FileWriter fw = new FileWriter(file);
                for (Map.Entry<Integer, String> m : data.entrySet()) {
                    fw.append(m.getKey() + " " + m.getValue());
                }
                fw.close();
                System.out.println("Berjaya! Fail [" + name + "] di simpan");
            } catch (IOException e) {
                System.err.println("Ralat: Fail [" + name + "] tidak dapat di simpan");
            }
        } else {
            System.err.println("Ralat: Direktori Basic tidak wujud");
        }
    }

    public String load(String name) {
        String data = "";
        if (new File(getDirectoryBasic()).exists()) {

        } else {
            System.err.println("Ralat: Direktori Basic tidak wujud");
        }
        return data;
    }

}
