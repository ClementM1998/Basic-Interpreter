import java.util.TreeMap;

public class Memory {

    private TreeMap<Integer, String> memory;

    public Memory() {
        memory = new TreeMap<Integer, String>();
    }

    public void add(int num, String line) {
        if (memory == null) {
            System.err.println("error: memory tidak boleh menyimpan");
            return;
        }
        memory.put(num, line);
    }

    public String get(int num) {
        if (memory == null) {
            System.err.println("error: memory tidak boleh diambil");
            return null;
        }
        return memory.get(num);
    }

    public boolean check(int num) {
        if (memory == null) {
            System.err.println("error: memory tidak boleh cek");
            return false;
        }
        return memory.containsKey(num);
    }

    public void clear() {
        if (memory == null) {
            System.err.println("error: memory tidak boleh di bersihkan");
            return;
        }
        memory.clear();
    }

    public boolean empty() {
        if (memory == null) {
            System.err.println("error: memory adalah null");
            return false;
        }
        return memory.isEmpty();
    }

    public TreeMap<Integer, String> getAll() {
        if (memory == null) {
            System.err.println("error: memory tidak boleh di akses");
            return null;
        }
        return memory;
    }

}
