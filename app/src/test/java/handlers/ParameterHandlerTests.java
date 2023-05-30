package handlers;

import ServerLogic.handlers.ParameterHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParameterHandlerTests {

    private Object[] array;

    @BeforeEach
    void setup(){
        array = new Object[]{0, 1, "0", "1", true, false, "true", "false", 10, 10.0, 10f, "string"};
    }

    /**
     * Checks whether the values of the dummy array are handled correctly.
     */
    @Test
    void checkIntegers(){
        Assertions.assertTrue(ParameterHandler.hasValue(array, 0, Integer.class));
        Assertions.assertTrue(ParameterHandler.hasValue(array, 1, Integer.class));
        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, Integer.class));;
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, Integer.class));;
        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, Integer.class));;
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, Integer.class));;
        Assertions.assertFalse(ParameterHandler.hasValue(array, 6, Integer.class));;
        Assertions.assertFalse(ParameterHandler.hasValue(array, 7, Integer.class));;
        Assertions.assertTrue(ParameterHandler.hasValue(array, 8, Integer.class));
        Assertions.assertFalse(ParameterHandler.hasValue(array, 9, Integer.class));;
        Assertions.assertFalse(ParameterHandler.hasValue(array, 10, Integer.class));;
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Integer.class));;
    }

    /**
     * Checks whether the values of the dummy array are handled correclty.
     */
    @Test
    void checkDoubles(){
        Assertions.assertTrue(ParameterHandler.hasValue(array, 0, Double.class)); // 0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 1, Double.class)); // 1
        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, Double.class)); // "0"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, Double.class)); // "1"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, Double.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, Double.class)); // false
        Assertions.assertFalse(ParameterHandler.hasValue(array, 6, Double.class)); // "true"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 7, Double.class)); // "false"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 8, Double.class)); // 10
        Assertions.assertTrue(ParameterHandler.hasValue(array, 9, Double.class)); // 10.0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 10, Double.class)); // 10f
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Double.class)); // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 12, Double.class)); // nullpointer
    }


}
