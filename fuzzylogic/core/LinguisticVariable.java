package fuzzylogic.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A linguistic variable used in a fuzzy logic application. A linguistic
 * variable such as age may accept values_ such as young and its antonym old.
 */
public class LinguisticVariable {
    private final String name_;
    private final List<LinguisticValue> values_;
    private final double minValue_;
    private final double maxValue_;

    // Constructeur
    public LinguisticVariable(String name, double min, double max) {
        name_ = name;
        minValue_ = min;
        maxValue_ = max;
        values_ = new ArrayList<>();
    }

    public void addLinguisticValue(LinguisticValue value) {
        values_.add(value);
    }

    public void addLinguisticValue(String name, FuzzySet set) {
        values_.add(new LinguisticValue(name, set));
    }

    public void clearValues() {
        values_.clear();
    }

    LinguisticValue getLinguisticValueFromItsName(String name) {
        for (LinguisticValue value : values_) {
            if (value.name_.equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name_;
    }

    public List<LinguisticValue> getValues() {
        return values_;
    }

    public double getMinValue() {
        return minValue_;
    }

    public double getMaxValue() {
        return maxValue_;
    }


}
