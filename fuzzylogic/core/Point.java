package fuzzylogic.core;

/**
 * A point in a fuzzy membership function.
 */
public class Point implements Comparable {
    private final double x_;
    private final double y_;

    public Point(double x, double y) {
        x_ = x;
        y_ = y;
    }

    public double getX() {
        return x_;
    }

    public double getY() {
        return y_;
    }

    @Override
    public int compareTo(Object o) {
        return (int) (x_ - ((Point) o).x_);
    }

    @Override
    public String toString() {
        return "(" + x_ + ";" + y_ + ")";
    }
}
