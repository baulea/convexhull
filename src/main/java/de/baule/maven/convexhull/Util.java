package de.baule.maven.convexhull;

import javax.vecmath.Point2d;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import java.util.Comparator;
import java.util.Set;

public final class Util {

    private static final Comparator<Point2d> LEFTMOST_LOWEST =
            Comparator.nullsLast(Comparator.comparingDouble(Point2d::getX).thenComparing(Point2d::getY));

    private static final Comparator<Point2d> LOWEST_LEFTMOST =
            Comparator.nullsLast(Comparator.comparingDouble(Point2d::getY).thenComparing(Point2d::getX));

    /**
     * utility class
     **/
    private Util() {
    }

    /**
     * @param points     set of points
     * @param comparator null-safe comparator for point
     * @return smallest point with reference to comparator
     */
    @Nullable
    private static Point2d getMinimum(@NonNull final Set<Point2d> points, Comparator<Point2d> comparator) {
        Point2d minimum = null;
        for (final Point2d point : points) {
            if (comparator.compare(point, minimum) < 0) {
                minimum = point;
            }
        }
        return minimum;
    }


    /**
     * get the leftmost-lowest point from a set of points
     *
     * @param points set of points
     * @return leftmost-lowest point
     */
    @Nullable
    public static Point2d computeLeftmostLowest(@NonNull final Set<Point2d> points) {
        return getMinimum(points, LEFTMOST_LOWEST);
    }

    /**
     * get the lowest-leftmost point from a set of points
     *
     * @param points set of points
     * @return lowest-leftmost point
     */
    @Nullable
    public static Point2d computeLowestLeftmost(@NonNull final Set<Point2d> points) {
        return getMinimum(points, LOWEST_LEFTMOST);
    }

    /**
     * calculate orientation of 3 points
     *
     * @param p1 point1
     * @param p2 point2
     * @param p3 point3
     * @return 0 if (p1,p2,p3) are collinear; 1 if (p1,p2,p3) are oriented
     * clockwise; -1 if (p1,p2,p3) are oriented counter-clockwise
     */
    public static int orientation(@NonNull final Point2d p1, @NonNull final Point2d p2,
                                  @NonNull final Point2d p3) {

        final double val = (p2.y - p1.y) * (p3.x - p2.x) - (p2.x - p1.x) * (p3.y - p2.y);
        return val == 0 ? 0 : val < 0 ? -1 : 1;
    }
}
