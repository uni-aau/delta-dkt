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
     * Checks whether the property on which the player is standing on can be bought.
     */
    @Test
    void checkBuyProperty_onLocation () {
        Game.map = mockMapHandling;
        Player.START_CASH = 1000;
        when(mockMapHandling.getField(0)).thenReturn(dummy);
        when(mockMapHandling.getField(dummy.getLocation())).thenReturn(dummy);

        player = new Player("Patrick"); // => usage mock in instance variables

        boolean paymentState = player.buyProperty();
        assertTrue(paymentState);

        assertEquals(1, player.getProperties().size());
        assertEquals(player, player.getProperties().get(0).getOwner());
        assertEquals((Player.START_CASH - dummy.getPrice()), player.getCash());

        verify(mockMapHandling).getField(0);
        verify(mockMapHandling).getField(dummy.getLocation());
    }


}
