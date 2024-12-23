package de.baule.maven.convexhull;

import javax.vecmath.Point2d;
import org.eclipse.jdt.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * <p><a href="https://en.wikipedia.org/wiki/Graham_scan">Graham Scan (Wikipedia)</a></p>
 *
 * <p><a href="https://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/">Graham Scan (geeksforgeeks)</a></p>
 */
public class ConvexHullImplGrahamScan implements ConvexHull {

    @Override
    @NonNull
    public List<Point2d> generateConvexHull(final @NonNull Set<Point2d> points) {

        final LinkedList<Point2d> convexHull = new LinkedList<>();

        final Point2d firstHullPoint = Util.computeLowestLeftmost(points);
        if (firstHullPoint != null) {
            // the convex hull is built starting with the lowest-leftmost point
            // and continues counter-clockwise
            convexHull.add(firstHullPoint);

            final List<Point2d> sortedPolygonPoints = this.removeFirstAndSort(points, firstHullPoint);

            final Stack<Point2d> stack = new Stack<>();

            for (final Point2d point : sortedPolygonPoints) {
                while (stack.size() > 1 && Util.orientation(stack.elementAt(stack.size() - 2),
                        stack.peek(), point) >= 0) {
                    // if the last two points on the stack together by the  current point are not oriented
                    // counterclockwise, the middle one (on top of the stack) has to be eliminated
                    stack.pop();
                }
                stack.push(point);
            }

            while (!stack.empty()) {
                final Point2d nextLastPoint = stack.pop();
                // insert after firstHullPoint and move others towards the end of the list
                convexHull.add(1, nextLastPoint);
            }
        }
        return convexHull;
    }

    /**
     * remove p0 from set of points and sort the remaining points with respect
     * to p0 counter-clockwise
     *
     * @param points set of points
     * @param p0     point to remove
     * @return remaining list of points
     */
    private List<Point2d> removeFirstAndSort(final Set<Point2d> points, @NonNull final Point2d p0) {

        final Comparator<? super Point2d> comparator = (Comparator<Point2d>) (p1, p2) -> Util.orientation(p0, p1, p2);

        final List<Point2d> list = new ArrayList<>(points);
        // remove first point from list, which has been already added to complex hull.
        // As list was constructed from a set, list can contain p0 only once.
        list.remove(p0);
        // sort remaining point counter-clockwise around p0
        list.sort(comparator);

        return list;
    }

}
