import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private final int count;
    private final LineSegment[] segs;
    
    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        
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
        
        Point[] ps = points.clone();
        Point[] ps2 = ps.clone();
        ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
        Arrays.sort(ps);
        Point curr = null;
        
        for (int p = 0; p < ps.length; p++) {
            curr = ps[p];
            Arrays.sort(ps2); // stability
            Arrays.sort(ps2, curr.slopeOrder());            
            int c = 1;
            Point start = null; // remove all repeats
           
            for (int q = 1; q < ps.length; q++) {
                if (curr.slopeTo(ps2[q]) == curr.slopeTo(ps2[q - 1])) {
                    c++;
                    if (c == 2) {
                        start = ps2[q - 1];
                    }
                    if (c >= 3 && (q == ps.length - 1)) { // last point
                        if (curr.compareTo(start) < 0) {
                            ls.add(new LineSegment(curr, ps2[q]));
                        }
                    }
                } else {
                    if (c >= 3) {
                        if (curr.compareTo(start) < 0) {
                            ls.add(new LineSegment(curr, ps2[q - 1]));
                        }
                        
                    }
                    c = 1;
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
        In in = new In("input9.txt");
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
        StdDraw.setPenRadius(0.007);

        // print and draw the line segments
        StdDraw.setPenColor(StdDraw.RED);
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        
        StdDraw.setPenRadius(0.009);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
    }
}
