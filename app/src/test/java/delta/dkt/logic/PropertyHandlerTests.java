package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static delta.dkt.logic.structure.PropertyLevel.NORMAL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import delta.dkt.logic.structure.Property;
import delta.dkt.logic.structure.PropertyHandler;

public class PropertyHandlerTests {

    Property p = new Property(6, 250, 96, NORMAL, 150);


    @Test
    void testGetPropertiesName() {
        assertEquals("Amtsplatz", PropertyHandler.getProperties(2).getName(), "Name is not the expected name");
    }

    @Test
    void testGetPropertiesExistingProperty(){
        assertNotEquals(p, PropertyHandler.getProperties(6), "Properties are not the same");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1 ,3, 4, 8, 9, 11, 13, 14, 18, 21, 23, 24, 28, 31, 33, 34, 38, 41})
    void testGetPropertiesNotExistingProperties(int input) {
        assertNull(PropertyHandler.getProperties(input), "Properties are not null");
    }
}
