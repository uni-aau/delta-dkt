package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static delta.dkt.logic.structure.PropertyLevel.NORMAL;

import org.junit.jupiter.api.BeforeEach;
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
        assertEquals("Murplatz", PropertyHandler.getProperties(5).getName(), "Name is not the expected name");
        assertEquals("Joseph-Haydn-Gasse", PropertyHandler.getProperties(10).getName(), "Name is not the expected name");
        assertEquals("Kärntnerstraße", PropertyHandler.getProperties(15).getName(), "Name is not the expected name");
        assertEquals("Stifterstraße", PropertyHandler.getProperties(20).getName(), "Name is not the expected name");
        assertEquals("Museumstraße", PropertyHandler.getProperties(22).getName(), "Name is not the expected name");
        assertEquals("Westbahnstraße", PropertyHandler.getProperties(26).getName(), "Name is not the expected name");
        assertEquals("Villacherstraße", PropertyHandler.getProperties(30).getName(), "Name is not the expected name");
        assertEquals("Maria-Theresien-Straße", PropertyHandler.getProperties(35).getName(), "Name is not the expected name");
        assertEquals("Arlbergstraße", PropertyHandler.getProperties(39).getName(), "Name is not the expected name");
        assertEquals("Rathausstraße", PropertyHandler.getProperties(40).getName(), "Name is not the expected name");
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
