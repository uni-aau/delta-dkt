package delta.dkt.logic;

import delta.dkt.logic.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTests {

    GameMap mockMapHandling = mock(GameMap.class);
    Player player = null;

    Property testProperty1 = null;
    Property testProperty2 = null;

    @BeforeEach
    void setup () {
        player = new Player("Mike");
        testProperty1 = new Property(12, 10, 10, PropertyLevel.NORMAL, 10, 10);
        testProperty2 = new Property(14, 10, 10, PropertyLevel.NORMAL, 10, 10);
    }

    /**
     * Checks whether the initialized nickname, via the constructor, has been set and is accesible.
     */
    @Test
    void checkNickname () {
        assertEquals("Mike", player.getNickname());
    }


    /**
     * Checks whether a property can be bought when standing on top of it and when a player would choose to buy any (free) property.
     * Including reference checks included in this payment cycle.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkSuccessfulPropertyAcquisition (boolean onTop) {
        setMockRequirements_PropertyAquisition();

        Property buying = onTop ? testProperty1 : testProperty2;

        if (onTop) {
            assertTrue(player.buyProperty());
        } else {
            assertTrue(player.buyProperty(testProperty2.getLocation()));
        }

        testSuccessfulPropertyAcquisition(1, buying.getPrice());

        verifyMockCalls(buying);
    }


    /**
     * Checks whether the proceeding of a player attempting to buy a property when he does not have enough cash.
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_LowCash (boolean onTop) {
        Player.START_CASH = 0;
        setMockRequirements_PropertyAquisition();

        Property buying = onTop ? testProperty1 : testProperty2;

        if (onTop) {
            assertFalse(player.buyProperty());
        } else {
            assertFalse(player.buyProperty(testProperty2.getLocation()));
        }

        verifyMockCalls(buying);
    }


    /**
     * Checks whether a property cannot be bought when it is already owned by someone else.
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_Owned (boolean onTop) {
        Property buying = onTop ? testProperty1 : testProperty2;
        buying.setOwner(new Player("Maxi"));

        setMockRequirements_PropertyAquisition();

        if (onTop) {
            assertFalse(player.buyProperty());
        } else {
            assertFalse(player.buyProperty(testProperty2.getLocation()));
        }

        verifyMockCalls(buying);
    }


    /**
     * This method will set up the mock-requirements for testing the property-payment-cycle
     */
    void setMockRequirements_PropertyAquisition () {
        Game.map = mockMapHandling;
        when(mockMapHandling.getField(0)).thenReturn(testProperty1);
        when(mockMapHandling.getField(testProperty1.getLocation())).thenReturn(testProperty1);
        when(mockMapHandling.getField(testProperty2.getLocation())).thenReturn(testProperty2);

        player = new Player("Patrick"); // => usage mock in instance variables
    }

    /**
     * This method will verify the calls of the previously mocked methods.
     */
    void verifyMockCalls (Property bought) {
        verify(mockMapHandling).getField(0);
        verify(mockMapHandling).getField(bought.getLocation());
    }

    /**
     * This method will check whether the property has been added to the players collection,
     * it will check if the owner property has been set correctly,
     * it will check whether the player cash has been modified correctly.
     */
    void testSuccessfulPropertyAcquisition (int exProperties, int propertyPrice) {
        assertEquals(exProperties, player.getProperties().size());
        assertEquals(player, player.getProperties().get(0).getOwner());
        assertEquals((Player.START_CASH - propertyPrice), player.getCash());
    }
}
