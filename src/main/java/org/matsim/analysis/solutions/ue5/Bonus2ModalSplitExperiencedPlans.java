package org.matsim.analysis.solutions.ue5;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.router.TripStructureUtils;

import java.util.Map;
import java.util.TreeMap;

public class Bonus2ModalSplitExperiencedPlans {
    public static void main(String[] args) {
        Population population = PopulationUtils.readPopulation("output/output_experienced_plans.xml");

        Map<String, Integer> tripsByMode = new TreeMap<>();
        int totalTrips = 0;

        for (Person person : population.getPersons().values()) {
            Plan selectedPlan = person.getSelectedPlan();

            for (TripStructureUtils.Trip trip : TripStructureUtils.getTrips(selectedPlan)) {
                String mode = TripStructureUtils.identifyMainMode(trip.getTripElements());

                int oldCount = tripsByMode.getOrDefault(mode, 0);
                tripsByMode.put(mode, oldCount + 1);
                totalTrips++;
            }
        }

        System.out.println("Modal split:");

        for (Map.Entry<String, Integer> entry : tripsByMode.entrySet()) {
            double share = (double) entry.getValue() / totalTrips;
            System.out.println(entry.getKey() + ": " + entry.getValue()
                    + " trips, share " + share);
        }
    }
}
