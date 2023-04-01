package delta.dkt.logic;

import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import delta.dkt.logic.structure.PropertyLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class PropertyTests {

    Player playerMock = Mockito.mock(Player.class);
    Property dummy = null;

    @BeforeEach
    void setup() {
        dummy = new Property(12, 100, 10, PropertyLevel.normal, 100);
    }


    /**
     * This test checks whether the owner of a property can be set.
     */
    @Test
    void checkPropertyOwner() {
        Assertions.assertNull(dummy.getOwner());

        Assertions.assertDoesNotThrow(() -> {
            dummy.setOwner(playerMock);
        });

        Assertions.assertNotNull(dummy.getOwner());
        Assertions.assertEquals(playerMock, dummy.getOwner());
    }

    /**
     * Checks whether a hotel can be bought, including testing for game rules (only 1 hotel can be bought).
     */
    @Test
    void checkHotelAccessory() {
        testAccessory(0, 0);

        dummy.addHotel(1000);
        testAccessory(0, 1);

        dummy.addHotel(1000);
        testAccessory(0, 1);
    }

    /**
     * Checks whether a house can be added to a property and if the amount of houses is capped at 4.
     */
    @Test
    void check_addHouse() {
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
    void check_houseTransition() {
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
    void check_Invalid_addHouse_withHotel() {
        testAccessory(0, 0);
        dummy.addHotel(1000);

        dummy.addHouse(1000);
        testAccessory(0, 1);
    }


    /**
     * Checks whether the rent increases with the amount of houses and hotels, in regard to the property level (factor).
     */
    @Test
    void checkNormalRentCalculation() {
        Assertions.assertEquals(10, dummy.calculateRent());

        dummy.addHouse(1000);
        Assertions.assertEquals(20, dummy.calculateRent());

        dummy.addHouse(1000);
        Assertions.assertEquals(30, dummy.calculateRent());

        dummy.addHouse(1000);
        Assertions.assertEquals(40, dummy.calculateRent());

        dummy.addHouse(1000);
        Assertions.assertEquals(50, dummy.calculateRent());


        dummy.addHotel(2000);
        testAccessory(0, 1);
        Assertions.assertEquals(70, dummy.calculateRent());
    }

    /**
     * Checks whether the properties accessories match the expected values.
     *
     * @param expectedHouse The expected count of houses.
     * @param expectedHotel The expected count of hotels.
     */
    private void testAccessory(int expectedHouse, int expectedHotel) {
        Assertions.assertEquals((expectedHouse + expectedHotel), dummy.getAccessories().size());
        Assertions.assertEquals(expectedHouse, dummy.getHouses());
        Assertions.assertEquals(expectedHotel, dummy.getHotels());
    }
}
