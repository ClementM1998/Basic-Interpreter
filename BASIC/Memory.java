
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Memory {

    private String dirCurrent = System.getProperty("user.dir");
    private String dirBasic = "Basic";
    private TreeMap<Integer, String> memory;

    public Memory() {
        //System.out.println("Path : " + getDirectoryBasic());
        if (!new File(getDirectoryBasic()).exists()) {
            new File(getDirectoryBasic()).mkdir();
            new BasicTest(getDirectoryBasic());
        } else {
            new BasicTest(getDirectoryBasic());
        }
        memory = new TreeMap<Integer, String>();
    }

    public String getDirectoryBasic() {
        return new File(dirCurrent, dirBasic).getPath();
    }

    public void add(int num, String line) {
        if (memory == null) {
            System.err.println("Ralat: memory adalah null");
            return;
        }
        memory.put(num, line);
    }

    public String get(int num) {
        if (memory == null) {
            System.err.println("Ralat: memory adalah null");
            return null;
        }
        return memory.get(num);
    }

    public boolean check(int num) {
        if (memory == null) {
            System.err.println("Ralat: memory adalah null");
            return false;
        }
        return memory.containsKey(num);
    }

    public void clear() {
        if (memory == null) {
            System.err.println("Ralat: memory adalah null");
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
            System.err.println("Ralat: memory adalah null");
            return null;
        }
        return memory;
    }

    public SortedMap<Integer, String> subMap(int from, int to) {
        if (memory == null) {
            System.err.println("Ralat: memory adalah null");
            return null;
        }
        return memory.subMap(from, to);
    }

    public void save(String name, TreeMap<Integer, String> data) throws IOException {
        /*
        if (new File(getDirectoryBasic()).exists()) {
            File file = new File(getDirectoryBasic(), name);
            try {
                FileWriter fw = new FileWriter(file);
                for (Map.Entry<Integer, String> m : data.entrySet()) {
                    fw.append(m.getKey() + " " + m.getValue()).append("\n");
                }
                fw.close();
                System.out.println("Berjaya! Fail [" + name + "] di simpan");
            } catch (IOException e) {
                System.err.println("Ralat: Fail [" + name + "] tidak dapat di simpan");
            }
        } else {
            System.err.println("Ralat: Direktori Basic tidak wujud");
        }
         */
        if (new File(getDirectoryBasic()).exists()) {
            File file = new File(getDirectoryBasic(), name);
            FileWriter fw = new FileWriter(file);
            for (Map.Entry<Integer, String> m : data.entrySet()) {
                fw.append(m.getKey() + " " + m.getValue()).append("\n");
            }
            fw.close();
        } else {
            System.out.println("Ralat: Direktori Basic tidak wujud");
        }
    }

    public TreeMap<Integer, String> load(String name) throws IOException {
        TreeMap<Integer, String> data = new TreeMap<Integer, String>();
        /*
        if (new File(getDirectoryBasic()).exists()) {
            File file = new File(getDirectoryBasic(), name);
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                StringBuffer buffer = new StringBuffer();
                while ((line = br.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                String[] lines = buffer.toString().trim().split("\n");
                for (String str : lines) {
                    str = str.trim();
                    if (str.contains(" ")) {
                        String num = str.substring(0, str.indexOf(" ")).trim();
                        String stat = str.substring(str.indexOf(" "), str.length()).trim();
                        data.put(Integer.valueOf(num), stat);
                    } else {
                        String num = str.trim();
                        if (num.equals("")) continue;
                        data.put(Integer.valueOf(num), "");
                    }
                }
                fr.close();
                br.close();
                System.out.println("Berjaya! Fail [" + name + "] telah di muat");
            } catch (IOException e) {
                System.out.println("Ralat: Fail [" + name + "] tidak dapat di muat");
            }
        } else {
            System.err.println("Ralat: Direktori Basic tidak wujud");
        }
         */
        if (new File(getDirectoryBasic()).exists()) {
            File file = new File(getDirectoryBasic(), name);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            String[] lines = buffer.toString().trim().split("\n");
            for (String str : lines) {
                str = str.trim();
                if (str.contains(" ")) {
                    String num = str.substring(0, str.indexOf(" ")).trim();
                    String stat = str.substring(str.indexOf(" "), str.length()).trim();
                    data.put(Integer.valueOf(num), stat);
                } else {
                    String num = str.trim();
                    if (num.equals("")) continue;
                    data.put(Integer.valueOf(num), "");
                }
            }
            fr.close();
            br.close();
        } else {
            System.out.println("Ralat: Direktori Basic tidak wujud");
        }
        return data;
    }

}
