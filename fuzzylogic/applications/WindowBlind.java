package fuzzylogic.applications;

import fuzzylogic.core.*;

public class WindowBlind {
    public static void main(String[] arg) {
        System.out.println("Fuzzy logic application: A window blind management");

        FuzzySupervisor supervisor = new FuzzySupervisor("Window blind management");

        System.out.println("Adding input variables...");
        // Input linguistic variable: lighting (in lux, from 0 to 100 000)
        LinguisticVariable lighting = new LinguisticVariable("Lighting", 0, 100000);
        lighting.addLinguisticValue(new LinguisticValue("Dark", new HalfRightTrapezoidFuzzySet(0, 100000, 23000, 26000)));
        lighting.addLinguisticValue(new LinguisticValue("Normal", new TrapezoidFuzzySet(0, 100000, 23000, 27000, 72000, 80000)));
        lighting.addLinguisticValue(new LinguisticValue("Bright", new HalfLeftTrapezoidFuzzySet(0, 100000, 66000, 80000)));
        supervisor.addInputLinguisticVariable(lighting);

        // Input linguistic variable: temperature (in °C, from 6 to 35)
        LinguisticVariable temperature = new LinguisticVariable("Temperature", 6, 35);
        temperature.addLinguisticValue(new LinguisticValue("Cold", new HalfRightTrapezoidFuzzySet(6, 35, 10, 12)));
        temperature.addLinguisticValue(new LinguisticValue("Cool", new TrapezoidFuzzySet(6, 35, 10, 12, 15, 17)));
        temperature.addLinguisticValue(new LinguisticValue("Medium", new TrapezoidFuzzySet(6, 35, 15, 17, 20, 25)));
        temperature.addLinguisticValue(new LinguisticValue("Hot", new HalfLeftTrapezoidFuzzySet(6, 35, 20, 25)));
        supervisor.addInputLinguisticVariable(temperature);

        System.out.println("Adding the output variable...");
        // Input linguistic variable: height_ of the window blind (from 0 cm / closed to 115 cm / open)
        LinguisticVariable windowBlind = new LinguisticVariable("WindowBlind", 0, 115);
        windowBlind.addLinguisticValue(new LinguisticValue("Closed", new HalfRightTrapezoidFuzzySet(0, 115, 25, 40)));
        windowBlind.addLinguisticValue(new LinguisticValue("Middle", new TrapezoidFuzzySet(0, 115, 25, 40, 85, 100)));
        windowBlind.addLinguisticValue(new LinguisticValue("Opened", new HalfLeftTrapezoidFuzzySet(0, 115, 85, 100)));
        supervisor.addOutputLinguisticVariable(windowBlind);

        System.out.println("Adding rules...");
        supervisor.addRule("IF Lighting IS Bright AND temperature IS Hot THEN WindowBlind is Closed");
        supervisor.addRule("IF Lighting IS Dark AND temperature IS Cold THEN WindowBlind IS Opened");

        System.out.println("-- Some practical cases to solve --");
        // First case: a speed of 35 km/h,
        // and a change of direction in 70 m
        System.out.println("Case 1:");
        supervisor.addNumericValue(temperature, 30);
        supervisor.addNumericValue(lighting, 80000);
        System.out.println("Result: " + supervisor.solve() + "\n");

    }
}
