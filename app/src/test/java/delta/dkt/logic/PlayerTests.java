package delta.dkt.logic;

import delta.dkt.logic.structure.MapHandling;
import delta.dkt.logic.structure.Player;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;

class PlayerTests {

    MapHandling mockMapHandling = mock(MapHandling.class);
    Player player = null;

    @BeforeEach
    void setup () {
        player = new Player("Mike");
    }
    
}
