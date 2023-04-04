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


    @Test
    void checkBuyProperty_onLocation () {
        testBuyProperty(true);
    }


    /**
     * This method will attempt to buy a property either on the players current position or any property identified by its location.
     */
    void testBuyProperty (boolean standingOnTop) {
        Game.map = mockMapHandling;
        Player.START_CASH = 1000;
        when(mockMapHandling.getField(0)).thenReturn(dummy);
        when(mockMapHandling.getField(dummy.getLocation())).thenReturn(dummy);

        player = new Player("Patrick"); // => usage mock in instance variables

        boolean paymentState = false;

        if (standingOnTop) paymentState = player.buyProperty();
        else paymentState = player.buyProperty(dummy.getLocation());

        assertTrue(paymentState);

        assertEquals(1, player.getProperties().size());
        assertEquals(player, player.getProperties().get(0).getOwner());
        assertEquals((Player.START_CASH - dummy.getPrice()), player.getCash());

        verify(mockMapHandling).getField(0);
        verify(mockMapHandling).getField(dummy.getLocation());
    }


}
