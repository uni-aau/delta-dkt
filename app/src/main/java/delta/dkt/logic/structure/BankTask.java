package delta.dkt.logic.structure;

public class BankTask extends Task{

    public String bankTask;

    public BankTask(int id, String name, String bankTask) {
        super(id, name);
        this.bankTask = bankTask;
    }
}
