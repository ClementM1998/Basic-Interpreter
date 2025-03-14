
public class ReturnStatement extends Statement {

    public void execute(Enviroment env) {
        env.returnFromSubroutine();
    }

}
