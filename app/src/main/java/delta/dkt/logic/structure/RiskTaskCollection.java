package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class includes a collection of all the risk tasks that a player can get in a game.
 */
public class RiskTaskCollection {
    private final ArrayList<RiskTask> riskTask = new ArrayList<>();

    /**
     * The RiskTaskCollection includes ten tasks that a player can get in a game.
     */
    public RiskTaskCollection(){
        riskTask.add(new RiskTask(1,"JailCard","Gehe in den Arrest!"));
        riskTask.add(new RiskTask(2,"OutOfJailCard","Diese Karte befreit dich aus dem Arrest! Hebe diese Karte auf, du wirst sie brauchen."));
        riskTask.add(new RiskTask(3,"TravelCard", "Besuch Salzburg und gehe am Mirabellplatz spazieren. Passierst du den Start, erhältst du 200€."));
        riskTask.add(new RiskTask(4,"TravelCard","Gehe um 4 Felder zurück."));
        riskTask.add(new RiskTask(5,"TravelCard","Rücke 7 Felder vor."));
        riskTask.add(new RiskTask(6,"LuckyCard","Die Bank zahlt dir an Dividenden 60€."));
        riskTask.add(new RiskTask(7,"LuckyCard","Für die Auswertung einer Erfindung erhältst du 140€ aus öffentlichen Mitteln."));
        riskTask.add(new RiskTask(8,"UnluckyCard","Zahle 5€ Polizeistrafe."));
        riskTask.add(new RiskTask(9,"UnluckyCard","Für unerlaubtes Parken bezahlst du 200€."));
        riskTask.add(new RiskTask(10,"TravelCard","Besuch Graz und gehe auf der Annenstraße spazieren. Passierst du den Start, erhältst du 200€."));
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
