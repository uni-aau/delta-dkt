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
        String luckyCardName = "lucky_card";
        String unluckyCardName = "unlucky_card";
//        bankTask.add(new BankTask(1,"jail_card","jail_card_text"));
        bankTask.add(new CashBankTask(2,luckyCardName, "lucky_card_text1", 180));
        bankTask.add(new CashBankTask(3,luckyCardName, "lucky_card_text2", 50));
        bankTask.add(new CashBankTask(4,unluckyCardName, "unlucky_card_text1", -120));
//        bankTask.add(new BankTask(5,"jail_card","jail_card_text"));
        bankTask.add(new CashBankTask(6,unluckyCardName, "unlucky_card_text2", -130));
//        bankTask.add(new BankTask(7,"jail_card","jail_card_text"));
        bankTask.add(new CashBankTask(8,luckyCardName, "lucky_card_text3", 40));
        bankTask.add(new CashBankTask(9,unluckyCardName, "unlucky_card_text2", -130));
//        bankTask.add(new BankTask(10,"jail_card","jail_card_text"));
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
