package delta.dkt.logic;

import delta.dkt.logic.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PlayerTests {

    GameMap mockMapHandling = mock(GameMap.class);
    Player player = null;

    Property dummy = null;

    @BeforeEach
    void setup () {
        player = new Player("Mike");
        dummy = new Property(12, 10, 10, PropertyLevel.NORMAL, 10, 10);
    }

    /**
     * Checks whether the initialized nickname, via the constructor, has been set and is accesible.
     */
    @Test
    void checkNickname () {
        assertEquals("Mike", player.getNickname());
    }


    /**
     * Checks whether the property, the player is standing on can be bought successfully, including the references that are being made during this cycle.
     */
    @Test
    void checkBuyProperty_onLocation () {
        setMockRequirements_PropertyAquisition();

        assertTrue(player.buyProperty());

        assertEquals(1, player.getProperties().size());
        assertEquals(player, player.getProperties().get(0).getOwner());
        assertEquals((Player.START_CASH - dummy.getPrice()), player.getCash());

        verifyMockCalls();
    }


    /**
     * This method will set up the mock-requirements for testing the property-payment-cycle
     */
    void setMockRequirements_PropertyAquisition () {
        Game.map = mockMapHandling;
        when(mockMapHandling.getField(0)).thenReturn(dummy);
        when(mockMapHandling.getField(dummy.getLocation())).thenReturn(dummy);

        player = new Player("Patrick"); // => usage mock in instance variables
    }

    /**
     * This method will verify the calls of the previously mocked methods.
     */
    void verifyMockCalls () {
        verify(mockMapHandling).getField(0);
        verify(mockMapHandling).getField(dummy.getLocation());
    }
}
