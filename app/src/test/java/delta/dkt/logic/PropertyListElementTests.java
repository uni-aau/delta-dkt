package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import delta.dkt.logic.structure.PropertyListElement;

class PropertyListElementTests {
    PropertyListElement propertyListElement = new PropertyListElement("Test1", "Test2", "10", "20", "Owner", 3);

    @Test
    void testGetPropertiesName() {
        assertEquals("Test1", propertyListElement.getPropNumber());
        assertEquals("Test2", propertyListElement.getPropName());
        assertEquals("10", propertyListElement.getPropPrice());
        assertEquals("20", propertyListElement.getPropRent());
        assertEquals("Owner", propertyListElement.getPropOwner());
        assertEquals(3, propertyListElement.getHousesAmount());
    }
}
