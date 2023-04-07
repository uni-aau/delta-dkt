package delta.dkt.logic.structure;

import java.util.ArrayList;

public class Player {
    private static int startCash = 500;

    public static int getStartCash () {
        return startCash;
    }

    public static void setStartCash (int startCash) {
        Player.startCash = startCash;
    }


    private static int _id = 1;

    //? May be used to sync player data across clients
    private int id = Player._id++;
    private String nickname = "Player-" + id;

    private Field position = Game.getMap().getField(0);
    private int cash = Player.startCash;
    private ArrayList<Property> properties = new ArrayList<>();

    //? May be used to check whether a player is timeoutet, e.g. prison, or not.
    private int suspention = 0;

    public Player (String nickname) {
        this.nickname = nickname;
    }

    public Player () {

    }

    //? Property handling

    /**
     * This function will be fetching the field based on the given location and will buy it, as long as it is not owned by anyone yet and the players cash is sufficient.
     */
    private boolean purchaseProperty (int location) {
        Field located = Game.getMap().getField(location);
        if (!(located instanceof Property)) return false;

        Property prop = (Property) located;

        if (prop.getOwner() != null) return false;
        if (prop.getPrice() > this.cash) return false;

        this.cash -= prop.getPrice();
        prop.setOwner(this);

        this.properties.add(prop);
        return true;
    }

    /**
     * This function will attempt to buy the property on which the player currently is standing on.
     */
    public boolean buyProperty () {
        return purchaseProperty(this.position.getLocation());
    }

    /**
     * This function will attempt to buy a given property by its location.
     * Following the rules of DKT, this type of acquistion is normally not allowed, but can be
     * used to increase the game speed.
     */
    public boolean buyProperty (int location) {
        return purchaseProperty(location);
    }


    /**
     * This function sells a given property and adds the refunded value to the players cash and removes the accessories and ownership.
     * @return Returns whether this operation was succesful or not.
     */
    private boolean refundProperty (int location) {
        Field located = Game.getMap().getField(location);
        if (!(located instanceof Property)) return false;

        Property property = (Property) located;

        if (property.getOwner() == null) return false;
        if (property.getOwner().getId() != this.getId()) return false;

        this.cash += property.getSellValue();

        property.setOwner(null);
        property.resetAccessories();
        this.properties.remove(property);
        return true;
    }

    /**
     * This function will attempt to sell a given property. The player does not have to be on top of it.
     */
    public boolean sellProperty (int location) {
        return refundProperty(location);
    }



    //? Player suspensions

    /**
     * Will time out this player from moving for a given amount of rounds.
     */
    public void setTimeout (int rounds) {
        this.suspention = rounds;
    }

    /**
     * @return Returns whether the player is prohibited from moving, thus has a suspension / timeout.
     */
    public boolean isTimeoutet () {
        return this.suspention > 0;
    }

    /**
     * This function may be called when a player is supposed to move, but is prohibited from doing so, thus the remaining amount of suspended rounds, can be decreased by 1.
     */
    public void reduceTimeout () {
        if (this.suspention > 0) this.suspention--;
    }

    /**
     * This function may be called when the player uses his 'jail-free-card' or receives any other action, that removes his suspension / timeout.
     */
    public void resetTimeout () {
        this.suspention = 0;
    }

    //? Positioning

    /**
     * Moves a player to a given location and sets the corresponding field as his updated position.
     * @param location The destination to which the player will be moved to.
     */
    private void move (int location) {
        this.position = Game.getMap().getField(location);
    }

    /**
     * This function will move the player by a given amount of steps.
     */
    public void moveSteps (int steps) {
        int location = (this.position.getLocation() + steps) % Game.getMap().getFields().size();
        this.move(location);
    }


    //? Getters

    public int getId () {
        return id;
    }

    public String getNickname () {
        return nickname;
    }

    public Field getPosition () {
        return position;
    }

    public int getCash () {
        return cash;
    }

    public ArrayList<Property> getProperties () {
        return properties;
    }

    public int getSuspention () {
        return suspention;
    }
}
