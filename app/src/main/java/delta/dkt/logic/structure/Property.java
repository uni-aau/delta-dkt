package delta.dkt.logic.structure;

import java.util.ArrayList;

/**
 * This class represents a property of the game that can be bought, where rent can be paid and more.
 */
public class Property extends Field {
    private int price;
    private int baseRent;

    private PropertyLevel level;
    private Player owner;

    private int housePrice;
    private int hotelPrice;

    private ArrayList<PropertyAccessory> accessories = new ArrayList<>();

    /**
     * Create a new property that has a given location on the field.
     * @param location The properties location
     * @param price The properties price for which it can be bought
     * @param baseRent The rent that can be collected, when there are no accessories on the property
     * @param level Defines the scaling of the properties rent. Cheap = * 0.5, Normal = * 1, Premium = * 1.5
     * @param housePrice Defines the price for which a house can be bought for the given property
     * @param hotelPrice Defines the price for which a houtel can be bought on the given property
     */
    public Property (int location, int price, int baseRent, PropertyLevel level, int housePrice, int hotelPrice) {
        super(location);
        this.price = price;
        this.baseRent = baseRent;
        this.level = level;
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
    }

    /**
     * Create a new property that has a given location on the field.
     * @param location The properties location
     * @param price The properties price for which it can be bought
     * @param baseRent The rent that can be collected, when there are no accessories on the property
     * @param level Defines the scaling of the properties rent. Cheap = * 0.5, Normal = * 1, Premium = * 1.5
     * @param housePrice Defines the price for which a house can be bought for the given property
     */
    public Property (int location, int price, int baseRent, PropertyLevel level, int housePrice) {
        super(location);
        this.price = price;
        this.baseRent = baseRent;
        this.level = level;
        this.housePrice = housePrice;
        this.hotelPrice = this.housePrice * 2;
    }

    /**
     * Sets the owner of the property, e.g. when the property is bought by a player.
     * @param owner The player who bought this property.
     */
    public void setOwner (Player owner) {
        this.owner = owner;
    }

    /**
     * This method will calculate the rent based on the accessories on it, its baseRent and its property level.
     * @return The rent to be paid by any person who visits this property.
     */
    public int calculateRent () {
        if (this.accessories.size() == 0) return this.baseRent;

        double levelFactor = 1;

        if (this.level == PropertyLevel.CHEAP) levelFactor = 0.5;
        if (this.level == PropertyLevel.PREMIUM) levelFactor = 1.5;

        // A house is worth 6 houses
        if (this.accessories.get(0) == PropertyAccessory.HOTEL)
            return (int) (this.baseRent + this.baseRent * levelFactor * 6);

        // otherwise return the product of: houses on the proeprty multiplied with its baseRent and its level factor.
        return (int) (this.baseRent + this.baseRent * this.accessories.size() * levelFactor);
    }

    /**
     * This method will reset the accessories on the property. Thus, all houses or a hotel will be removed from it.
     */
    public void resetAccessories () {
        this.accessories.clear();
    }

    /**
     * This method adds a house to the property as long as it does not break the rules of the game.
     * @param playerCash The cash of the player which is used to check whether the player is able to buy a house.
     * @return Returns whether a house has been added to the property or not.
     */
    public boolean addHouse (int playerCash) {
        // only one hotel can be on the property.
        if (this.accessories.size() == 1 && this.accessories.get(0) == PropertyAccessory.HOTEL) return false;

        // max of 4 houses on the property.
        if (this.accessories.size() >= 4) return false;

        // the player is able to pay a house
        if (playerCash < this.housePrice) return false;

        this.accessories.add(PropertyAccessory.HOUSE);
        return true;
    }

    /**
     * This method adds a house to this property as long the rules of the game are not broken. When there are
     * @param playerCash The cash of player is used to check if a hotel can be bought.
     * @return Returns whether a hotel has been added to the property or not.
     */
    public boolean addHotel (int playerCash) {
        // when there is at least one house but less than 4 houses (hotel could replace them) on the property than a hotel can't be bought.
        if (this.accessories.size() > 0 && this.accessories.size() < 4) return false;

        //? this way a hotel could be bought before houses are on the property or when there 4 houses.
        //? depending on the prefered game speed this setting could be changed.

        // player can't efford to pay a hotel
        if (playerCash < this.hotelPrice) return false;

        // when there are 4 houses on the property, replace them with a hotel.
        if (this.accessories.size() == 4) this.resetAccessories();

        this.accessories.add(PropertyAccessory.HOTEL);
        return true;
    }

    /**
     * @return Returns the total value of the property, including the price and its accessories.
     */
    public int getRawValue () {
        int total = this.price;

        total += getHouses() * this.housePrice;
        total += getHotels() * this.hotelPrice;

        return total;
    }

    /**
     * @return Returns the value for which the property could be sold. Calculation: 50% property price, 25% accessory price.
     */
    public int getSellValue () {
        // only 50% of the price is refundable
        double value = this.price * 0.5;

        // only 25% of the accessories value is refundable
        value += this.getHouses() * this.housePrice * 0.25;
        value += this.getHotels() * this.hotelPrice * 0.25;

        return (int) value;
    }


    // Getters

    /**
     * @return Returns the count of houses on the property
     */
    public int getHouses () {
        if (this.accessories.size() == 0) return 0;

        if (this.accessories.get(0) == PropertyAccessory.HOTEL) return 0;

        return this.accessories.size();
    }

    /**
     * @return Returns the count of hotels on the property, can only be (0|1).
     */
    public int getHotels () {
        if (this.accessories.size() == 0) return 0;

        if (this.accessories.get(0) == PropertyAccessory.HOTEL) return 1;

        // there can only be 1 hotel on a property.
        return 0;
    }


    /**
     * @return Returns the price of the property for which it can be bought.
     */
    public int getPrice () {
        return price;
    }

    /**
     * @return Returns the type/level of property, which has direct impact on the rent calculation. It can either be a property that has a good, normal or cheap rent ratio.
     */
    public PropertyLevel getLevel () {
        return level;
    }

    /**
     * @return Returns the Player that has bought, thus owns this property.
     */
    public Player getOwner () {
        return owner;
    }

    /**
     * @return Returns the price for which a house can be bought on to this property.
     */
    public int getHousePrice () {
        return housePrice;
    }

    /**
     * @return Returns the price for which a hotel can be bought on to this property.
     */
    public int getHotelPrice () {
        return hotelPrice;
    }

    /**
     * @return Returns the current accesssories on the property, thus a hotel, its houses or an empty list.
     */
    public ArrayList<PropertyAccessory> getAccessories () {
        return accessories;
    }


}
