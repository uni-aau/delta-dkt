package ClientUIHandling.handlers.languages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;

public class LanguageHandler {
    private LanguageHandler(){}
    

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

        int requestedArgs = 0;
        if(languageFormat.contains("%s")) requestedArgs = languageFormat.split("%s").length - 1;

        Object[] acceptedArgs = new String[requestedArgs];
        Arrays.fill(acceptedArgs, "(missing)");

        //* Fill requested args if provided.
        if(args.length != 0){
            for(int i = 0; i < acceptedArgs.length; i++){
                if(args.length > i) acceptedArgs[i] = args[i];
                else acceptedArgs[i] = "missing";
            }
        }


        activity.runOnUiThread(() -> textElement.setText(String.format(languageFormat, acceptedArgs)));
    }
}
