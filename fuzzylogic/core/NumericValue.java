package fuzzylogic.core;

/**
 * This class associates a linguistic variable to a numeric value.
 */
public class NumericValue {
    private final LinguisticVariable linguisticVariable_;
    private final double value_;

    public NumericValue(LinguisticVariable linguisticVariable, double value) {
        linguisticVariable_ = linguisticVariable;
        value_ = value;
    }

    public LinguisticVariable getLinguisticVariable() {
        return linguisticVariable_;
    }

    public double getValue() {
        return value_;
    }


}
