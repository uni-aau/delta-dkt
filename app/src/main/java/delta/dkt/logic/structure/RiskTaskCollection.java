package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class includes a collection of all the risk tasks that a player can get in a game.
 */
public class RiskTaskCollection {
    private ArrayList<RiskTask> riskTasks = new ArrayList<>();

    /**
     * The RiskTaskCollection includes ten tasks that a player can get in a game.
     */
    public RiskTaskCollection(){
        riskTasks.add(new RiskTask(1,"JailCard","Gehe in den Arrest!"));
        riskTasks.add(new RiskTask(2,"OutOfJailCard","Diese Karte befreit dich aus dem Arrest! Hebe diese Karte auf, du wirst sie brauchen."));
        riskTasks.add(new RiskTask(3,"TravelCard", "Besuch Salzburg und gehe am Mirabellplatz spazieren. Passierst du den Start, erhältst du 200€."));
        riskTasks.add(new RiskTask(4,"TravelCard","Gehe um 4 Felder zurück."));
        riskTasks.add(new RiskTask(5,"TravelCard","Rücke 7 Felder vor."));
        riskTasks.add(new RiskTask(6,"LuckyCard","Die Bank zahlt dir an Dividenden 60€."));
        riskTasks.add(new RiskTask(7,"LuckyCard","Für die Auswertung einer Erfindung erhältst du 140€ aus öffentlichen Mitteln."));
        riskTasks.add(new RiskTask(8,"UnluckyCard","Zahle 5€ Polizeistrafe."));
        riskTasks.add(new RiskTask(9,"UnluckyCard","Für unerlaubtes Parken bezahlst du 200€."));
        riskTasks.add(new RiskTask(10,"TravelCard","Besuch Graz und gehe auf der Annenstraße spazieren. Passierst du den Start, erhältst du 200€."));
    }

    /**
     * gets a random risk task from the RiskTaskCollection.
     * @return A random risk task.
     */
    public RiskTask getRandomRiskTask(){
        Random r = new Random();
        int index = r.nextInt(riskTasks.size()); //we
        return riskTasks.get(0);
    }
}
