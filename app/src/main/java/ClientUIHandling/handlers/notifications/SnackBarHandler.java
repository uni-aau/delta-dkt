package ClientUIHandling.handlers.notifications;

import android.graphics.Color;
import android.view.View;
import com.google.android.material.snackbar.Snackbar;

import static com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT;

/**
 * This handler provides different methods to create Snackbars
 */
public class SnackBarHandler {
    private SnackBarHandler() {
    }

    /**
     * This method will create a Snackbar and return it, so that it can be shown or modified.
     *
     * @param parent The parent view of the Snackbar.
     * @param message The message that the Snackbar should display.
     */
    public static Snackbar createSnackbar(View parent, String message) {
        return SnackBarHandler.createSnackbar(parent, message, LENGTH_SHORT, false);
    }

    /**
     * This method will create a Snackbar and return it, so that it can be shown or modified.
     *
     * @param parent The parent view of the Snackbar.
     * @param message The message that the Snackbar should display.
     * @param duration The amount of time in ms, that the Snackbar should be visible.
     */
    public static Snackbar createSnackbar(View parent, String message, int duration) {
        return SnackBarHandler.createSnackbar(parent, message, duration, false);
    }

    /**
     * This method will create a Snackbar and return it, so that it can be shown or modified.
     *
     * @param parent The parent view of the Snackbar.
     * @param message The message that the Snackbar should display.
     * @param duration The amount of time in ms, that the Snackbar should be visible.
     * @param closeButton Whether the Snackbar should provide a button to close it, before its duration expires.
     */
    public static Snackbar createSnackbar(View parent, String message, int duration, boolean closeButton) {
        return SnackBarHandler.createSnackbar(parent, message, duration, closeButton, null, null);
    }


    /**
     * This method will create a Snackbar and return it, so that it can be shown or modified.
     *
     * @param parent The parent view of the Snackbar.
     * @param message The message that the Snackbar should display.
     * @param duration The amount of time in ms, that the Snackbar should be visible.
     * @param closeButton Whether the Snackbar should provide a button to close it, before its duration expires.
     * @param textColor Sets the Text color of the Snackbar as HEX-String
     */
    public static Snackbar createSnackbar(View parent, String message, int duration, boolean closeButton, String textColor) {
        return SnackBarHandler.createSnackbar(parent, message, duration, closeButton, null, textColor);
    }

    /**
     * This method will create a Snackbar and return it, so that it can be shown or modified.
     * ? Note that, the attributes closeButton, backgroundColor and textColor can be set dynmically. Thus, setting such an attribute null will ignore it.
     *
     * @param parent The parent view of the Snackbar.
     * @param message The message that the Snackbar should display.
     * @param duration The amount of time in ms, that the Snackbar should be visible.
     * @param closeButton Whether the Snackbar should provide a button to close it, before its duration expires.
     * @param backgroundColor Sets the Backgroundcolor of the snackbar as HEX-String.
     * @param textColor Sets the Text color of the Snackbar as HEX-String
     */
    public static Snackbar createSnackbar(View parent, String message, int duration, boolean closeButton, String backgroundColor, String textColor) {
        var snack = Snackbar.make(parent, message, duration);

        if (closeButton) {
            snack.setAction("Close", view -> snack.dismiss());
        }

        if (backgroundColor != null && !backgroundColor.isEmpty())
            snack.setBackgroundTint(Color.parseColor(backgroundColor));
        if (textColor != null && !textColor.isEmpty()) snack.setTextColor(Color.parseColor(textColor));
        return snack;
    }
}
