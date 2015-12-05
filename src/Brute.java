
import java.util.Arrays;

public class Brute {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Wrong number of arguments; "
                    + "usage = Brute input_file");
        }

        String fileName = args[0];
        Point[] points = readPointsFromFile(fileName);
        plotPoints(points);

        if (points.length < 4) {
            return;
        }

        for (int i = 0; i < (points.length - 3); i++) {
            for (int j = i + 1; j < (points.length - 2); j++) {
                for (int k = j + 1; k < (points.length - 1); k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if ((points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]))
                                && (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))) {

                            Point[] segPoints = new Point[4];

                            segPoints[0] = points[i];
                            segPoints[1] = points[j];
                            segPoints[2] = points[k];
                            segPoints[3] = points[l];

                            Arrays.sort(segPoints);

                            StdOut.println(segPoints[0] + " -> "
                                    + segPoints[1] + " -> "
                                    + segPoints[2] + " -> "
                                    + segPoints[3]);

                            segPoints[0].drawTo(segPoints[3]);
                            StdDraw.show(0);
                        }
                    }
                }
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
            points[i].draw();
        }

        StdDraw.setPenRadius();
        StdDraw.setPenColor();

        StdDraw.show(0);
    }
}
