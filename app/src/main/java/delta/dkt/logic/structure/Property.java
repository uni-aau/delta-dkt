package delta.dkt.logic.structure;

import java.util.ArrayList;

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


}
