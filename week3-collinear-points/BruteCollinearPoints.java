/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/


import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    private Point[] points;
    private java.util.ArrayList<LineSegment> segments;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] pointsArg) {
        if (pointsArg == null) {
            throw new NullPointerException();
        }

        this.points = pointsArg.clone();

        java.util.Arrays.sort(points);

        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        segments = new java.util.ArrayList<LineSegment>();

        numberOfSegments = 0;

        if (points.length >= 4) {
            for (int p = 0; p < points.length - 3; p++) {
                for (int q = p + 1; q < points.length - 2; q++) {
                    for (int r = q + 1; r < points.length - 1; r++) {
                        for (int s = r + 1; s < points.length; s++) {
                            double slopeOne = points[p].slopeTo(points[q]);
                            double slopeTwo = points[q].slopeTo(points[r]);
                            boolean condOne = isNearlyEqual(slopeOne, slopeTwo);

                            slopeOne = points[q].slopeTo(points[r]);
                            slopeTwo = points[r].slopeTo(points[s]);
                            boolean condTwo = isNearlyEqual(slopeOne, slopeTwo);

                            if (condOne && condTwo) {

                                Point[] fourPoints = {points[p], points[q], points[r], points[s]};
                                java.util.Arrays.sort(fourPoints);

                                segments.add(new LineSegment(fourPoints[0], fourPoints[3]));
                                numberOfSegments++;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isNearlyEqual(double a, double b) {
        if (a == b) {
            return true;
        }

        double epsilon = Math.ulp(Math.min(a, b));
        return Math.abs(a - b) <= epsilon;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segmentsCopy = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            segmentsCopy[i] = segments.get(i);
        }

        return segmentsCopy;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}