
import java.util.Arrays;

public class Fast {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong number of arguments; "
                    + "usage = Fast input_file");
        }

        String fileName = args[0];
        Point[] points = readPointsFromFile(fileName);
        plotPoints(points);

        if(points.length < 4) {
            return;
        }
        
        LinearProbingHashST<Integer, Point[]> segTable =
                new LinearProbingHashST<Integer, Point[]>();

        for (int i = 0; i < points.length; i++) {
            Point currPoint = points[i];

            Point[] pointsCopy = new Point[points.length - 1];
            int index = 0;

            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    pointsCopy[index++] = points[j];
                }
            }

            // sort the points according to slope to point i
            Arrays.sort(pointsCopy, currPoint.SLOPE_ORDER);

            // When finding a group of 3 or more points with the same value for slope
            // save them
            double slopeVal = currPoint.slopeTo(pointsCopy[0]);
            int index2 = 1, eqSlopePoints = 1;
            boolean finished = false;

            while (!finished) {
                if ((index2 < pointsCopy.length)
                        && (currPoint.slopeTo(pointsCopy[index2]) == slopeVal)) {

                    eqSlopePoints++;
                } else {
                    // end of array or slope value changed

                    if (eqSlopePoints >= 3) {
                        // valid segment
                        Point[] segPoints = new Point[eqSlopePoints + 1];

                        segPoints[0] = currPoint;
                        int segIndex = 1;
                        for (int k = index2 - eqSlopePoints; k < index2; k++) {
                            segPoints[segIndex++] = pointsCopy[k];
                        }

                        // Sort the segment points
                        Arrays.sort(segPoints);

                        // Check if the segment is not already in the segment table
                        if (!segTable.contains(Arrays.hashCode(segPoints))) {
                            segTable.put(Arrays.hashCode(segPoints), segPoints);

                            // Print and plot the segment
                            for (int k = 0; k < segPoints.length - 1; k++) {
                                StdOut.print(segPoints[k] + " -> ");
                            }
                            StdOut.print(segPoints[segPoints.length - 1] + "\n");

                            segPoints[0].drawTo(segPoints[segPoints.length - 1]);
                            StdDraw.show(0);
                        }

                    }

                    if (index2 < pointsCopy.length) {
                        slopeVal = currPoint.slopeTo(pointsCopy[index2]);
                        eqSlopePoints = 1;
                    }
                }

                if (index2 == pointsCopy.length) {
                    finished = true;
                }
                index2++;

            }

        }
    }

    private static Point[] readPointsFromFile(String fileName) {
        In fileStream = new In(fileName);
        Point[] points = null;

        // Reading nr of points
        if (fileStream.hasNextLine()) {
            int nrPoints = fileStream.readInt();
            points = new Point[nrPoints];
        }

        // Reading and creating points
        for (int i = 0; (i < points.length) && fileStream.hasNextLine(); i++) {
            int x = fileStream.readInt();
            int y = fileStream.readInt();
            points[i] = new Point(x, y);
        }

        return points;
    }

    private static void plotPoints(Point[] points) {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLUE);

        for (int i = 0; i < points.length; i++) {
//            StdOut.println(i + " : " + points[i]);
            points[i].draw();
        }

        StdDraw.setPenRadius();
        StdDraw.setPenColor();

        StdDraw.show(0);
    }

}
