package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class includes a collection of all the risk tasks that a player can get in a game.
 */
public class RiskTaskCollection {
    private ArrayList<RiskTask> riskTask = new ArrayList<>();

    /**
     * The RiskTaskCollection includes ten tasks that a player can get in a game.
     */
    public RiskTaskCollection(){
        riskTask.add(new RiskTask(1,"jail_card","jail_card_text"));
        riskTask.add(new RiskTask(2,"out_of_jail_card","out_of_jail_card_text"));
        riskTask.add(new RiskTask(3,"travel_card", "travel_card_text1"));
        riskTask.add(new RiskTask(4,"travel_card","travel_card_text2"));
        riskTask.add(new RiskTask(5,"travel_card","travel_card_text3"));
        riskTask.add(new RiskTask(6,"lucky_card","lucky_card_text4"));
        riskTask.add(new RiskTask(7,"lucky_card","lucky_card_text5"));
        riskTask.add(new RiskTask(8,"unlucky_card","unlucky_card_text3"));
        riskTask.add(new RiskTask(9,"unlucky_card","unlucky_card_text4"));
        riskTask.add(new RiskTask(10,"travel_card","travel_card_text4"));
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
