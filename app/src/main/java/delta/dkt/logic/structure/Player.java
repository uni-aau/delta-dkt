package delta.dkt.logic.structure;

import java.util.ArrayList;

public class Player {
    static int START_CASH = 500;
    private static int _ID = 1;

    //? May be used to sync player data across clients
    private int id = Player._ID++;
    private String nickname;

    private Field position = null; //todo -> set this to be the start field
    private int cash = Player.START_CASH;
    private ArrayList<Property> properties = new ArrayList<>();

    //? May be used to check whether a player is timeoutet, e.g. prison, or not.
    private int suspention = 0;

    public Player (String nickname) {
        this.nickname = nickname;
    }

    //? Property handling

    /**
     * This function will be fetching the field based on the given location and will buy it, as long as it is not owned by anyone yet and the players cash is sufficient.
     */
    private boolean purchaseProperty (int location) {
        Property prop = (Property) MapHandling.getField(location); //todo Map.getField(location)

        if (prop == null) return false;

        if (prop.getOwner() != null) return false;
        if (prop.getPrice() > this.cash) return false;

        this.cash -= prop.getPrice();
        prop.setOwner(this);
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
        Property property = (Property) MapHandling.getField(location); //todo once again fetch the property by its ID from some global field handler.

        if (property == null) return false;

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
