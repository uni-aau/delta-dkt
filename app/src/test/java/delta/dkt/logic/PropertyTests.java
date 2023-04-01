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
