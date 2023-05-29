package delta.dkt.logic.structure;

import java.util.ArrayList;
import java.util.Random;

import delta.dkt.logic.structure.ActionCards.GoToJailActionCard;
import delta.dkt.logic.structure.ActionCards.OutOfJailCard;
import delta.dkt.logic.structure.ActionCards.PlayerMoneyCard;
import delta.dkt.logic.structure.ActionCards.PlayerPositionCard;

/**
 * This class includes a collection of all the risk tasks that a player can get in a game.
 */
public class TaskCollection {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * The RiskTaskCollection includes ten tasks that a player can get in a game.
     */
    public TaskCollection(){
        tasks.add(new GoToJailActionCard(1,"GoToJailCard","Gehe in den Arrest!"));
        tasks.add(new OutOfJailCard(2,"OutOfJailCard","Diese Karte befreit dich aus dem Arrest! Hebe diese Karte auf, du wirst sie brauchen."));
        tasks.add(new PlayerPositionCard(3,"TravelCard", "Besuch Salzburg und gehe am Mirabellplatz spazieren. Passierst du den Start, erhältst du 200€.",
                getFieldLocationByName("Mirabellplatz"), false));
        tasks.add(new PlayerPositionCard(4,"TravelCard","Gehe um 4 Felder zurück.", -4,true));
        tasks.add(new PlayerPositionCard(5,"TravelCard","Rücke 7 Felder vor.",7,true));
        tasks.add(new PlayerMoneyCard(6,"LuckyCard","Die Bank zahlt dir an Dividenden 60€.",60));
        tasks.add(new PlayerMoneyCard(7,"LuckyCard","Für die Auswertung einer Erfindung erhältst du 140€ aus öffentlichen Mitteln.",140));
        tasks.add(new PlayerMoneyCard(8,"UnluckyCard","Zahle 5€ Polizeistrafe.",-5));
        tasks.add(new PlayerMoneyCard(9,"UnluckyCard","Für unerlaubtes Parken bezahlst du 200€.",-200));
        tasks.add(new PlayerPositionCard(10,"TravelCard","Besuch Graz und gehe auf der Annenstraße spazieren. Passierst du den Start, erhältst du 200€.",
                                     getFieldLocationByName("Annenstraße"), false));
    }

    private int getFieldLocationByName(String name){
        return findFieldByName( name).getLocation();
    }

    private Field findFieldByName(String name){
        return Game.getMap().getFieldByName(name);
    }
    /**
     * gets a random risk task from the RiskTaskCollection.
     * @return A random risk task.
     */
    public Task getRandomTask(){
        Random r = new Random();
        int index = r.nextInt(tasks.size()); //we want a random number between 0 and task.size (excluded)
        return tasks.get(index);
    }
}
