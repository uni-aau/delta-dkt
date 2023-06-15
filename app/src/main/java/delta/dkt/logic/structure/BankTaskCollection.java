package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class includes a collection of all the bank tasks that a player can get in a game.
 */

public class BankTaskCollection {
    private final ArrayList<BankTask> bankTask = new ArrayList<>();

    /**
     * The BankTaskCollection includes ten tasks that a player can get in a game.
     */
    public BankTaskCollection(){
        bankTask.add(new BankTask(1,"jail_card","jail_card_text"));
        bankTask.add(new BankTask(2,"lucky_card", "lucky_card_text1"));
        bankTask.add(new BankTask(3,"lucky_card", "lucky_card_text2"));
        bankTask.add(new BankTask(4,"unlucky_card", "unlucky_card_text1"));
        bankTask.add(new BankTask(5,"jail_card","jail_card_text"));
        bankTask.add(new BankTask(6,"unlucky_card", "unlucky_card_text2"));
        bankTask.add(new BankTask(7,"jail_card","jail_card_text"));
        bankTask.add(new BankTask(8,"lucky_card", "lucky_card_text3"));
        bankTask.add(new BankTask(9,"unlucky_card", "unlucky_card_text2"));
        bankTask.add(new BankTask(10,"jail_card","jail_card_text"));
    }
    /**
     * gets a random bank task from the BankTaskCollection.
     * @return A random bank task.
     */
    public BankTask getRandomBankTask(){
        Collections.shuffle(bankTask);
        return bankTask.get(0);
    }

}
