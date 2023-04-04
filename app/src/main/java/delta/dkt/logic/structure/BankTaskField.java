package delta.dkt.logic.structure;

public class BankTaskField extends Field{

    BankTask bankTask;

    public BankTaskField(int location, BankTask bankTask) {
        super(location);
        this.bankTask = bankTask;
    }
}
