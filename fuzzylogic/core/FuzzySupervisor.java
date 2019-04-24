package fuzzylogic.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to manage the fuzzy system.
 */
public class FuzzySupervisor {
    private final String name_;
    private final List<LinguisticVariable> inputs_;
    private final List<FuzzyRule> rules_;
    private final List<NumericValue> problem_;
    private LinguisticVariable output_;

    public FuzzySupervisor(String name) {
        name_ = name;
        inputs_ = new ArrayList<>();
        rules_ = new ArrayList<>();
        problem_ = new ArrayList<>();
    }

    // Ajout d'une variable linguistique en entrée
    public void addInputLinguisticVariable(LinguisticVariable linguisticVariable) {
        inputs_.add(linguisticVariable);
    }

    // Ajout d'une variable linguistique en output_
    // 1 seule possible : remplace l'existante si besoin
    public void addOutputLinguisticVariable(LinguisticVariable linguisticVariable) {
        output_ = linguisticVariable;
    }

    // Ajout d'une règle
    public void addRule(FuzzyRule rule) {
        rules_.add(rule);
    }

    // Ajout d'une règle (format textuel)
    public void addRule(String ruleAsString) {
        FuzzyRule rule = new FuzzyRule(ruleAsString, this);
        rules_.add(rule);
    }

    // Ajout d'une value_ numérique en entrée
    public void addNumericValue(LinguisticVariable linguisticVariable, double value) {
        problem_.add(new NumericValue(linguisticVariable, value));
    }

    // Remise à zéro du problème (pour passer au cas suivant)
    public void clearNumericValues() {
        problem_.clear();
    }

    // Retrouver une variable linguistique à partir de son name_
    public LinguisticVariable getLinguisticVariableByItsName(String name) {
        for (LinguisticVariable variable : inputs_) {
            if (variable.getName().equalsIgnoreCase(name)) {
                return variable;
            }
        }
        if (output_.getName().equalsIgnoreCase(name)) {
            return output_;
        }
        return null;
    }

    // Résoud le problème posé
    public double solve() {
        FuzzySet result = new FuzzySet(output_.getMinValue(), output_.getMaxValue());
        result.add(output_.getMinValue(), 0);
        result.add(output_.getMaxValue(), 0);

        for (FuzzyRule rule : rules_) {
            result = result.or(rule.apply(problem_));
        }

        // Defuzzifying
        return result.centroid();
    }
}
