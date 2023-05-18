package delta.dkt.logic;

import ClientUIHandling.handlers.languages.LanguageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LanguageHandlerTests {

    /**
     * Checks whether the given arguments are inserted correctly into a template string
     * Note: The amount of placeholders and arguments are the same => match
     */
    @Test
    void checkTemplateInsertion_MatchingPlaceholders() {
        String template = "Currently its %s and the sun is %s very bright!";

        String formation = LanguageHandler.formatText(template, new String[]{"Monday", "shining"});
        Assertions.assertEquals(String.format(template, "Monday", "shining"), formation);
    }
}
