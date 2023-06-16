package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import delta.dkt.logic.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class TaskHandlerTests {

    private GameMap mockMap = mock(GameMap.class);

    @BeforeEach
    void setup(){
        Game.setMap(mockMap);
    }

    /**
     * Check if the getTask methode returns not null if it gets a location where there is a task field.
     */
    @ParameterizedTest
    @ValueSource(ints = {3, 9, 23, 28, 38})
    void testGetTaskExistingTask(int input){
        when(mockMap.getField(3)).thenReturn(new RiskTaskField(3));
        when(mockMap.getField(23)).thenReturn(new RiskTaskField(23));
        when(mockMap.getField(38)).thenReturn(new RiskTaskField(38));

        when(mockMap.getField(9)).thenReturn(new BankTaskField(9));
        when(mockMap.getField(28)).thenReturn(new BankTaskField(28));

        assertNotNull(TaskHandler.getTask(input));

        verify(mockMap, atLeastOnce()).getField(input);
    }

    /**
     * Check if the getProperties methode returns null if it gets a location where there is no property.
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 8, 22, 30, 37, 39, 50})
    void testGetTaskNotExistingTask(int input){
        assertNull(TaskHandler.getTask(input));
    }
}
