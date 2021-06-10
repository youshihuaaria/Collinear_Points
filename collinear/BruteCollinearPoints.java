import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final int count;
    private final LineSegment[] segs;
    
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        
        if (points == null) {
            throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        
        ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
        Point[] ps = points.clone();
        Arrays.sort(ps);
        
        for (int p = 0; p < ps.length - 3; p++) {
            for (int q = p + 1; q < ps.length - 2; q++) {
                for (int r = q + 1; r < ps.length - 1; r++) {
                    for (int s = r + 1; s < ps.length; s++) {

                        double pq = ps[p].slopeTo(ps[q]);
                        double pr = ps[p].slopeTo(ps[r]);
                        if (pq != pr) {
                            continue;
                        }
                        double sps = ps[p].slopeTo(ps[s]);
                        if (pq == sps) {
                            LineSegment lineseg = new LineSegment(ps[p], ps[s]);
                            if (!ls.contains(lineseg)) {
                                ls.add(lineseg);
                            }
                        }
                    }
                }
            }
        }
        
        this.count = ls.size();
        segs = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            segs[i] = ls.get(i);
        }
    }
    public int numberOfSegments() {
        // the number of line segments
        return count;
    }
    public LineSegment[] segments() {
        // the line segments
        return segs.clone();
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("input8.txt");
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
    }
}
