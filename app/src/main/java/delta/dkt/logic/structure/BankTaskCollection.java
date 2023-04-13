package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class includes a collection of all the bank tasks that a player can get in a game.
 */

public class BankTaskCollection {
    private ArrayList<BankTask> bankTask = new ArrayList<>();

    /**
     * The BankTaskCollection includes ten tasks that a player can get in a game.
     */
    public BankTaskCollection() {
        bankTask.add(new BankTask(1, "JailCard", "Gehe in den Arrest"));
        bankTask.add(new BankTask(2, "LuckyCard", "Für Bankzinsen erhältst du 180€ "));
        bankTask.add(new BankTask(3, "LuckyCard", "Für den Verkauf von Wertpapieren erhältst du 50€ "));
        bankTask.add(new BankTask(4, "UnluckyCard", "Bezahle deine Lebensversicherungsprämie: 120€"));
        bankTask.add(new BankTask(5, "JailCard", "Gehe in den Arrest"));
        bankTask.add(new BankTask(6, "UnluckyCard", "An die Krankenkasse bezahle: 130€"));
        bankTask.add(new BankTask(7, "JailCard", "Gehe in den Arrest"));
        bankTask.add(new BankTask(8, "LuckyCard", " An Reisespesenbeitrag erhältst du 40€"));
        bankTask.add(new BankTask(9, "UnluckyCard", "An die Krankenkasse bezahle: 130€"));
        bankTask.add(new BankTask(10, "JailCard", "Gehe in den Arrest"));
    }

    /**
     * gets a random bank task from the BankTaskCollection.
     *
     * @return A random bank task.
     */
    public BankTask getRandomBankTask() {
        Collections.shuffle(bankTask);
        return bankTask.get(0);
    }

}
