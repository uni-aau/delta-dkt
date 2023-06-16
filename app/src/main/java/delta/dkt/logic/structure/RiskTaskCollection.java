package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

import delta.dkt.logic.structure.ActionCards.OutOfJailCard;

/**
 * This class includes a collection of all the risk tasks that a player can get in a game.
 */
public class RiskTaskCollection {
    private final ArrayList<RiskTask> riskTask = new ArrayList<>();

    /**
     * The RiskTaskCollection includes ten tasks that a player can get in a game.
     */
    public RiskTaskCollection(){
        riskTask.add(new OutOfJailCard(1,"jail_card","jail_card_text"));
        riskTask.add(new OutOfJailCard(2,"out_of_jail_card","out_of_jail_card_text"));
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
