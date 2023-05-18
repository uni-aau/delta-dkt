package delta.dkt.logic;

import ClientUIHandling.handlers.languages.LanguageHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LanguageHandlerTests {
    String template = "Currently its %s and the sun is %s very bright!";

    /**
     * Checks whether the given arguments are inserted correctly into a template string
     * Note: The amount of placeholders and arguments are the same => match
     */
    @Test
    void checkTemplateInsertion_MatchingPlaceholders() {
        String formation = LanguageHandler.formatText(template, new String[]{"Monday", "shining"});
        Assertions.assertEquals(String.format(template, "Monday", "shining"), formation);
    }

    /**
     * Check whether the template is formatted correctly when there are not enough placeholders.
     */
    @Test
    void checkTemplateInsertion_MissingPlaceholders() {
        String formation = LanguageHandler.formatText(template, new String[]{"Monday", "shining", "hot"});
        Assertions.assertEquals(String.format(template, "Monday", "shining") + " -> (hot)", formation);
    }

    /**
     * Checks whether the template is formatted correctly when there are to few arguments provided.
     */
    @Test
    void checkTemplateInsertion_MissingArguments() {
        String formation = LanguageHandler.formatText(template, new String[]{"Monday"});
        Assertions.assertEquals(String.format(template, "Monday", "(missing)"), formation);
    }

    /**
     * Checks whether the template is formatted correctly when there are no arguments provided.
     */
    @Test
    void checkTemplateInsertion_NoArguments() {
        String formation = LanguageHandler.formatText(template, new String[]{});
        Assertions.assertEquals(String.format(template, "(missing)", "(missing)"), formation);
    }

    /**
     * Checks whether the template is formatted correclty when there are more is more than placeholder missing
     */
    @Test
    void checkTemplateInsertion_MultipleMissingPlaceholders() {
        String formation = LanguageHandler.formatText(template, new String[]{"Monday", "shining", "hot", "sand", "italy"});
        Assertions.assertEquals(String.format(template, "Monday", "shining") + " -> (hot|sand|italy)", formation);
    }

    /**
     * Checks whether the arguments are attached to the end of the formation, when there are no placeholders at all.
     */
    @Test
    void checkTemplateInsertion_NoPlacerholders() {
        template = "I am missing placeholders.";
        String formation = LanguageHandler.formatText(template, new String[]{"Monday", "shining", "hot", "sand", "italy"});
        Assertions.assertEquals(template + " -> (Monday|shining|hot|sand|italy)", formation);
    }
}
