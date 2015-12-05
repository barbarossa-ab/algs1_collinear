<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>
Programming Assignment 3: Pattern Recognition Assignment
</title><style type="text/css"></style></head>


<body>
<h2>Programming Assignment 3: Pattern Recognition</h2>

<p>
<font color="green"><em>The APIs have been revised substantially for the Fall 2015 offering.</em></font>
<br>
<font color="red"><em>NOTE : I solved the old version of the assigment - the one with collinear points.</em></font>
<br>

</p><p>
<br>
Write a program to recognize line patterns in a given set of points.

</p><p>
Computer vision involves analyzing patterns in visual images and
reconstructing the real-world objects that produced them.  The process
is often broken up into two phases: <em>feature detection</em> and
<em>pattern recognition</em>. Feature detection involves selecting
important features of the image; pattern recognition involves
discovering patterns in the features. We will investigate a
particularly clean pattern recognition problem involving points and
line segments.  This kind of pattern recognition arises in many other
applications such as statistical data analysis.

</p><p>
<b>The problem.</b>
Given a set of <em>N</em> distinct points in the plane, 
find every (maximal) line segment that connects a subset of 4 or more of the points.
</p><p>

</p><center>
<img src="./Programming Assignment 3_ Pattern Recognition Assignment_files/lines2.png" width="500" height="200" alt="Points and lines">
</center>

<p>
<b>Point data type.</b>
Create an immutable data type <tt>Point</tt> that represents a point in the plane
by implementing the following API:

</p><blockquote>
<pre><b>public class Point implements Comparable&lt;Point&gt; {</b>
<font color="gray">   public Point(int x, int y)                         // constructs the point (x, y)</font>

<font color="gray">   public   void draw()                               // draws this point</font>
<font color="gray">   public   void drawTo(Point that)                   // draws the line segment from this point to that point</font>
<font color="gray">   public String toString()                           // string representation</font>

<b>   public               int compareTo(Point that)</b>     <font color="gray">// compare two points by y-coordinates, breaking ties by x-coordinates</font>
<b>   public            double slopeTo(Point that)</b>       <font color="gray">// the slope between this point and that point</font>
<b>   public Comparator&lt;Point&gt; slopeOrder()</b>              <font color="gray">// compare two points by slopes they make with this point</font>
<b>}</b>
</pre>
</blockquote>

To get started, use the data type
<a href="http://coursera.cs.princeton.edu/algs4/testing/collinear/Point.java">Point.java</a>,
which implements the constructor and the
<tt>draw()</tt>, <tt>drawTo()</tt>, and <tt>toString()</tt> methods.
Your job is to add the following components.


<ul>


<p></p><li> The <tt>compareTo()</tt> method should compare points by their <em>y</em>-coordinates,
breaking ties by their <em>x</em>-coordinates.
Formally, the invoking point
(<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>)
is <em>less than</em> the argument point
(<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>)
if and only if either <em>y</em><sub>0</sub> &lt; <em>y</em><sub>1</sub> or if
<em>y</em><sub>0</sub> = <em>y</em><sub>1</sub> and <em>x</em><sub>0</sub> &lt; <em>x</em><sub>1</sub>.

<p></p></li><li> The <tt>slopeTo()</tt> method should return the slope between the invoking point
(<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>) and the argument point
(<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), which is given by the formula
(<em>y</em><sub>1</sub> − <em>y</em><sub>0</sub>) / (<em>x</em><sub>1</sub> − <em>x</em><sub>0</sub>).
Treat the slope of a horizontal line segment as positive zero;
treat the slope of a vertical line segment as positive infinity;
treat the slope of a degenerate line segment (between a point and itself) as negative infinity.

<p></p></li><li> The <tt>slopeOrder()</tt> method should return a comparator that compares its two argument
points by the slopes they make with the invoking point (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>).
Formally, the point (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>) is <em>less than</em>
the point (<em>x</em><sub>2</sub>, <em>y</em><sub>2</sub>) if and only if the slope
(<em>y</em><sub>1</sub> − <em>y</em><sub>0</sub>) / (<em>x</em><sub>1</sub> − <em>x</em><sub>0</sub>) 
is less than the slope
(<em>y</em><sub>2</sub> − <em>y</em><sub>0</sub>) / (<em>x</em><sub>2</sub> − <em>x</em><sub>0</sub>).
Treat horizontal, vertical, and degenerate line segments as in the <tt>slopeTo()</tt> method.

</li></ul>

<p><em>Corner cases.</em> To avoid potential complications with integer overflow or floating-point precision,
you may assume that the constructor arguments <tt>x</tt> and <tt>y</tt> are each between 0 and 32,767.

</p><p>
<b>Line segment data type.</b>
To represent line segments in the plane, use the data type
<a href="http://coursera.cs.princeton.edu/algs4/testing/collinear/LineSegment.java">LineSegment.java</a>,
which has the following API:

</p><blockquote>
<pre><b>public class LineSegment {</b>
<font color="gray">   public LineSegment(Point p, Point q)        // constructs the line segment between points p and q</font>
<font color="gray">   public   void draw()                        // draws this line segment</font>
<font color="gray">   public String toString()                    // string representation</font>
<b>}</b>
</pre>
</blockquote>

<p>
<b>Brute force.</b>
Write a program <tt>BruteCollinearPoints.java</tt> that examines 4 
points at a time and checks whether they all lie on the same line segment, returning all such line segments.
To check whether the 4 points <em>p</em>, <em>q</em>, <em>r</em>, and <em>s</em> are collinear,
check whether the three slopes between <em>p</em> and <em>q</em>, 
between <em>p</em> and <em>r</em>, and between <em>p</em> and <em>s</em>
are all equal.


</p><blockquote>
<pre><b>public class BruteCollinearPoints {</b>
<b>   public BruteCollinearPoints(Point[] points)    </b><font color="gray">// finds all line segments containing 4 points</font>
<b>   public           int numberOfSegments()        </b><font color="gray">// the number of line segments</font>
<b>   public LineSegment[] segments()                </b><font color="gray">// the line segments</font>
<b>}</b>
</pre>
</blockquote>


<p>
The method <tt>segments()</tt> should include each line segment containing 4 points exactly once.

If 4 points appear on a line segment in the
order <em>p</em>→<em>q</em>→<em>r</em>→<em>s</em>,
then you should include either the line segment
<em>p</em>→<em>s</em> or <em>s</em>→<em>p</em> (but not both)
and you should not include <em>subsegments</em> such as <em>p</em>→<em>r</em> or
<em>q</em>→<em>r</em>.
For simplicity,
we will not supply any input to <tt>BruteCollinearPoints</tt> that has 5 or more collinear points.

</p><p><em>Corner cases.</em>
Throw a <tt>java.lang.NullPointerException</tt> either the argument to the constructor
is <tt>null</tt> or if any point in the array is <tt>null</tt>.
Throw a <tt>java.lang.IllegalArgumentException</tt> if the argument to the constructor
contains a repeated point.



</p><p><em>Performance requirement.</em>
The order of growth of the running time of your program should be
<em>N</em><sup>4</sup> in the worst case and 
it should use space proportional to <em>N</em> plus the number of line segments returned.


</p><p>
<b>A faster, sorting-based solution.</b>
Remarkably, it is possible to solve the problem much faster than the
brute-force solution described above.
Given a point <em>p</em>, the following method determines whether <em>p</em>
participates in a set of 4 or more collinear points.
</p><ul>
<li>Think of <em>p</em> as the origin.
<p></p></li><li>For each other point <em>q</em>, determine the slope it makes with <em>p</em>.
<p></p></li><li>Sort the points according to the slopes
they makes with <em>p</em>.
<p></p></li><li>Check if any 3 (or more) adjacent points in the sorted order have equal
slopes with respect to <em>p</em>.
If so, these points, together with <em>p</em>, are collinear.
</li></ul>

Applying this method for each of the <em>N</em> points in turn yields an
efficient algorithm to the problem.
The algorithm solves the problem because points that have equal 
slopes with respect to <em>p</em> are collinear, and sorting brings such points together.
The algorithm is fast because the bottleneck operation is sorting.

<p>

</p><center>
<img src="./Programming Assignment 3_ Pattern Recognition Assignment_files/lines1.png" alt="Points and slopes">
</center>
<p>
Write a program <tt>FastCollinearPoints.java</tt> that implements this algorithm.

</p><blockquote>
<pre><b>public class FastCollinearPoints {</b>
<b>   public FastCollinearPoints(Point[] points)     </b><font color="gray">// finds all line segments containing 4 or more points</font>
<b>   public           int numberOfSegments()        </b><font color="gray">// the number of line segments</font>
<b>   public LineSegment[] segments()                </b><font color="gray">// the line segments</font>
<b>}</b>
</pre>
</blockquote>

<p>
The method <tt>segments()</tt> should include each <em>maximal</em> line segment
containing 4 (or more) points exactly once.
For example, if 5 points appear on a line segment in the
order <em>p</em>→<em>q</em>→<em>r</em>→<em>s</em>→<em>t</em>,
then do not include the subsegments <em>p</em>→<em>s</em> or <em>q</em>→<em>t</em>.


</p><p><em>Corner cases.</em>
Throw a <tt>java.lang.NullPointerException</tt> either the argument to the constructor
is <tt>null</tt> or if any point in the array is <tt>null</tt>.
Throw a <tt>java.lang.IllegalArgumentException</tt> if the argument to the constructor
contains a repeated point.

</p><p><em>Performance requirement.</em>
The order of growth of the running time of your program should be
<em>N</em><sup>2</sup> log <em>N</em> in the worst case and 
it should use space proportional to <em>N</em> plus the number of line segments returned.
<tt>FastCollinearPoints</tt> should work properly even if the input has 5 or more collinear points.


</p><p>
<b>Sample client.</b> 
This client program takes the name of an input file as a command-line argument;
read the input file (in the format specified below);
prints to standard output the line segments that your program discovers, one per line;
and draws to standard draw the line segments.

</p><blockquote>
<pre>public static void main(String[] args) {

    <font color="gray">// read the N points from a file</font>
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    for (int i = 0; i &lt; N; i++) {
        int x = in.readInt();
        int y = in.readInt();
        points[i] = new Point(x, y);
    }

    <font color="gray">// draw the points</font>
    StdDraw.show(0);
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
        p.draw();
    }
    StdDraw.show();

    <font color="gray">// print and draw the line segments</font>
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
}
</pre>
</blockquote>



<p>
<b>Input format.</b>
We supply several sample input files (suitable for use with the test client above)
 in the following format:
An integer <em>N</em>, followed by <em>N</em>
pairs of integers (<em>x</em>, <em>y</em>), each between 0 and 32,767.
Below are two examples.

</p><blockquote>
<pre>% <b>more input6.txt</b>       % <b>more input8.txt</b>
6                       8
19000  10000             10000      0
18000  10000                 0  10000
32000  10000              3000   7000
21000  10000              7000   3000
 1234   5678             20000  21000
14000  10000              3000   4000
                         14000  15000
                          6000   7000
</pre>
</blockquote>


<blockquote><pre>% <b>java BruteCollinearPoints input8.txt</b>
(10000, 0) -&gt; (0, 10000) 
(3000, 4000) -&gt; (20000, 21000) 

% <b>java FastCollinearPoints input8.txt</b>
(3000, 4000) -&gt; (20000, 21000) 
(0, 10000) -&gt; (10000, 0)

% <b>java FastCollinearPoints input6.txt</b>
(14000, 10000) -&gt; (32000, 10000) 
</pre>
</blockquote>



<p>
<b>Deliverables.</b>
Submit only the files
<tt>BruteCollinearPoints.java</tt>, <tt>FastCollinearPoints.java</tt>, and <tt>Point.java</tt>.
We will supply <tt>LineSegment.java</tt> and <tt>algs4.jar</tt>.
You may not call any library functions other those in
<tt>java.lang</tt>, <tt>java.util</tt>, and <tt>algs4.jar</tt>.
In particular, you may call <tt>Arrays.sort()</tt>.


</p><address><small>
This assignment was developed by Kevin Wayne.
<br>Copyright © 2005.
</small>
</address>




</body></html>
