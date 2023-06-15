package delta.dkt.logic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import delta.dkt.logic.structure.BankTask;
import delta.dkt.logic.structure.BankTaskField;
import delta.dkt.logic.structure.RiskTask;
import delta.dkt.logic.structure.RiskTaskField;

class TasksTest {
    RiskTaskField riskTaskField = new RiskTaskField(1, new RiskTask(1, "Risktask", "Description"));
    BankTaskField bankTaskField = new BankTaskField(2, new BankTask(2, "BankTask", "Description"));

    @Test
    void testRiskTaskFieldNotNull() {
        assertNotNull(riskTaskField.getRiskTask());
    }

    @Test
    void testBankTaskFieldNotNull() {
        assertNotNull(bankTaskField.getBankTask());
    }
}
