package delta.dkt.logic;

import delta.dkt.logic.structure.Player;
import delta.dkt.logic.structure.Property;
import delta.dkt.logic.structure.PropertyLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class PropertyTests {

    Player playerMock = Mockito.mock(Player.class);

    /**
     * This test checks whether the owner of a property can be set.
     */
    @Test
    void checkPropertyOwner() {
        Property p1 = new Property(12, 100, 10, PropertyLevel.normal, 100);
        Assertions.assertNull(p1.getOwner());

        Assertions.assertDoesNotThrow(() -> {
            p1.setOwner(playerMock);
        });

        Assertions.assertNotNull(p1.getOwner());
        Assertions.assertEquals(playerMock, p1.getOwner());
    }
}
