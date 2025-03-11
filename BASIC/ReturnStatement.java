
public class RetStatement extends Statement {

    public void execute(Enviroment env) {
        int returnLine = env.popReturnAddress(); // Ambil baris yang disimpan dalam stack
        env.setLineNumber(returnLine);
    }

}
