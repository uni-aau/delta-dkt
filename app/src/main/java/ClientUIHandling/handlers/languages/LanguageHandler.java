package ClientUIHandling.handlers.languages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class LanguageHandler {
    private LanguageHandler() {
    }

    /**
     * This method inserts the given arguments into a given template string.
     * If there are to few placeholders, the remaining arguments are attached to the end of the formation.
     * If there are to many placeholders, the remaining placeholders are filled with '(missing)'.
     *
     * @param template The template string that should contain placeholders ('%s')
     * @param args The arguments that should be inserted into the template
     * @return The formatted string, based on the given template and arguments
     */
    public static String formatText(String template, Object[] args) {
        int requestedArgs = 0;
        if (template.contains("%s")) requestedArgs = template.split("%s").length - 1;

        Object[] acceptedArgs = new String[requestedArgs];
        Arrays.fill(acceptedArgs, "(missing)");

        //* Fill requested args if provided.
        if (args.length != 0) {
            for (int i = 0; i < acceptedArgs.length; i++) {
                if (args.length > i) acceptedArgs[i] = args[i].toString();
                else acceptedArgs[i] = "(missing)";
            }
        }

        //? Add arguments to the format, that exceed its placeholder count.
        StringBuilder exceeded = new StringBuilder();
        for (int i = acceptedArgs.length; i < args.length; i++) {
            exceeded.append(args[i]);
            if (i != args.length - 1) exceeded.append("|");
        }

        return String.format(template, acceptedArgs) + String.format(" -> (%s)", exceeded);
    }

    //START-NOSCAN

    /**
     * This method sets the text of an UI element based on the current app-language
     * It uses the previsouly language-template-string and inserts the given arguments into the placeholders.
     * In case there are to few placeholders, the remaining arguments will be added to the end of the string.
     * In case there are to few arguments, the template strings placeholders will be filled with '(missing)', indicating it.
     *
     * @param activity The activitity in which the text element is to be set
     * @param fieldName The field of which the text is to be updated
     * @param strings The xml string name, that is to be used as a template
     * @param args The arguments that should be inserted into the template
     */
    @SuppressLint("DiscouragedApi")
    public static void updateTextElement(Activity activity, String fieldName, String strings, String[] args) {
        int textElementIdentifier = activity.getResources().getIdentifier(fieldName, "id", activity.getPackageName());
        TextView textElement = activity.findViewById(textElementIdentifier);

        if (textElement == null) {
            Log.w("Game-Translation", String.format("Translation failed, as the view-element (%s) was not found!", fieldName));
            return;
        }

        //? get the template string
        int stringsXMLIdentifier = activity.getResources().getIdentifier(strings, "string", activity.getPackageName());
        if (stringsXMLIdentifier == 0) {
            Log.w("Game-Translation", String.format("Translation failed, as the templateString (%s) was not found!", strings));
            return;
        }
        String languageFormat = activity.getResources().getString(stringsXMLIdentifier);

        textElement.setText(LanguageHandler.formatText(languageFormat, args));

        Log.d("Game-Translation", "Successfully updated UI elements text using a language template!");
    }

    //END-NOSCAN
}
