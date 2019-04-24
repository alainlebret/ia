package fuzzylogic.core;

/**
 * A trapezoid fuzzy set that uses a trapezoidal membership function.
 * <pre>
 *          ____
 *         /    \
 *        /      \
 * ______/        \______
 * </pre>
 */
public class TrapezoidFuzzySet extends FuzzySet {
    public TrapezoidFuzzySet(double min, double max, double beginBase, double beginUpperBase, double endUpperBase, double endBase) {
        super(min, max);
        add(new Point(min, 0));
        add(new Point(beginBase, 0));
        add(new Point(beginUpperBase, 1));
        add(new Point(endUpperBase, 1));
        add(new Point(endBase, 0));
        add(new Point(max, 0));
    }
}
