package ClientUIHandling.handlers.map_movements;

import android.graphics.PointF;
import android.widget.ImageView;
import org.jetbrains.annotations.NotNull;

/**
 * This class is used to convert relative values into absolute values.
 */
class RelativeRectangle {
    PointF topLeft, topRight, bottomRight, bottomLeft;
    float width, height;

    /**
     * By using this constructor the width and height of a given field, will be set, based on the given locations.
     */
    public RelativeRectangle (@NotNull PointF topLeft, @NotNull PointF topRight, PointF bottomRight, @NotNull PointF bottomLeft) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomRight = bottomRight;
        this.bottomLeft = bottomLeft;

        this.width = topRight.x - topLeft.x;
        this.height = bottomLeft.y - topLeft.y;
    }

    /**
     * By using this constructor, the rectangle will act like a sqaure, thus have the same height and width, based on the given locations.
     * @param start A Point that should be either horizontally or vertially aligned with the end point.
     * @param end A Point that should be either horizontally or vertially aligned with the start point.
     */
    public RelativeRectangle (PointF start, PointF end) {
        width = end.x - start.x;
        height = end.x - start.x;
    }


    public float getWidthFactor () {
        return width;
    }

    public float getHeightFactor () {
        return height;
    }




}


