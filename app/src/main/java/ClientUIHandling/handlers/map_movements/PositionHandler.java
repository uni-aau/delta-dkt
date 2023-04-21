package ClientUIHandling.handlers.map_movements;

import android.graphics.PointF;
import android.util.Log;
import android.widget.ImageView;
import org.jetbrains.annotations.NotNull;

//? A map has fields
    //? Most of them are normal fields with a static width and height
    //? Some have a special size, thus they are bigger, those fields are at the corner of the map

//? Move the figure on the map based on its location.
    //? The Map has 39 fields, where 10 are always either horizontally or vertically aligned.
    //? The calculation depends on where the location is at (Top or Bottom Row), thus either from
        //? Moving from Right -> Left
        //? Or Moving from Left -> Right

    //? Additionally, the position inside the field depends on the player's figure.
    //? Each figure has a specific position inside a field.




public class PositionHandler {
    private static RelativeRectangle normalField = new RelativeRectangle(
            new PointF(0.21065000f, 0.86278754f), //? Top Left   (standing up right)
            new PointF(0.29321185f, 0.86278754f), //? Top Right  (standing up right)
            new PointF(0.21222293f, 0.9778396f),  //? Bottom Left
            new PointF(0.292346f, 0.9787013f)     //? Bottom Right
    );

    private static RelativeRectangle specialField = new RelativeRectangle(
            new PointF(0.020407075f, 0.01965946f),  //? Top Left
            new PointF(0.12783727f, 0.01965946f),   //? Top Right
            new PointF(0.019541241f, 0.12706885f),  //? Bottom Left
            new PointF(0.12783727f, 0.12706885f)    //? Bottom Right
    );

    private static RelativeRectangle mapBorder = new RelativeRectangle(new PointF(0.97948635f, 0.87043023f), new PointF(0.9999334f, 0.87043023f));

}