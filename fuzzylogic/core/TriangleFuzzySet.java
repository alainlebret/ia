package fuzzylogic.core;

/**
 * A triangle fuzzy set that uses a triangular membership function.
 * <pre>
 *         /\
 *        /  \
 * ______/    \_________
 * </pre>
 */
public class TriangleFuzzySet extends FuzzySet {
    public TriangleFuzzySet(double min, double max, double beginBase, double apex, double endBase) {
        super(min, max);
        add(new Point(min, 0));
        add(new Point(beginBase, 0));
        add(new Point(apex, 1));
        add(new Point(endBase, 0));
        add(new Point(max, 0));
    }
}
