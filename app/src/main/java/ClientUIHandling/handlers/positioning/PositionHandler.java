package ClientUIHandling.handlers.positioning;

import android.graphics.PointF;
import android.util.Log;
import android.widget.ImageView;

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
    private static boolean logs = false;

    public static void setLogs(boolean state) {
        logs = state;
    }

    private PositionHandler() {
    }

    private static final RelativeRectangle normalField = new RelativeRectangle(
            new PointF(0.21065000f, 0.86278754f), //? Top Left   (standing up right)
            new PointF(0.29321185f, 0.86278754f), //? Top Right  (standing up right)
            new PointF(0.21222293f, 0.9778396f),  //? Bottom Left
            new PointF(0.292346f, 0.9787013f)     //? Bottom Right
    );

    private static final RelativeRectangle specialField = new RelativeRectangle(
            new PointF(0.020407075f, 0.01965946f),  //? Top Left
            new PointF(0.12783727f, 0.01965946f),   //? Top Right
            new PointF(0.019541241f, 0.12706885f),  //? Bottom Left
            new PointF(0.12783727f, 0.12706885f)    //? Bottom Right
    );

    private static final RelativeRectangle mapBorder = new RelativeRectangle(new PointF(0.97948635f, 0.87043023f), new PointF(0.9999334f, 0.87043023f));


    /**
     * This method calculates the absolute position of a corner based on relative values.
     *
     * @param map The ImageView that represents the map, relative values are based on the Width and Height of this map.
     * @param corner The corner for which the location is to be calculated.
     * @return Returns the absolute location of a corner.
     */
    private static PointF getMapCorner(ImageView map, MapPosition corner) {

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
     * ? Note: The desired layout is only achieved when the X and Y values are used accordingly.
     * ? Thus, the first player is always in the bottom left corner of a given field.
     *
     * @param player The player's identifier, can range between 1-6
     * @param figure The visual figure element of the player.
     * @return Returns the X and Y spacing as a PointF.
     */
    private static PointF calculatePlayerSpacing(int player, ImageView figure) {
        PointF spacing = new PointF(0, 0);

        //* The second player in a row
        if (player % 2 == 0) spacing.x += figure.getWidth() + 5;

        //* Players that are not in the base layer (row=1)
        if (Math.ceil(player / 2.0) > 1) {
            var layer = Math.ceil(player / 2.0);
            spacing.y += figure.getHeight() * (layer - 1);
        }

        return spacing;
    }


    /**
     * This method will calculate the location of a players figure on the map based on the destination, the size of figure and the map.
     *
     * @param location The destination on which the figure is to be positioned.
     * @param player The player's identifier, a player's figure has a fixed location inside a given field.
     * @param figure The ImageView that represents the player's figure.
     * @param map The ImageView that presents the map of the game.
     * @return Returns the location for the given player at the requested location.
     */
    public static PointF calculateFigurePosition(int location, int player, ImageView figure, ImageView map) {
        if (player <= 0 || figure == null || map == null) {
            Log.e("Movement", "Invalid parameters, aborting calculation!");
            return new PointF(-100, -100);
        }

        if (PositionHandler.logs) Log.d("Movement-PositionHandler", "Moving to the following field: " + location);

        //? Made with the assumption that the start has the location 0.
        location -= 1;

        //* The corners location on the game's map.
        PointF start = getMapCorner(map, MapPosition.BOTTOM_RIGHT);

        PointF playerSpacing = calculatePlayerSpacing(player, figure);
        PointF position = new PointF(start.x - figure.getWidth() - playerSpacing.x, start.y - figure.getHeight() - playerSpacing.y);


        if (location > 0 && location <= 10) {
            start = getMapCorner(map, MapPosition.BOTTOM_RIGHT);

            position.x = start.x - figure.getWidth() - specialField.getAbsWidth(map) - ((location - 1) * normalField.getAbsWidth(map)) - playerSpacing.x;
            position.y = start.y - figure.getHeight() - playerSpacing.y;
        }
        if (location >= 11 && location <= 20) {
            start = getMapCorner(map, MapPosition.BOTTOM_LEFT);
            location -= 10;

            position.x = start.x + playerSpacing.y;
            position.y = start.y - figure.getHeight() - specialField.getAbsHeight(map) - playerSpacing.x - ((location - 1) * normalField.getAbsWidth(map));
        }
        if (location >= 21 && location <= 30) {
            start = getMapCorner(map, MapPosition.TOP_LEFT);
            location -= 20;

            position.x = start.x + specialField.getAbsWidth(map) + ((location - 1) * normalField.getAbsWidth(map)) + playerSpacing.x;
            position.y = start.y + playerSpacing.y;
        }
        if (location >= 31 && location <= 39) {
            start = getMapCorner(map, MapPosition.TOP_RIGHT);
            location -= 30;

            position.x = start.x - figure.getWidth() - playerSpacing.y;
            position.y = start.y + specialField.getAbsHeight(map) + playerSpacing.x + ((location - 1) * normalField.getAbsWidth(map));
        }

        return position;
    }

}