package delta.dkt.logic;

import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import delta.dkt.logic.structure.PropertyLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


class PropertyTests {

    Player playerMock = Mockito.mock(Player.class);
    Property dummy = null;

    @BeforeEach
    void setup () {
        dummy = new Property(12, 100, 10, PropertyLevel.NORMAL, 100);
    }

    //* Testing getter, setter functions

    /**
     * Check the location of the property by calling its inheritated getter function
     */
    @Test
    void checkLocation () {
        Assertions.assertEquals(12, dummy.getLocation());
    }


    /**
     * Checks whether the name is set by default and if it can be modified.
     */
    @Test
    void checkName () {
        Assertions.assertNull(dummy.getName());

        dummy.setName("Luxenburg");
        Assertions.assertEquals("Luxenburg", dummy.getName());
    }


    /**
     * Checks whether the provided level is set and accessible.
     */
    @Test
    void checkPropertyLevel () {
        Assertions.assertEquals(PropertyLevel.NORMAL, dummy.getLevel());
    }


    /**
     * This test checks whether the owner of a property can be set.
     */
    @Test
    void checkPropertyOwner () {
        Assertions.assertNull(dummy.getOwner());

        Assertions.assertDoesNotThrow(() -> dummy.setOwner(playerMock));

        Assertions.assertNotNull(dummy.getOwner());
        Assertions.assertEquals(playerMock, dummy.getOwner());
    }


    //* Testing Property Accessories modifications

    /**
     * Checks whether a hotel can be bought, including testing for game rules (only 1 hotel can be bought).
     */
    @Test
    void checkHotelAccessory () {
        testAccessory(0, 0);

        // not enough money to buy a hotel
        dummy.addHotel(0);
        testAccessory(0, 0);

        dummy.addHotel(1000);
        testAccessory(0, 1);

        // to many hotels
        dummy.addHotel(1000);
        testAccessory(0, 1);
    }

    /**
     * Checks whether a house can be added to a property and if the amount of houses is capped at 4.
     */
    @Test
    void check_addHouse () {
        testAccessory(0, 0);

        // not enough money to buy a house
        dummy.addHouse(0);
        testAccessory(0, 0);

        // add house nr 1
        dummy.addHouse(1000);
        testAccessory(1, 0);

        // add house nr 2
        dummy.addHouse(1000);
        testAccessory(2, 0);

        // add house nr 3
        dummy.addHouse(1000);
        testAccessory(3, 0);

        // add house nr 4
        dummy.addHouse(1000);
        testAccessory(4, 0);

        // theoretically add house nr 5 => would brake the rules of the game
        dummy.addHouse(1000);
        testAccessory(4, 0);
    }

    /**
     * Checks whether the transition from 4 houses to 1 hotel works as indentended
     */
    @Test
    void check_houseTransition () {
        // add 4 houses to the property
        check_addHouse();

        testAccessory(4, 0);

        dummy.addHotel(1000);
        testAccessory(0, 1);
    }

    /**
     * Checks whether a house can be added to the property when there is a hotel.
     */
    @Test
    void check_Invalid_addHouse_withHotel () {
        testAccessory(0, 0);
        dummy.addHotel(1000);

        dummy.addHouse(1000);
        testAccessory(0, 1);
    }

    /**
     * Checks whether the accessories of a property can be set to default (empty).
     */
    @Test
    void checkResetingAccessories () {
        testAccessory(0, 0);

        dummy.addHouse(1000);
        testAccessory(1, 0);

        dummy.addHouse(1000);
        testAccessory(2, 0);

        dummy.resetAccessories();
        testAccessory(0, 0);
    }


    //* Testing Rent Calculations


    /**
     * Checks whether the rent increases with the amount of houses and hotels, in regard to the property level (factor).
     */
    @Test
    void checkNormalRentCalculation () {
        int base = 10;
        double levelFactor = 1;

        testRentCalculations(base, levelFactor);
    }

    /**
     * Checks whether the rent calculation for a cheap property increases properly considering its level / factor, in this case cheap.
     */
    @Test
    void checkCheapRentCalculation () {
        dummy = new Property(10, 10, 10, PropertyLevel.CHEAP, 10, 10);

        int base = 10;
        double levelFactor = 0.5;

        testRentCalculations(base, levelFactor);
    }


    /**
     * Checks whether the rent calculation increases properly considering its level-factor, in this case premium.
     */
    @Test
    void checkPremiumRentCalculation () {
        dummy = new Property(10, 10, 10, PropertyLevel.PREMIUM, 10, 10);

        int base = 10;
        double levelFactor = 1.5;

        testRentCalculations(base, levelFactor);
    }


    //* Testing Property value evaluations


    /**
     * Checks whether the raw value of the property is calculated correctly.
     */
    @Test
    void checkRawPropertyValue () {
        Assertions.assertEquals(dummy.getPrice(), dummy.getRawValue());

        dummy.addHouse(1000);
        Assertions.assertEquals(dummy.getPrice() + dummy.getHousePrice(), dummy.getRawValue());

        dummy.addHouse(1000);
        Assertions.assertEquals(dummy.getPrice() + dummy.getHousePrice() * 2, dummy.getRawValue());

        dummy.addHouse(1000);
        Assertions.assertEquals(dummy.getPrice() + dummy.getHousePrice() * 3, dummy.getRawValue());

        dummy.addHouse(1000);
        Assertions.assertEquals(dummy.getPrice() + dummy.getHousePrice() * 4, dummy.getRawValue());


        dummy.addHotel(1000);
        Assertions.assertEquals(dummy.getPrice() + dummy.getHotelPrice(), dummy.getRawValue());
    }


    /**
     * Checks whether the sell value of a property for all the possible accessories is calculated correclty.
     */
    @Test
    void checkSellPropertyValue () {
        // no accessories
        testSellValue();

        dummy.addHouse(1000);
        testSellValue();

        dummy.addHouse(1000);
        testSellValue();

        dummy.addHouse(1000);
        testSellValue();

        dummy.addHouse(1000);
        testSellValue();


        dummy.addHotel(1000);
        testSellValue();
    }

    /**
     * Checks whether the actual value is equal to the expected value.
     */
    private void testSellValue () {
        Assertions.assertEquals(calculateExpectedSellValue(), dummy.getSellValue());
    }

    /**
     * @return the sell value of the property (50% price, 25% accessories)
     */
    private int calculateExpectedSellValue () {
        int value = dummy.getPrice() / 2;

        value += dummy.getHouses() * dummy.getHousePrice() * 0.25;
        value += dummy.getHotels() * dummy.getHotelPrice() * 0.25;

        return value;
    }



    /**
     * Calculates the expected rent of a propery
     * @param base The base rent of the property
     * @param levelFactor The factor of the property cheap (0.5), normal (1), premium (1.5)
     * @param houses The amount of houses on the property
     * @param hotel The amount of hotels on the property
     * @return Returns the expected rent of the property
     */
    private int expectedRent (int base, double levelFactor, int houses, int hotel) {
        int rent = base;

        // add house rent calculation
        rent += houses * base * levelFactor;

        // add hotel rent calculation
        rent += hotel * base * levelFactor * 6;

        return rent;
    }

    /**
     * This method is testing the different rent calculations for a property 0 - 4 houses and 0 - 1 hotel, based on the base rent and the level-factor of the property.
     * @param base The base rent of the property
     * @param levelFactor The factor of the property (0.5 | 1 | 1.5)
     */
    private void testRentCalculations (int base, double levelFactor) {
        Assertions.assertEquals(expectedRent(base, levelFactor, 0, 0), dummy.calculateRent()); // => base rent of the property

        dummy.addHouse(1000);
        Assertions.assertEquals(expectedRent(base, levelFactor, 1, 0), dummy.calculateRent());

        dummy.addHouse(1000);
        Assertions.assertEquals(expectedRent(base, levelFactor, 2, 0), dummy.calculateRent());

        dummy.addHouse(1000);
        Assertions.assertEquals(expectedRent(base, levelFactor, 3, 0), dummy.calculateRent());

        dummy.addHouse(1000);
        Assertions.assertEquals(expectedRent(base, levelFactor, 4, 0), dummy.calculateRent());


        dummy.addHotel(2000);
        testAccessory(0, 1);
        Assertions.assertEquals(expectedRent(base, levelFactor, 0, 1), dummy.calculateRent());
    }

    /**
     * Checks whether the properties accessories match the expected values.
     * @param expectedHouse The expected count of houses.
     * @param expectedHotel The expected count of hotels.
     */
    private void testAccessory (int expectedHouse, int expectedHotel) {
        Assertions.assertEquals((expectedHouse + expectedHotel), dummy.getAccessories().size());
        Assertions.assertEquals(expectedHouse, dummy.getHouses());
        Assertions.assertEquals(expectedHotel, dummy.getHotels());
    }
}
