package delta.dkt.logic;

import delta.dkt.logic.structure.MapHandling;
import delta.dkt.logic.structure.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class PlayerTests {

    MapHandling mockMapHandling = mock(MapHandling.class);
    Player player = null;

    @BeforeEach
    void setup () {
        player = new Player("Mike");
    }

    /**
     * Checks whether the initialized nickname, via the constructor, has been set and is accesible.
     */
    @Test
    void checkNickname () {
        assertEquals("Mike", player.getNickname());
    }
    

}
