package delta.dkt.logic.structure;

import static ClientUIHandling.Constants.PREFIX_PLAYER_BUYPROPERTY;
import static ClientUIHandling.Constants.PREFIX_PLAYER_PAYRENT;

import java.util.ArrayList;

import ClientUIHandling.Config;
import ClientUIHandling.Constants;
import ServerLogic.ServerActionHandler;

public class Player implements Comparable<Player>{
    private static int _id = 1;

    //? May be used to sync player data across clients
    private int id = Player._id++;
    private String nickname = "Player-" + id;

    private Field position = Game.getMap().getField(1);
    private int cash = Config.INITIAL_CASH;
    private ArrayList<Property> properties = new ArrayList<>();

    //? May be used to check whether a player is timeoutet, e.g. prison, or not.
    private int suspention = 0;

    public Player(String nickname) {
        this.nickname = nickname;
    }

    public Player() {

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

        if (this.position instanceof Property) {
            if(((Property) this.position).getOwner() != null) {
                //START-NOSCAN
                ServerActionHandler.triggerAction(PREFIX_PLAYER_PAYRENT, this.getId());
                //END-NOSCAN
            }else{ //property can be bought , ask user
                //START-NOSCAN
                //TODO: Ask the user if he wants to buy the property.
               // ServerActionHandler.triggerAction(PREFIX_PLAYER_BUYPROPERTY, this.getId()); //we only need one parameter , and thates the id of the player
                //END-NOSCAN
            }
        } else if (this.position instanceof SpecialField) {
            if (this.position.getName().equals("VermögensAbgabe") || this.position.getName().equals("Steuerabgabe")) {
                //START-NOSCAN
                ServerActionHandler.triggerAction(Constants.PREFIX_PAY_TAX, this.getId());
                //END-NOSCAN
            }
        }
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

    @Override
    public int compareTo(Player o) {
        return this.id-o.id;
    }
}
