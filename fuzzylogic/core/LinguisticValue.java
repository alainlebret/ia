package fuzzylogic.core;

/**
 * A linguistic value used in a fuzzy logic application.
 */
public class LinguisticValue {
    protected final FuzzySet fuzzySet_;
    protected final String name_;

    public LinguisticValue(String name, FuzzySet set) {
        fuzzySet_ = set;
        name_ = name;
    }

    double degreeOfMembership(double value) {
        return fuzzySet_.degreeOfMembership(value);
    }
}
