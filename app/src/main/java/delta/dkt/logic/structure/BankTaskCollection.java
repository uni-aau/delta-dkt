package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

public class BankTaskCollection {
    private ArrayList<BankTask> bankTask = new ArrayList<>();

    public BankTaskCollection(){
        bankTask.add(new BankTask(1,"BankCard1","Do something"));
        bankTask.add(new BankTask(2,"BankCard2", "Do another thing"));
    }

    public BankTask getRandomBankTask(){
        Collections.shuffle(bankTask);
        return bankTask.get(0);
    }

}
