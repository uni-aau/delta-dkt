package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import delta.dkt.logic.structure.BankTask;
import delta.dkt.logic.structure.BankTaskField;
import delta.dkt.logic.structure.RiskTask;
import delta.dkt.logic.structure.RiskTaskField;

class TasksTest {
    RiskTaskField riskTaskField = new RiskTaskField(1);
    BankTaskField bankTaskField = new BankTaskField(2);

    @Test
    void testRiskTaskFieldNotNull() {
        assertNotNull(riskTaskField.getRiskTask());
    }

    @Test
    void testBankTaskFieldNotNull() {
        assertNotNull(bankTaskField.getBankTask());
    }
}
