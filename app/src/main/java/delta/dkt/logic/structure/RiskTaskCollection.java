package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

import delta.dkt.logic.structure.actioncards.CashRiskTask;
import delta.dkt.logic.structure.actioncards.OutOfJailCard;
import delta.dkt.logic.structure.actioncards.RiskTask;

/**
 * This class includes a collection of all the risk tasks that a player can get in a game.
 */
public class RiskTaskCollection {
    private final ArrayList<RiskTask> riskTask = new ArrayList<>();

    /**
     * The RiskTaskCollection includes ten tasks that a player can get in a game.
     */
    public RiskTaskCollection(){
        String luckyCardName = "lucky_card";
        String unluckyCardName = "unlucky_card";
        riskTask.add(new RiskTask(1,"jail_card","jail_card_text"));
        riskTask.add(new OutOfJailCard(2,"out_of_jail_card","out_of_jail_card_text"));

        riskTask.add(new CashRiskTask(6, luckyCardName,"lucky_card_text4", 60));
        riskTask.add(new CashRiskTask(7, luckyCardName,"lucky_card_text5", 140));
        riskTask.add(new CashRiskTask(8, unluckyCardName,"unlucky_card_text3", -5));
        riskTask.add(new CashRiskTask(9, unluckyCardName,"unlucky_card_text4", -200));
    }

    /**
     * gets a random risk task from the RiskTaskCollection.
     * @return A random risk task.
     */
    public RiskTask getRandomRiskTask(){
        Collections.shuffle(riskTask);
        return riskTask.get(0);
    }
}
