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

    /**
     * Checks whether the values of the dummy array are handled correclty.
     */
    @Test
    void checkFloats(){
        Assertions.assertTrue(ParameterHandler.hasValue(array, 0, Float.class)); // 0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 1, Float.class)); // 1
        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, Float.class)); // "0"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, Float.class)); // "1"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, Float.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, Float.class)); // false
        Assertions.assertFalse(ParameterHandler.hasValue(array, 6, Float.class)); // "true"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 7, Float.class)); // "false"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 8, Float.class)); // 10
        Assertions.assertTrue(ParameterHandler.hasValue(array, 9, Float.class)); // 10.0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 10, Float.class)); // 10f
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Float.class)); // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 12, Float.class)); // nullpointer
    }


    /**
     * Checks whether the values of the dummy array are handled correclty.
     */
    @Test
    void checkBooleans(){
        Assertions.assertTrue(ParameterHandler.hasValue(array, 0, Boolean.class)); // 0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 1, Boolean.class)); // 1
        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, Boolean.class)); // "0"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, Boolean.class)); // "1"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 4, Boolean.class)); // true
        Assertions.assertTrue(ParameterHandler.hasValue(array, 5, Boolean.class)); // false
        Assertions.assertTrue(ParameterHandler.hasValue(array, 6, Boolean.class)); // "true"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 7, Boolean.class)); // "false"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 8, Boolean.class)); // 10
        Assertions.assertFalse(ParameterHandler.hasValue(array, 9, Boolean.class)); // 10.0
        Assertions.assertFalse(ParameterHandler.hasValue(array, 10, Boolean.class)); // 10f
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Boolean.class)); // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 12, Boolean.class)); // nullpointer
    }

    /**
     * Checks whether the values of the dummy array are handled correclty.
     */
    @Test
    void checkStrings(){
        Assertions.assertFalse(ParameterHandler.hasValue(array, 0, String.class)); // 0
        Assertions.assertFalse(ParameterHandler.hasValue(array, 1, String.class)); // 1
        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, String.class)); // "0"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, String.class)); // "1"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, String.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, String.class)); // false
        Assertions.assertTrue(ParameterHandler.hasValue(array, 6, String.class)); // "true"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 7, String.class)); // "false"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 8, String.class)); // 10
        Assertions.assertFalse(ParameterHandler.hasValue(array, 9, String.class)); // 10.0
        Assertions.assertFalse(ParameterHandler.hasValue(array, 10, String.class)); // 10f
        Assertions.assertTrue(ParameterHandler.hasValue(array, 11, String.class)); // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 12, String.class)); // nullpointer
    }
}
