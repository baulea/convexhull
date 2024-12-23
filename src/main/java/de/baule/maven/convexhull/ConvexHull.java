package de.baule.maven.convexhull;

import javax.vecmath.Point2d;
import org.eclipse.jdt.annotation.NonNull;

import java.util.List;
import java.util.Set;

public interface ConvexHull {
    /**
     * Generate the convex hull from a set of points
     * <a href="https://en.wikipedia.org/wiki/Convex_hull">convex hull (Wikipedia)</a>
     *
     * @param points set of points
     * @return convex hull as a list of points. The convex hull is formed by a
     * path from each point in the list to its successor, and from the
     * last point in the list back to the first point in the list.
     */
    @NonNull
    List<Point2d> generateConvexHull(@NonNull Set<Point2d> points);
}
