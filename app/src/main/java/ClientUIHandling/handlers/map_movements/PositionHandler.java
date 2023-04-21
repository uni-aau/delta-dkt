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


    /**
     * This method calculates the absolute position of a corner based on relative values.
     * @param map The ImageView that represents the map, relative values are based on the Width and Height of this map.
     * @param corner The corner for which the location is to be calculated.
     * @return Returns the absolute location of a corner.
     */
    private static PointF getMapCorner (ImageView map, MapPosition corner) {

        //? top left
        float x = map.getX() + (mapBorder.getWidthFactor() * map.getWidth());
        float y = map.getY() + (mapBorder.getHeightFactor() * map.getHeight());

        if (corner == MapPosition.TOP_RIGHT) {
            x += map.getWidth() - 2 * (map.getWidth() * mapBorder.getWidthFactor());
        }

        if (corner == MapPosition.BOTTOM_LEFT) {
            y += map.getHeight() - 2 * (map.getHeight() * mapBorder.getHeightFactor());
        }

        if (corner == MapPosition.BOTTOM_RIGHT) {
            x = map.getX() + map.getWidth() - mapBorder.getAbsWidth(map);
            y = map.getY() + map.getHeight() - mapBorder.getAbsHeight(map);
        }

        return new PointF(x, y);
    }


    /**
     * This method calculates the spacing between player figures.
     * The chosen layout is 3 rows and 2 columns.
     *? Note: The desired layout is only achieved when the X and Y values are used accordingly.
     *? Thus, the first player is always in the bottom left corner of a given field.
     * @param player The player's identifier, can range between 1-6
     * @param figure The visual figure element of the player.
     * @return Returns the X and Y spacing as a PointF.
     */
    private static PointF calculatePlayerSpacing (int player, ImageView figure) {
        PointF spacing = new PointF(0, 0);

        //* The second player in a row
        if (player % 2 == 0) spacing.x += figure.getWidth() +5;

        //* Players that are not in the base layer (row=1)
        if (Math.ceil(player / 2.0) > 1) {
            var layer = Math.ceil(player/2.0);
            spacing.y += figure.getHeight() * (layer-1);
        }

        return spacing;
    }
}