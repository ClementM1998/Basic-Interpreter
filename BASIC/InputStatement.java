import java.util.ArrayList;
import java.util.Scanner;

public class InputStatement extends Statement {
    final ArrayList<String> varNames;

    public InputStatement(ArrayList<String> varNames) {
        this.varNames = varNames;
    }

    public void execute(Environment env) {
        Scanner scanner = new Scanner(System.in);
        for (String name : varNames) {
            while (true) { // Loop sehingga input sah
                System.out.print("? "); // BASIC biasanya guna '?' untuk input
                String input = scanner.nextLine();
                try {
                    // Cuba tukar ke nombor jika boleh
                    if (input.contains(".")) {
                        if (env.hasVariable(name)) env.assignVariable(name, new DoubleExpression(Double.parseDouble(input)));
                        else env.defineVariable(name, new DoubleExpression(Double.parseDouble(input)));
                    } else {
                        if (env.hasVariable(name)) env.assignVariable(name, new IntegerExpression(Integer.parseInt(input)));
                        else env.defineVariable(name, new IntegerExpression(Integer.parseInt(input)));
                    }
                    break; // Keluar dari loop jika berjaya
                } catch (NumberFormatException e) {
                    //if (env.hasVariable(name)) env.assignVariable(name, new StringExpression(input)); // Jika gagal, simpan sebagai string
                    //else env.defineVariable(name, new StringExpression(input));
                    System.out.println("WHAT?"); // Sama BASIC Darthmouth
                }
            }
        }
    }
}
