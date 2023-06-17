package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

import delta.dkt.logic.structure.actioncards.BankTask;
import delta.dkt.logic.structure.actioncards.CashBankTask;

/**
 * This class includes a collection of all the bank tasks that a player can get in a game.
 */

public class BankTaskCollection {
    private final ArrayList<BankTask> bankTask = new ArrayList<>();

    /**
     * The BankTaskCollection includes ten tasks that a player can get in a game.
     */
    public BankTaskCollection() {
        String luckyCardName = "lucky_card";
        String unluckyCardName = "unlucky_card";
        String jailCardName = "jail_card";
        String jailCardText = "jail_card_text";

        bankTask.add(new BankTask(1, jailCardName, jailCardText));
        bankTask.add(new CashBankTask(2, luckyCardName, "lucky_card_text1", 160));
        bankTask.add(new CashBankTask(3, luckyCardName, "lucky_card_text2", 80));
        bankTask.add(new CashBankTask(4, unluckyCardName, "unlucky_card_text1", -100));
        bankTask.add(new BankTask(5, jailCardName, jailCardText));
        bankTask.add(new CashBankTask(6, unluckyCardName, "unlucky_card_text2", -120));
        bankTask.add(new CashBankTask(7, luckyCardName, "lucky_card_text3", 50));
        bankTask.add(new CashBankTask(8, unluckyCardName, "unlucky_card_text2", -120));
        bankTask.add(new BankTask(9, jailCardName, jailCardText));
    }

    /**
     * gets a random bank task from the BankTaskCollection.
     *
     * @return A random bank task.
     */
    public BankTask getRandomBankTask(){
        Collections.shuffle(bankTask);
        return bankTask.get(0);
    }

}
