package fuzzylogic.core;

/**
 * A half left trapezoid fuzzy set that uses a L-function.
 * <pre>
 *           Upper base
 *          __________
 *         /
 *        /
 * ______/
 * </pre>
 */
public class HalfLeftTrapezoidFuzzySet extends FuzzySet {
    public HalfLeftTrapezoidFuzzySet(double min, double max, double endLowerBase, double beginUpperBase) {
        super(min, max);
        add(new Point(min, 0));
        add(new Point(endLowerBase, 0));
        add(new Point(beginUpperBase, 1));
        add(new Point(max, 1));
    }
}
