package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import delta.dkt.logic.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TasksTest {
    RiskTaskField riskTaskField = new RiskTaskField(3);
    BankTaskField bankTaskField = new BankTaskField(9);

    private GameMap mockMap = mock(GameMap.class);

    @BeforeEach
    void setup(){
        Game.setMap(mockMap);
    }

    @Test
    void testRiskTaskFieldNotNull() {
        when(mockMap.getField(3)).thenReturn(new RiskTaskField(3));
        assertNotNull(riskTaskField.getRiskTask());
        verify(mockMap, atLeastOnce()).getField(3);
    }

    @Test
    void testBankTaskFieldNotNull() {
        when(mockMap.getField(9)).thenReturn(new BankTaskField(9));
        assertNotNull(bankTaskField.getBankTask());
        verify(mockMap, atLeastOnce()).getField(9);
    }
}
