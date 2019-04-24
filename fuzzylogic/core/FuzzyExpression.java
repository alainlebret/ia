package fuzzylogic.core;

/**
 * A fuzzy expression such as "Variable IS Value".
 * Some examples: "Temperature IS Cold", "Distance IS Small", etc.
 */
public class FuzzyExpression {
    private final LinguisticVariable linguisticVariable_;
    private final String linguisticValueName_;

    public FuzzyExpression(LinguisticVariable linguisticVariable, String value) {
        linguisticVariable_ = linguisticVariable;
        linguisticValueName_ = value;
    }

    public LinguisticVariable getLinguisticVariable() {
        return linguisticVariable_;
    }

    public String getLinguisticValueName() {
        return linguisticValueName_;
    }


}
