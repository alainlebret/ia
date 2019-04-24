package fuzzylogic.core;

import java.util.*;

/**
 * A class to manage fuzzy set.
 */
public class FuzzySet {
    private final List<Point> points_;
    private final double min_;
    private final double max_;

    public FuzzySet(double min, double max) {
        points_ = new ArrayList<>();
        min_ = min;
        max_ = max;
    }

    private static double optimum(double firstValue, double secondValue, String method) {
        if (method.equals("Min")) {
            return Math.min(firstValue, secondValue);
        } else {
            return Math.max(firstValue, secondValue);
        }
    }

    private static FuzzySet merge(FuzzySet firstSet, FuzzySet secondSet, String method) {
        FuzzySet resultSet = new FuzzySet(Math.min(firstSet.min_, secondSet.min_), Math.max(firstSet.max_, secondSet.max_));

        Iterator<Point> firstIterator = firstSet.points_.iterator();
        Point pointFromFirstSet = firstIterator.next();
        Point previousPointFromFirstSet = pointFromFirstSet;
        Iterator<Point> secondIterator = secondSet.points_.iterator();
        Point pointFromSecondSet = secondIterator.next();

        int previousRelativePosition;
        int newRelativePosition = (int) Math.signum(pointFromFirstSet.getY() - pointFromSecondSet.getY());

        boolean firstListEnded = false;
        boolean secondListEnded = false;

        while (!firstListEnded && !secondListEnded) {
            double x1 = pointFromFirstSet.getX();
            double x2 = pointFromSecondSet.getX();

            previousRelativePosition = newRelativePosition;
            newRelativePosition = (int) Math.signum(pointFromFirstSet.getY() - pointFromSecondSet.getY());

            if (previousRelativePosition != newRelativePosition && previousRelativePosition != 0 && newRelativePosition != 0) {

                double x = (x1 == x2 ? previousPointFromFirstSet.getX() : Math.min(x1, x2));
                double xPrime = Math.max(x1, x2);

                double slope1 = firstSet.degreeOfMembership(xPrime) - firstSet.degreeOfMembership(x) / (xPrime - x);
                double slope2 = secondSet.degreeOfMembership(xPrime) - secondSet.degreeOfMembership(x) / (xPrime - x);

                double delta = 0;
                if ((slope2 - slope1) != 0) {
                    delta = (secondSet.degreeOfMembership(x) - firstSet.degreeOfMembership(x)) / (slope1 - slope2);
                }

                // Adds the intersection point to the results
                resultSet.add(x + delta, firstSet.degreeOfMembership(x + delta));

                if (x1 < x2) {
                    previousPointFromFirstSet = pointFromFirstSet;
                    if (firstIterator.hasNext()) {
                        pointFromFirstSet = firstIterator.next();
                    } else {
                        firstListEnded = true;
                        pointFromFirstSet = null;
                    }
                } else if (x1 > x2) {
                    if (secondIterator.hasNext()) {
                        pointFromSecondSet = secondIterator.next();
                    } else {
                        pointFromSecondSet = null;
                        secondListEnded = true;
                    }
                }
            } else if (x1 == x2) {
                resultSet.add(x1, optimum(pointFromFirstSet.getY(), pointFromSecondSet.getY(), method));

                if (firstIterator.hasNext()) {
                    previousPointFromFirstSet = pointFromFirstSet;
                    pointFromFirstSet = firstIterator.next();
                } else {
                    pointFromFirstSet = null;
                    firstListEnded = true;
                }
                if (secondIterator.hasNext()) {
                    pointFromSecondSet = secondIterator.next();
                } else {
                    pointFromSecondSet = null;
                    secondListEnded = true;
                }
            } else if (x1 < x2) {
                resultSet.add(x1, optimum(pointFromFirstSet.getY(), secondSet.degreeOfMembership(x1), method));
                if (firstIterator.hasNext()) {
                    previousPointFromFirstSet = pointFromFirstSet;
                    pointFromFirstSet = firstIterator.next();
                } else {
                    pointFromFirstSet = null;
                    firstListEnded = true;
                }
            } else {
                resultSet.add(x2, optimum(firstSet.degreeOfMembership(x2), pointFromSecondSet.getY(), method));
                if (secondIterator.hasNext()) {
                    pointFromSecondSet = secondIterator.next();
                } else {
                    pointFromSecondSet = null;
                    secondListEnded = true;
                }
            }
        }

        if (!firstListEnded) {
            while (firstIterator.hasNext()) {
                pointFromFirstSet = firstIterator.next();
                resultSet.add(pointFromFirstSet.getX(), optimum(pointFromFirstSet.getY(), 0, method));
            }
        } else if (!secondListEnded) {
            while (secondIterator.hasNext()) {
                pointFromSecondSet = secondIterator.next();
                resultSet.add(pointFromSecondSet.getX(), optimum(pointFromSecondSet.getY(), 0, method));
            }
        }

        return resultSet;
    }

    public void add(Point point) {
        points_.add(point);
        Collections.sort(points_);
    }

    public void add(double x, double y) {
        Point point = new Point(x, y);
        add(point);
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass()) {
            return false;
        }
        return toString().equals(o.toString());
    }

    public FuzzySet MultiplyBy(double value) {
        FuzzySet set = new FuzzySet(min_, max_);
        for (Point pt : points_) {
            set.add(pt.getX(), pt.getY() * value);
        }
        return set;
    }

    public double degreeOfMembership(double value) {
        if (value < min_ || value > max_ || points_.size() < 2) {
            return 0;
        }

        Point previousPoint = points_.get(0);
        Point nextPoint = points_.get(1);
        int index = 0;
        while (value >= nextPoint.getX()) {
            index++;
            previousPoint = nextPoint;
            nextPoint = points_.get(index);
        }

        if (previousPoint.getX() == value) {
            return previousPoint.getY();
        } else {
            // Interpolation
            return ((previousPoint.getY() - nextPoint.getY()) *
                    (nextPoint.getX() - value) / (nextPoint.getX() - previousPoint.getX()) + nextPoint.getY());
        }
    }

    public FuzzySet not() {
        FuzzySet set = new FuzzySet(min_, max_);
        for (Point pt : points_) {
            set.add(pt.getX(), 1 - pt.getX());
        }
        return set;
    }

    public FuzzySet and(FuzzySet set) {
        return merge(this, set, "Min");
    }

    public FuzzySet or(FuzzySet set) {
        return merge(this, set, "Max");
    }

    public double centroid() {
        if (points_.size() <= 2) {
            return 0;
        } else {
            double weightedArea = 0;
            double totalArea = 0;
            double localArea;
            Point previousPoint = null;

            for (Point point : points_) {
                if (previousPoint != null) {
                    if (previousPoint.getY() == point.getY()) {
                        // It's a rectangle ==> the centroid is in the middle
                        localArea = point.getY() * (point.getX() - previousPoint.getX());
                        totalArea += localArea;
                        weightedArea += localArea * ((point.getX() - previousPoint.getX()) / 2.0 + previousPoint.getX());
                    } else {
                        // It's a trapeze ==> decomposition in a rectangle + right-angled triangle
                        // 1)
                        localArea = Math.min(point.getY(), previousPoint.getY()) * (point.getX() - previousPoint.getX());
                        totalArea += localArea;
                        weightedArea += localArea * ((point.getX() - previousPoint.getX()) / 2.0 + previousPoint.getX());
                        // 2)
                        localArea = (point.getX() - previousPoint.getX()) * Math.abs(point.getY() - previousPoint.getY()) / 2.0;
                        totalArea += localArea;
                        if (point.getY() > previousPoint.getY()) {
                            weightedArea += localArea * (2.0 / 3.0 * (point.getX() - previousPoint.getX()) + previousPoint.getX());
                        } else {
                            weightedArea += localArea * (1.0 / 3.0 * (point.getX() - previousPoint.getX()) + previousPoint.getX());
                        }
                    }
                }
                previousPoint = point;
            }
            return weightedArea / totalArea;
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(" ");
        sj.add("[" + min_ + "-" + max_ + "]:");
        for (Point pt : points_) {
            sj.add(pt.toString());
        }
        return sj.toString();
    }

}
