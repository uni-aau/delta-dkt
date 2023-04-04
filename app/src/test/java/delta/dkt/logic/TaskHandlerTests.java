package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import delta.dkt.logic.structure.TaskHandler;

public class TaskHandlerTests {

    @ParameterizedTest
    @ValueSource(ints = {3, 9, 23, 28, 33, 38})
    void testGetTaskExistingTask(int input){
        assertNotNull(TaskHandler.getTask(input));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 8, 22, 30, 37, 39, 50})
    void testGetTaskNotExistingTask(int input){
        assertNull(TaskHandler.getTask(input));
    }

    @Test
    void testGetTaskLocation(){
        assertEquals(33, TaskHandler.getTask(33).getLocation());
    }
}
