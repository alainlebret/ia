package fuzzylogic.core;

/**
 * A half-right trapezoid fuzzy set that uses a R-function.
 * <pre>
 * Upper base
 * _____
 *      \
 *       \
 *        \_________ Lower base
 * </pre>
 */
public class HalfRightTrapezoidFuzzySet extends FuzzySet {
    public HalfRightTrapezoidFuzzySet(double min, double max, double endUpperBase, double endLowerBase) {
        super(min, max);
        add(new Point(min, 1));
        add(new Point(endUpperBase, 1));
        add(new Point(endLowerBase, 0));
        add(new Point(max, 0));
    }
}
