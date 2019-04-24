package fuzzylogic.applications;

import fuzzylogic.core.*;

public class GpsZoom {
    public static void main(String[] arg) {
        System.out.println("Fuzzy logic application: A GPS zoom management");

        FuzzySupervisor supervisor = new FuzzySupervisor("GPS zoom management");

        System.out.println("Adding input variables...");
        // Input linguistic variable: distance (in meters, from 0 to 500 000)
        LinguisticVariable distance = new LinguisticVariable("Distance", 0, 500000);
        distance.addLinguisticValue(new LinguisticValue("Short", new HalfRightTrapezoidFuzzySet(0, 500000, 30, 50)));
        distance.addLinguisticValue(new LinguisticValue("Medium", new TrapezoidFuzzySet(0, 500000, 40, 50, 100, 150)));
        distance.addLinguisticValue(new LinguisticValue("Long", new HalfLeftTrapezoidFuzzySet(0, 500000, 100, 150)));
        supervisor.addInputLinguisticVariable(distance);

        // Input linguistic variable: speed (in km/h, from 0 to 200)
        LinguisticVariable speed = new LinguisticVariable("Speed", 0, 200);
        speed.addLinguisticValue(new LinguisticValue("Slow", new HalfRightTrapezoidFuzzySet(0, 200, 20, 30)));
        speed.addLinguisticValue(new LinguisticValue("Normal", new TrapezoidFuzzySet(0, 200, 20, 30, 70, 80)));
        speed.addLinguisticValue(new LinguisticValue("Fast", new TrapezoidFuzzySet(0, 200, 70, 80, 90, 110)));
        speed.addLinguisticValue(new LinguisticValue("VeryFast", new HalfLeftTrapezoidFuzzySet(0, 200, 90, 110)));
        supervisor.addInputLinguisticVariable(speed);

        System.out.println("Adding the output variable...");
        // Input linguistic variable: zoom level (from 1 to 5)
        LinguisticVariable zoom = new LinguisticVariable("Zoom", 0, 5);
        zoom.addLinguisticValue(new LinguisticValue("Small", new HalfRightTrapezoidFuzzySet(0, 5, 1, 2)));
        zoom.addLinguisticValue(new LinguisticValue("Normal", new TrapezoidFuzzySet(0, 5, 1, 2, 3, 4)));
        zoom.addLinguisticValue(new LinguisticValue("Big", new HalfLeftTrapezoidFuzzySet(0, 5, 3, 4)));
        supervisor.addOutputLinguisticVariable(zoom);

        System.out.println("Adding rules...");
        // Adds rules (9 to cover the 12 cases)
        supervisor.addRule("IF Distance IS Long THEN Zoom IS Small");
        supervisor.addRule("IF Distance IS Short AND Speed IS Slow THEN Zoom IS Normal");
        supervisor.addRule("IF Distance IS Short AND Speed IS Normal THEN Zoom IS Normal");
        supervisor.addRule("IF Distance IS Short AND Speed IS Fast THEN Zoom IS Big");
        supervisor.addRule("IF Distance IS Short AND Speed IS VeryFast THEN Zoom IS Big");
        supervisor.addRule("IF Distance IS Medium AND Speed IS Slow THEN Zoom IS Small");
        supervisor.addRule("IF Distance IS Medium AND Speed IS Normal THEN Zoom IS Normal");
        supervisor.addRule("IF Distance IS Medium AND Speed IS Fast THEN Zoom IS Normal");
        supervisor.addRule("IF Distance IS Medium AND Speed IS VeryFast THEN Zoom IS Big");

        System.out.println("-- Some practical cases to solve --");
        // First case: a speed of 35 km/h,
        // and a change of direction in 70 m
        System.out.println("Case 1:");
        supervisor.addNumericValue(speed, 35);
        supervisor.addNumericValue(distance, 70);
        System.out.println("Result: " + supervisor.solve() + "\n");

        // Second case: a speed of 25 km/h,
        // and a change of direction in 70 m
        supervisor.clearNumericValues();
        System.out.println("Case 2:");
        supervisor.addNumericValue(speed, 25);
        supervisor.addNumericValue(distance, 70);
        System.out.println("Result: " + supervisor.solve() + "\n");

        // Third case: speed of 73 km/h,
        // and a change of direction in 40 m
        supervisor.clearNumericValues();
        System.out.println("Case 3:");
        supervisor.addNumericValue(speed, 73);
        supervisor.addNumericValue(distance, 40);
        System.out.println("Result: " + supervisor.solve() + "\n");

        // Fourth case: speed of 100 km/h,
        // and a change of direction in 110 m
        supervisor.clearNumericValues();
        System.out.println("Case 4:");
        supervisor.addNumericValue(speed, 100);
        supervisor.addNumericValue(distance, 110);
        System.out.println("Result: " + supervisor.solve() + "\n");

        // Fifth case: speed of 45 km/h,
        // and a change of direction in 160 m
        supervisor.clearNumericValues();
        System.out.println("Case 5:");
        supervisor.addNumericValue(speed, 45);
        supervisor.addNumericValue(distance, 160);
        System.out.println("Result: " + supervisor.solve() + "\n");
    }
}
