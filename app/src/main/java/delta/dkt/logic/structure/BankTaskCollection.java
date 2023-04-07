package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

public class BankTaskCollection {
    private ArrayList<BankTask> bankTask = new ArrayList<>();

    public BankTaskCollection(){
        bankTask.add(new BankTask(1,"JailCard","Gehe in den Arrest"));
        bankTask.add(new BankTask(2,"LuckyCard", "FFür Bankzinsen erhältst du 180€ "));
        bankTask.add(new BankTask(3,"LuckyCard", "Für den Verkauf von Wertpapieren erhältst du 50€ "));
        bankTask.add(new BankTask(4,"UnluckyCard", "Bezahle deine Lebensversicherungsprämie: 120€"));
        bankTask.add(new BankTask(5,"JailCard","Gehe in den Arrest"));
        bankTask.add(new BankTask(6,"UnluckyCard", "An die Krankenkasse bezahle: 130€"));
        bankTask.add(new BankTask(7,"JailCard","Gehe in den Arrest"));
        bankTask.add(new BankTask(8,"LuckyCard", " An Reisespesenbeitrag erhältst du 40€"));
        bankTask.add(new BankTask(9,"UnluckyCard", "An die Krankenkasse bezahle: 130€"));
        bankTask.add(new BankTask(10,"JailCard","Gehe in den Arrest"));
    }

    public BankTask getRandomBankTask(){
        Collections.shuffle(bankTask);
        return bankTask.get(0);
    }

}
