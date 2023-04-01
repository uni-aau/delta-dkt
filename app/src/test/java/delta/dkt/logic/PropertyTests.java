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


}
