/******************************************************************************
 *  Compilation: 
 *  [optional lines]
 *  Execution: 
 *  [optional lines]
 ******************************************************************************/


import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class FastCollinearPoints {
    private Point[] points;
    private java.util.ArrayList<LineSegment> segments;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] pointsArg) {
        if (pointsArg == null) {
            throw new NullPointerException();
        }

        this.points = new Point[pointsArg.length];
        System.arraycopy(pointsArg, 0, points, 0, pointsArg.length);

        java.util.Arrays.sort(points);

        for (int i = 1; i < points.length; i++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        segments = new java.util.ArrayList<LineSegment>();

        numberOfSegments = 0;

        if (points.length >= 4) {
            for (int p = 0; p < pointsArg.length; p++) {
                java.util.Arrays.sort(points, pointsArg[p].slopeOrder());

                int counter = 0;
                int startIndex = 1;

                for (int q = 1; q < points.length - 1; q++) {
                    counter++;
                    double a = pointsArg[p].slopeTo(points[q]);
                    double b = pointsArg[p].slopeTo(points[q + 1]);
                    if (!isNearlyEqual(a, b) || q == points.length - 2) {
                        if (q == points.length - 2) {
                            if (isNearlyEqual(a, b)) {

                                counter++;   
                            }
                        }

                        if (counter < 3) {                            
                            counter = 0;
                            startIndex = q + 1;
                            continue;
                        }

                        Point[] collinearPoints = new Point[counter + 1];
                        for (int r = startIndex, s = 0; r < startIndex + counter; r++) {
                            collinearPoints[s++] = points[r];
                        }
                        collinearPoints[collinearPoints.length - 1] = pointsArg[p];
                        java.util.Arrays.sort(collinearPoints);

                        Point startPoint = collinearPoints[0];
                        if (startPoint.compareTo(pointsArg[p]) != 0) {
                            counter = 0;
                            startIndex = q + 1;
                            continue;
                        }

                        Point endPoint = collinearPoints[collinearPoints.length - 1];
                        segments.add(new LineSegment(startPoint, endPoint));
                        numberOfSegments++;
                        
                        counter = 0;
                        startIndex = q + 1;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}