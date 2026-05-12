package org.matsim.analysis.solutions.ue5;

import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.router.TripStructureUtils;

public class Task22AverageCarTravelTime {
    public static void main(String[] args) {
        Population population = PopulationUtils.readPopulation("output/output_experienced_plans.xml");

        double carTravelTimeSum = 0.0;
        int numberOfCarLegs = 0;

        for (Person person : population.getPersons().values()) {
            Plan selectedPlan = person.getSelectedPlan();

            for (Leg leg : TripStructureUtils.getLegs(selectedPlan)) {
                if (leg.getMode().equals("car") && leg.getTravelTime().isDefined()) {
                    carTravelTimeSum += leg.getTravelTime().seconds();
                    numberOfCarLegs++;
                }
            }
        }

        double averageCarTravelTime = carTravelTimeSum / numberOfCarLegs;
        System.out.println("Average car travel time: " + averageCarTravelTime + " seconds.");
    }
}
