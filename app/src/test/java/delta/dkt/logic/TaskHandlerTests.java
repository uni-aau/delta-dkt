package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import delta.dkt.logic.structure.TaskHandler;

class TaskHandlerTests {

    /**
     * Check if the getTask methode returns not null if it gets a location where there is a task field.
     */
    @ParameterizedTest
    @ValueSource(ints = {3, 9, 23, 28, 33, 38})
    void testGetTaskExistingTask(int input){
        assertNotNull(TaskHandler.getTask(input));
    }

    /**
     * Check if the getProperties methode returns null if it gets a location where there is no property.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 8, 22, 30, 37, 39, 50})
    void testGetTaskNotExistingTask(int input){
        assertNull(TaskHandler.getTask(input));
    }

    /**
     * Check the location of the task by calling the getLocation methode.
     */
    @Test
    void testGetTaskLocation(){
        assertEquals(33, TaskHandler.getTask(33).getLocation());
    }
}
