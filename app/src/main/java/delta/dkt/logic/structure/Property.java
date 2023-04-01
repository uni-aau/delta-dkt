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
     *
     * @param location   The properties location
     * @param price      The properties price for which it can be bought
     * @param baseRent   The rent that can be collected, when there are no accessories on the property
     * @param level      Defines the scaling of the properties rent. Cheap = * 0.5, Normal = * 1, Premium = * 1.5
     * @param housePrice Defines the price for which a house can be bought for the given property
     * @param hotelPrice Defines the price for which a houtel can be bought on the given property
     */
    public Property(int location, int price, int baseRent, PropertyLevel level, int housePrice, int hotelPrice) {
        super(location);
        this.price = price;
        this.baseRent = baseRent;
        this.level = level;
        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
    }

    /**
     * Create a new property that has a given location on the field.
     *
     * @param location   The properties location
     * @param price      The properties price for which it can be bought
     * @param baseRent   The rent that can be collected, when there are no accessories on the property
     * @param level      Defines the scaling of the properties rent. Cheap = * 0.5, Normal = * 1, Premium = * 1.5
     * @param housePrice Defines the price for which a house can be bought for the given property
     */
    public Property(int location, int price, int baseRent, PropertyLevel level, int housePrice) {
        super(location);
        this.price = price;
        this.baseRent = baseRent;
        this.level = level;
        this.housePrice = housePrice;
        this.hotelPrice = this.housePrice * 2;
    }

    /**
     * Sets the owner of the property, e.g. when the property is bought by a player.
     *
     * @param owner The player who bought this property.
     */
    public void setOwner(Player owner) {
        this.owner = owner;
    }


    /**
     * This method will calculate the rent based on the accessories on it, its baseRent and its property level.
     *
     * @return The rent to be paid by any person who visits this property.
     */
    public int calculateRent() {
        if (this.accessories.size() == 0) return this.baseRent;

        double levelFactor = 1;

        if (this.level == PropertyLevel.cheap) levelFactor = 0.5;
        if (this.level == PropertyLevel.premium) levelFactor = 1.5;

        // A house is worth 6 houses
        if (this.accessories.get(0) == PropertyAccessory.hotel)
            return (int) (this.baseRent * levelFactor * 6);

        // otherwise return the product of: houses on the proeprty multiplied with its baseRent and its level factor.
        return (int) (this.baseRent * this.accessories.size() * levelFactor);
    }

    /**
     * This method will reset the accessories on the property. Thus, all houses or a hotel will be removed from it.
     */
    public void resetAccessories() {
        this.accessories.clear();
    }

    /**
     * @return Returns the price of the property for which it can be bought.
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return Returns the type/level of property, which has direct impact on the rent calculation. It can either be a property that has a good, normal or cheap rent ratio.
     */
    public PropertyLevel getLevel() {
        return level;
    }

    /**
     * @return Returns the Player that has bought, thus owns this property.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * @return Returns the price for which a house can be bought on to this property.
     */
    public int getHousePrice() {
        return housePrice;
    }

    /**
     * @return Returns the price for which a hotel can be bought on to this property.
     */
    public int getHotelPrice() {
        return hotelPrice;
    }

    /**
     * @return Returns the current accesssories on the property, thus a hotel, its houses or an empty list.
     */
    public ArrayList<PropertyAccessory> getAccessories() {
        return accessories;
    }


}
