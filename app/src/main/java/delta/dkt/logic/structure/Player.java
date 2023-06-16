package delta.dkt.logic.structure;

import static ClientUIHandling.Constants.PREFIX_ASK_BUY_PROPERTY;
import static ClientUIHandling.Constants.PREFIX_PLAYER_PAYRENT;

import java.util.ArrayList;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;

public class Player implements Comparable<Player>{
    private boolean youGetOutOfPrisonCard = false;
    public static int _id = 1;

    //? May be used to sync player data across clients
    private int id = Player._id++;
    private String nickname = "Player-" + id;

    private Field position = Game.getMap().getField(1);
    private int cash = Config.INITIAL_CASH;
    private final ArrayList<Property> properties = new ArrayList<>();

    //? May be used to check whether a player is timeoutet, e.g. prison, or not.
    private int suspention = 0;
    private boolean hasCheated = false;

    private boolean hasReceivedPing;

    private Object pingToken;


    public Player(String nickname) {
        this.nickname = nickname;
        this.hasReceivedPing = false;
        this.pingToken = "";
    }

    public Player() {
        this.hasReceivedPing = false;
        this.pingToken = "";
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setId(int id) {
        this.id = id;
    }


    //? Property handling

    /**
     * This function will be fetching the field based on the given location and will buy it, as long as it is not owned by anyone yet and the players cash is sufficient.
     */
    private boolean purchaseProperty(int location) {
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
    public boolean buyProperty() {
        return purchaseProperty(this.position.getLocation());
    }

    /**
     * This function will attempt to buy a given property by its location.
     * Following the rules of DKT, this type of acquistion is normally not allowed, but can be
     * used to increase the game speed.
     */
    public boolean buyProperty(int location) {
        return purchaseProperty(location);
    }


    /**
     * This function sells a given property and adds the refunded value to the players cash and removes the accessories and ownership.
     *
     * @return Returns whether this operation was succesful or not.
     */
    private boolean refundProperty(int location) {
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
    public boolean sellProperty(int location) {
        return refundProperty(location);
    }


    //? Player suspensions

    /**
     * Will time out this player from moving for a given amount of rounds.
     */
    public void setSuspension(int rounds) {
        this.suspention = rounds;
    }

    /**
     * @return Returns whether the player is prohibited from moving, thus has a suspension / timeout.
     */
    public boolean isSuspended() {
        return this.suspention > 0;
    }

    /**
     * This function may be called when a player is supposed to move, but is prohibited from doing so, thus the remaining amount of suspended rounds, can be decreased by 1.
     */
    public void reduceSuspension() {
        if (this.suspention > 0) this.suspention--;
    }

    /**
     * This function may be called when the player uses his 'jail-free-card' or receives any other action, that removes his suspension / timeout.
     */
    public void resetSuspension() {
        this.suspention = 0;
    }

    //? Positioning

    /**
     * Moves a player to a given location and sets the corresponding field as his updated position.
     *
     * @param location The destination to which the player will be moved to.
     */
    private void move(int location) {
        //? Player has a suspension and is prohibited from moving.
        if (this.isSuspended()) return;

        if (location == 0) location++;
        this.position = Game.getMap().getField(location);
    }

    /**
     * This function will move the player by a given amount of steps.
     */
    public void moveSteps(int steps) {
        int location = (this.position.getLocation() + steps) % (Game.getMap().getFields().size() + 1);
        this.move(location);
    }

    /**
     * This function will move a player to a given location, e.g. to prison.
     *
     * @param location Represents the location of a field on the game map.
     */
    public void moveTo(int location) {
        this.move(location);
    }


    //? Getters

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public Field getPosition() {
        return position;
    }

    public int getCash() {
        return cash;
    }

    public boolean getYouGetOutOfPrisonCard(){return youGetOutOfPrisonCard;}


    /**
     * Sets the youGetOutOfPrisonCard value of a player.
     *
     * @param youGetOutOfPrisonCard is true, if the player has a youGetOutOfPrisonCard.
     */
    public void setYouGetOutOfPrisonCard(boolean youGetOutOfPrisonCard) {
        this.youGetOutOfPrisonCard = youGetOutOfPrisonCard;
    }

    public int getWealth() {

        int wealth = 0;
        for (Property property : properties){
            wealth+=property.getPrice();
            wealth+= property.getHouses()*property.getHousePrice();
            wealth+= property.getHotels()* property.getHotelPrice();
        }
        wealth+=cash;
        return wealth;
    }

    /**
     * This method will set the players cash.
     *
     * @param cash The new amount of cash the player is able to spend.
     */
    public void setCash(int cash) {
        this.cash = cash;
    }

    public ArrayList<Property> getProperties() {
        return properties;
    }

    public int getSuspention() {
        return suspention;
    }

    public void payRentTo(Player recipient, int amount) {
        this.cash -= amount;
        recipient.cash += amount;

    }

    public boolean hasCheated(){
        return hasCheated;
    }

    public void setCheat(boolean state){
        hasCheated = state;
    }


    @Override
    public int compareTo(Player o) {
        return this.id-o.id;
    }

    public boolean getAndClearPing() {
        synchronized (pingToken) {
            if (this.hasReceivedPing) {
                this.hasReceivedPing = false;
                return true;
            }
        }
        return false;
    }

    public void setHasReceivedPing(boolean hasReceivedPing) {
        synchronized (pingToken) {
            this.hasReceivedPing = hasReceivedPing;
        }
    }
}
