package ServerLogic.handlers;

public class ParameterHandler {

    /**
     * This checks whether a given array has a value at the given index and whether the value is of the given type.
     * <p>
     * <p>
     * <p>
     * * If the object on the index 1 is of type String but the requested type is Integer,
     * * Double or Float, it will attempt to parse it, if parsing it works then it will
     * * return true, otherwise false.
     *
     * @param array The array that is to be checked for the type specific value
     * @param index The index of the value that is to be checked
     * @param indexType The type the value should have.
     * @return Returns true or false depending on the fact
     */
    public static <T> boolean hasValue(Object[] array, int index, Class<T> indexType) {
        if (array.length == 0) return false;
        if (array.length - 1 < index) return false;

        //? Attempt to parse the value to the requested type => if successfull return true
        if (indexType == Integer.class) {
            try {
                Integer.parseInt(array[index].toString());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        if (indexType == Double.class) {
            try {
                Double.parseDouble(array[index].toString());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        if (indexType == Float.class) {
            try {
                Float.parseFloat(array[index].toString());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        if (indexType == Boolean.class) {
            //* value could be 0 or 1, "0" or "1", true or false, "true" or "false"
            try {
                if (array[index].toString().trim().length() == 1) {
                    //? "0" or "1"  &&  0 or 1
                    return Boolean.valueOf((array[index].toString().trim().equalsIgnoreCase("1") || array[index].toString().trim().equalsIgnoreCase("0")));
                }
                return array[index].toString().trim().equalsIgnoreCase("true");
            } catch (Exception e) { //* Prevent possible NullPointerException
                return false;
            }
        }

        try {
            if (array[index].getClass() == indexType) return true;
        } catch (Exception e) {
            return false;
        }

        return false;
    }

}
