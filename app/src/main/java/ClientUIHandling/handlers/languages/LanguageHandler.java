package ClientUIHandling.handlers.languages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class LanguageHandler {
    private LanguageHandler(){}

    public static String formatText(String template, Object[] args){
        int requestedArgs = 0;
        if(template.contains("%s")) requestedArgs = template.split("%s").length - 1;

        Object[] acceptedArgs = new String[requestedArgs];
        Arrays.fill(acceptedArgs, "(missing)");

        //* Fill requested args if provided.
        if(args.length != 0){
            for(int i = 0; i < acceptedArgs.length; i++){
                if(args.length > i) acceptedArgs[i] = args[i];
                else acceptedArgs[i] = "(missing)";
            }
        }

        //? Add arguments to the format, that exceed its placeholder count.
        StringBuilder exceeded = new StringBuilder();
        for(int i = acceptedArgs.length; i < args.length; i++){
            exceeded.append(args[i]);
            if(i != args.length -1) exceeded.append("|");
        }

        return String.format(template, acceptedArgs) + String.format("(%s)", exceeded);
    }
    
    @SuppressLint("DiscouragedApi")
    public static void updateTextElement(Activity activity, String fieldName, String strings, String[] args){
        int textElementIdentifier = activity.getResources().getIdentifier(fieldName, "id", activity.getPackageName());
        TextView textElement = activity.findViewById(textElementIdentifier);

        if(textElement == null) {
            Log.w("Game-Translation", String.format("Translation failed, as the view-element (%s) was not found!", fieldName));
            return;
        }

        //? get the template string
        int stringsXMLIdentifier = activity.getResources().getIdentifier(strings, "strings", activity.getPackageName());
        String languageFormat = activity.getResources().getString(stringsXMLIdentifier);

        activity.runOnUiThread(() -> textElement.setText(LanguageHandler.formatText(languageFormat, args)));
    }
}
