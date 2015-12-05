
/**
 * ***********************************************************************
 * Name: Email:
 *
 * Compilation: javac Point.java Execution: Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 ************************************************************************
 */
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrderComparator();
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        // special cases
        if (compareTo(that) == 0) {
            // degenerate segment
            return Double.NEGATIVE_INFINITY;
        } else if (this.x == that.x) {
            // vertical segment
            return Double.POSITIVE_INFINITY;
        } else if (this.y == that.y) {
            // horizontal segment
            return 0.0;
        }

        return (((double) that.y - this.y) / (that.x - this.x));
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    @Override
    public int compareTo(Point that) {
        if (this.y != that.y) {
            return (this.y - that.y);
        } else {
            return (this.x - that.x);
        }
    }

    // return string representation of this point
    @Override
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }


    private class SlopeOrderComparator implements Comparator<Point> {
        @Override
        public int compare(Point t1, Point t2) {
            if(slopeTo(t1) > slopeTo(t2)) {
                return 1;
            } else if (slopeTo(t1) < slopeTo(t2)) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    
    
}