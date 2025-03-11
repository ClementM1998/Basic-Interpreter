import java.util.Scanner;

public class InputStatement extends Statement {
    private final String name;

    public InputStatement(String name) {
        this.name = name;
    }

    public void execute(Enviroment env) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("? "); // BASIC biasanya guna '?' untuk input
        String input = scanner.nextLine();
        try {
            // Cuba tukar ke nombor jika boleh
            if (input.contains(".")) env.assign(name, Double.parseDouble(input));
            else env.assign(name, Integer.parseInt(input));
        } catch (NumberFormatException e) {
            env.assign(name, input); // Jika gagal, simpan sebagai string
        }
    }
}
