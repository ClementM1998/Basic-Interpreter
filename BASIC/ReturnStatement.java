
public class RetStatement extends Statement {

    public void execute(Enviroment env) {
        env.returnFromSubroutine();
    }

}
