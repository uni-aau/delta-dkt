package handlers;

import ServerLogic.handlers.ParameterHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParameterHandlerTests {

    private Object[] array;

    @BeforeEach
    void setup(){
        array = new Object[]{0, 1, "0", "1", true, false, "true", "false", 10, 10.0, 10f, "string", 10L, "10L", null};
    }

    /**
     * Checks whether the values of the dummy array are handled correctly.
     */
    @Test
    void checkIntegers(){
        Assertions.assertTrue(ParameterHandler.hasValue(array, 0, Integer.class)); // 0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 1, Integer.class)); // 1
        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, Integer.class));; // "0"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, Integer.class));; //"1"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 8, Integer.class)); // 10
        Assertions.assertTrue(ParameterHandler.hasValue(array, 12, Integer.class)); // 10L

        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, Integer.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, Integer.class));; // false
        Assertions.assertFalse(ParameterHandler.hasValue(array, 6, Integer.class));; // "true"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 7, Integer.class));; // "false"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 9, Integer.class));; // "10.0"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 10, Integer.class));; // 10f
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Integer.class));; // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 13, Integer.class));; // "10L"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 14, Integer.class));; // null
        Assertions.assertFalse(ParameterHandler.hasValue(array, array.length, Integer.class));; // index-out-of-bounce
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
        Assertions.assertTrue(ParameterHandler.hasValue(array, 8, Double.class)); // 10
        Assertions.assertTrue(ParameterHandler.hasValue(array, 9, Double.class)); // 10.0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 10, Double.class)); // 10f
        Assertions.assertTrue(ParameterHandler.hasValue(array, 12, Double.class)); // 10L

        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, Double.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, Double.class)); // false
        Assertions.assertFalse(ParameterHandler.hasValue(array, 6, Double.class)); // "true"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 7, Double.class)); // "false"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Double.class)); // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 13, Double.class)); // "10L"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 14, Double.class)); // null
        Assertions.assertFalse(ParameterHandler.hasValue(array, array.length, Double.class)); // index-out-of-bounce
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
        Assertions.assertTrue(ParameterHandler.hasValue(array, 8, Float.class)); // 10
        Assertions.assertTrue(ParameterHandler.hasValue(array, 9, Float.class)); // 10.0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 10, Float.class)); // 10f
        Assertions.assertTrue(ParameterHandler.hasValue(array, 12, Float.class)); // 10L

        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, Float.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, Float.class)); // false
        Assertions.assertFalse(ParameterHandler.hasValue(array, 6, Float.class)); // "true"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 7, Float.class)); // "false"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Float.class)); // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 13, Float.class)); // "10L
        Assertions.assertFalse(ParameterHandler.hasValue(array, 14, Float.class)); // null
        Assertions.assertFalse(ParameterHandler.hasValue(array, array.length, Float.class)); // index-out-of-bounce
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
        Assertions.assertFalse(ParameterHandler.hasValue(array, 12, Boolean.class)); // 10L
        Assertions.assertFalse(ParameterHandler.hasValue(array, 13, Boolean.class)); // "10L"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 14, Boolean.class)); // null
        Assertions.assertFalse(ParameterHandler.hasValue(array, array.length, Boolean.class)); // index-out-of-bounce
    }

    /**
     * Checks whether the values of the dummy array are handled correclty.
     */
    @Test
    void checkStrings(){
        Assertions.assertFalse(ParameterHandler.hasValue(array, 0, String.class)); // 0
        Assertions.assertFalse(ParameterHandler.hasValue(array, 1, String.class)); // 1
        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, String.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, String.class)); // false
        Assertions.assertFalse(ParameterHandler.hasValue(array, 8, String.class)); // 10
        Assertions.assertFalse(ParameterHandler.hasValue(array, 9, String.class)); // 10.0
        Assertions.assertFalse(ParameterHandler.hasValue(array, 10, String.class)); // 10f
        Assertions.assertFalse(ParameterHandler.hasValue(array, 12, String.class)); // 10L
        Assertions.assertFalse(ParameterHandler.hasValue(array, 14, String.class)); // null
        Assertions.assertFalse(ParameterHandler.hasValue(array, array.length, String.class)); // index-out-of-bounce

        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, String.class)); // "0"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, String.class)); // "1"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 6, String.class)); // "true"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 7, String.class)); // "false"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 11, String.class)); // string
        Assertions.assertTrue(ParameterHandler.hasValue(array, 13, String.class)); // "10L"
    }

    /**
     * Checks whether the values of the dummy array are handled correclty.
     */
    @Test
    void checkLongs(){
        Assertions.assertTrue(ParameterHandler.hasValue(array, 0, Long.class)); // 0
        Assertions.assertTrue(ParameterHandler.hasValue(array, 1, Long.class)); // 1
        Assertions.assertTrue(ParameterHandler.hasValue(array, 2, Long.class)); // "0"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 3, Long.class)); // "1"
        Assertions.assertTrue(ParameterHandler.hasValue(array, 8, Long.class)); // 10
        Assertions.assertTrue(ParameterHandler.hasValue(array, 12, Long.class)); // 10L


        Assertions.assertFalse(ParameterHandler.hasValue(array, 4, Long.class)); // true
        Assertions.assertFalse(ParameterHandler.hasValue(array, 5, Long.class)); // false
        Assertions.assertFalse(ParameterHandler.hasValue(array, 6, Long.class)); // "true"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 7, Long.class)); // "false"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 9, Long.class)); // 10.0
        Assertions.assertFalse(ParameterHandler.hasValue(array, 10, Long.class)); // 10f
        Assertions.assertFalse(ParameterHandler.hasValue(array, 11, Long.class)); // string
        Assertions.assertFalse(ParameterHandler.hasValue(array, 13, Long.class)); // "10L"
        Assertions.assertFalse(ParameterHandler.hasValue(array, 14, Long.class)); // null
        Assertions.assertFalse(ParameterHandler.hasValue(array, array.length, Long.class)); // index-out-of-bounce
    }

    //? getValue function

    /**
     * Checks whether the values of the dummy array are parsed correctly.
     */
    @Test
    void check_GetValue_Integers(){
        Assertions.assertEquals(0, ParameterHandler.getValue(array, 0, Integer.class)); // 0
        Assertions.assertEquals(1, ParameterHandler.getValue(array, 1, Integer.class)); // 1
        Assertions.assertEquals(0, ParameterHandler.getValue(array, 2, Integer.class)); // "0"
        Assertions.assertEquals(1, ParameterHandler.getValue(array, 3, Integer.class)); // "1"
        Assertions.assertEquals(10, ParameterHandler.getValue(array, 8, Integer.class)); // 10
        Assertions.assertEquals(10, ParameterHandler.getValue(array, 12, Integer.class)); // 10L

        Assertions.assertNull(ParameterHandler.getValue(array, 9, Integer.class)); // 10.0
        Assertions.assertNull(ParameterHandler.getValue(array, 10, Integer.class)); // 10f
        Assertions.assertNull(ParameterHandler.getValue(array, 4, Integer.class)); // true
        Assertions.assertNull(ParameterHandler.getValue(array, 5, Integer.class)); // false
        Assertions.assertNull(ParameterHandler.getValue(array, 6, Integer.class)); // "true"
        Assertions.assertNull(ParameterHandler.getValue(array, 7, Integer.class)); // "false"
        Assertions.assertNull(ParameterHandler.getValue(array, 11, Integer.class)); // string
        Assertions.assertNull(ParameterHandler.getValue(array, 13, Integer.class)); // "10L"
        Assertions.assertNull(ParameterHandler.getValue(array, 14, Integer.class)); // null
        Assertions.assertNull(ParameterHandler.getValue(array, array.length, Integer.class)); // index-out-of-bounce
    }


    /**
     * Checks whether the values of the dummy array are parsed correctly.
     */
    @Test
    void check_GetValue_Doubles(){
        Assertions.assertEquals(0.0, ParameterHandler.getValue(array, 0, Double.class)); // 0
        Assertions.assertEquals(1.0, ParameterHandler.getValue(array, 1, Double.class)); // 1
        Assertions.assertEquals(0.0, ParameterHandler.getValue(array, 2, Double.class)); // "0"
        Assertions.assertEquals(1.0, ParameterHandler.getValue(array, 3, Double.class)); // "1"
        Assertions.assertEquals(10.0, ParameterHandler.getValue(array, 8, Double.class)); // 10
        Assertions.assertEquals(10.0, ParameterHandler.getValue(array, 9, Double.class)); // 10.0
        Assertions.assertEquals(10.0, ParameterHandler.getValue(array, 10, Double.class)); // 10f
        Assertions.assertEquals(10.0, ParameterHandler.getValue(array, 12, Double.class)); // 10L

        Assertions.assertNull(ParameterHandler.getValue(array, array.length, Double.class)); // index-out-of-bounce
        Assertions.assertNull(ParameterHandler.getValue(array, 4, Double.class)); // true
        Assertions.assertNull(ParameterHandler.getValue(array, 5, Double.class)); // false
        Assertions.assertNull(ParameterHandler.getValue(array, 6, Double.class)); // "true"
        Assertions.assertNull(ParameterHandler.getValue(array, 7, Double.class)); // "false"
        Assertions.assertNull(ParameterHandler.getValue(array, 11, Double.class)); // string
        Assertions.assertNull(ParameterHandler.getValue(array, 13, Double.class)); // "10L"
        Assertions.assertNull(ParameterHandler.getValue(array, 14, Double.class)); // null
    }

    /**
     * Checks whether the values of the dummy array are parsed correctly.
     */
    @Test
    void check_GetValue_Float(){
        Assertions.assertEquals(0f, ParameterHandler.getValue(array, 0, Float.class)); // 0
        Assertions.assertEquals(1f, ParameterHandler.getValue(array, 1, Float.class)); // 1
        Assertions.assertEquals(0f, ParameterHandler.getValue(array, 2, Float.class)); // "0"
        Assertions.assertEquals(1f, ParameterHandler.getValue(array, 3, Float.class)); // "1"
        Assertions.assertEquals(10f, ParameterHandler.getValue(array, 8, Float.class)); // 10
        Assertions.assertEquals(10f, ParameterHandler.getValue(array, 9, Float.class)); // 10.0
        Assertions.assertEquals(10f, ParameterHandler.getValue(array, 10, Float.class)); // 10f
        Assertions.assertEquals(10f, ParameterHandler.getValue(array, 12, Float.class)); // 10L

        Assertions.assertNull(ParameterHandler.getValue(array, array.length, Float.class)); // index-out-of-bounce
        Assertions.assertNull(ParameterHandler.getValue(array, 4, Float.class)); // true
        Assertions.assertNull(ParameterHandler.getValue(array, 5, Float.class)); // false
        Assertions.assertNull(ParameterHandler.getValue(array, 6, Float.class)); // "true"
        Assertions.assertNull(ParameterHandler.getValue(array, 7, Float.class)); // "false"
        Assertions.assertNull(ParameterHandler.getValue(array, 11, Float.class)); // string
        Assertions.assertNull(ParameterHandler.getValue(array, 13, Float.class)); // "10L"
        Assertions.assertNull(ParameterHandler.getValue(array, 14, Float.class)); // null
    }

    /**
     * Checks whether the values of the dummy array are parsed correctly.
     */
    @Test
    void check_GetValue_String(){
        Assertions.assertEquals("0", ParameterHandler.getValue(array, 2, String.class)); // "0"
        Assertions.assertEquals("1", ParameterHandler.getValue(array, 3, String.class)); // "1"
        Assertions.assertEquals("true", ParameterHandler.getValue(array, 6, String.class)); // "true"
        Assertions.assertEquals("false", ParameterHandler.getValue(array, 7, String.class)); // "false"
        Assertions.assertEquals("string", ParameterHandler.getValue(array, 11, String.class)); // string
        Assertions.assertEquals("10L", ParameterHandler.getValue(array, 13, String.class)); // "10L"

        Assertions.assertNull(ParameterHandler.getValue(array, 0, String.class)); // 0
        Assertions.assertNull(ParameterHandler.getValue(array, 1, String.class)); // 1
        Assertions.assertNull(ParameterHandler.getValue(array, 4, String.class)); // true
        Assertions.assertNull(ParameterHandler.getValue(array, 5, String.class)); // false
        Assertions.assertNull(ParameterHandler.getValue(array, 8, String.class)); // 10
        Assertions.assertNull(ParameterHandler.getValue(array, 9, String.class)); // 10.0
        Assertions.assertNull(ParameterHandler.getValue(array, 10, String.class)); // 10f
        Assertions.assertNull(ParameterHandler.getValue(array, 12, String.class)); // 10L
        Assertions.assertNull(ParameterHandler.getValue(array, 14, String.class)); // null
        Assertions.assertNull(ParameterHandler.getValue(array, array.length, String.class)); // index-out-of-bounce

    }

    /**
     * Checks whether the values of the dummy array are parsed correctly.
     */
    @Test
    void check_GetValue_Boolean(){
        Assertions.assertFalse(ParameterHandler.getValue(array, 2, Boolean.class)); // "0"
        Assertions.assertTrue(ParameterHandler.getValue(array, 3, Boolean.class)); // "1"
        Assertions.assertTrue(ParameterHandler.getValue(array, 6, Boolean.class)); // "true"
        Assertions.assertFalse(ParameterHandler.getValue(array, 7, Boolean.class)); // "false"
        Assertions.assertNull(ParameterHandler.getValue(array, 11, Boolean.class)); // string
        Assertions.assertNull(ParameterHandler.getValue(array, 13, Boolean.class)); // "10L"

        Assertions.assertFalse(ParameterHandler.getValue(array, 0, Boolean.class)); // 0
        Assertions.assertTrue(ParameterHandler.getValue(array, 1, Boolean.class)); // 1
        Assertions.assertTrue(ParameterHandler.getValue(array, 4, Boolean.class)); // true
        Assertions.assertFalse(ParameterHandler.getValue(array, 5, Boolean.class)); // false
        Assertions.assertNull(ParameterHandler.getValue(array, 8, Boolean.class)); // 10
        Assertions.assertNull(ParameterHandler.getValue(array, 9, Boolean.class)); // 10.0
        Assertions.assertNull(ParameterHandler.getValue(array, 10, Boolean.class)); // 10f
        Assertions.assertNull(ParameterHandler.getValue(array, 12, Boolean.class)); // 10L
        Assertions.assertNull(ParameterHandler.getValue(array, 14, Boolean.class)); // null
        Assertions.assertNull(ParameterHandler.getValue(array, array.length, Boolean.class)); // index-out-of-bounce

    }


    /**
     * Checks whether the values of the dummy array are parsed correctly.
     */
    @Test
    void check_GetValue_Long(){
        Assertions.assertEquals(0L, ParameterHandler.getValue(array, 0, Long.class)); // 0
        Assertions.assertEquals(1L, ParameterHandler.getValue(array, 1, Long.class)); // 1
        Assertions.assertEquals(0L, ParameterHandler.getValue(array, 2, Long.class)); // "0"
        Assertions.assertEquals(1L, ParameterHandler.getValue(array, 3, Long.class)); // "1"
        Assertions.assertEquals(10L, ParameterHandler.getValue(array, 8, Long.class)); // 10
        Assertions.assertEquals(10L, ParameterHandler.getValue(array, 12, Long.class)); // 10L

        Assertions.assertNull(ParameterHandler.getValue(array, 4, Long.class)); // true
        Assertions.assertNull(ParameterHandler.getValue(array, 5, Long.class)); // false
        Assertions.assertNull(ParameterHandler.getValue(array, 6, Long.class)); // "true"
        Assertions.assertNull(ParameterHandler.getValue(array, 7, Long.class)); // "false"
        Assertions.assertNull(ParameterHandler.getValue(array, 9, Long.class)); // 10.0
        Assertions.assertNull(ParameterHandler.getValue(array, 10, Long.class)); // 10f
        Assertions.assertNull(ParameterHandler.getValue(array, 11, Long.class)); // string
        Assertions.assertNull(ParameterHandler.getValue(array, 13, Long.class)); // "10L"
        Assertions.assertNull(ParameterHandler.getValue(array, 14, Long.class)); // null
        Assertions.assertNull(ParameterHandler.getValue(array, array.length, Long.class)); // index-out-of-bounce
    }
}
