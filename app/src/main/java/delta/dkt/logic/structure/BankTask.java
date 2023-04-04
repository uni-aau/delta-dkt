package delta.dkt.logic.structure;

public class BankTask extends Task{

    public String bankTaskString;

    public BankTask(int id, String name, String bankTask) {
        super(id, name);
        this.bankTaskString = bankTask;
    }
}
