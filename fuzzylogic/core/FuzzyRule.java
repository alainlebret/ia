package fuzzylogic.core;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a fuzzy rule with some premises and a conclusion.
 */
public class FuzzyRule {
    private List<FuzzyExpression> premises_;
    private FuzzyExpression conclusion_;

    public FuzzyRule(List<FuzzyExpression> premises, FuzzyExpression conclusion) {
        premises_ = premises;
        conclusion_ = conclusion;
    }

    public FuzzyRule(String rule, FuzzySupervisor supervisor) {
        rule = rule.toUpperCase();

        // Splits the rule to some premises and a conclusion using the "THEN" keyword
        String[] premises = rule.split(" THEN ");
        if (premises.length == 2) {
            premises[0] = premises[0].replaceFirst("IF ", "").trim();
            String[] premiseElements = premises[0].split(" AND ");
            premises_ = new ArrayList<>();
            for (String expression : premiseElements) {
                // Splits each premise to create a fuzzy expression using the "IS" keyword
                String[] partsOfTheExpression = expression.trim().split(" IS ");
                if (partsOfTheExpression.length == 2) {
                    FuzzyExpression fuzzyExpression = new FuzzyExpression(supervisor.getLinguisticVariableByItsName(partsOfTheExpression[0]), partsOfTheExpression[1]);
                    premises_.add(fuzzyExpression);
                }
            }
            String[] conclusionElements = premises[1].trim().split(" IS ");
            if (conclusionElements.length == 2) {
                conclusion_ = new FuzzyExpression(supervisor.getLinguisticVariableByItsName(conclusionElements[0]), conclusionElements[1]);
            }
        }
    }

    /**
     * Applies a fuzzy rule to a given problem and returns a fuzzy set.
     *
     * @param problem
     * @return
     */
    FuzzySet apply(List<NumericValue> problem) {
        double degree = 1;
        // We are looking for the degree of each premise
        for (FuzzyExpression premise : premises_) {
            double localDegree = 0;
            LinguisticValue linguisticValue = null;
            for (NumericValue numericValue : problem) {
                if (premise.getLinguisticVariable().equals(numericValue.getLinguisticVariable())) {
                    linguisticValue = premise.getLinguisticVariable().getLinguisticValueFromItsName(premise.getLinguisticValueName());
                    if (linguisticValue != null) {
                        localDegree = linguisticValue.degreeOfMembership(numericValue.getValue());
                        break;
                    }
                }
            }
            if (linguisticValue == null) {
                return null;
            }
            degree = Math.min(degree, localDegree);
        }

        return conclusion_.getLinguisticVariable().getLinguisticValueFromItsName(conclusion_.getLinguisticValueName()).fuzzySet_.MultiplyBy(degree);
    }
}
