package org.matsim.analysis;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.router.TripStructureUtils;

import java.util.List;

public class SimpleExperiencedPlans {
    static void main() {
        Population population = PopulationUtils.readPopulation("output/output_experienced_plans.xml");

        for (Person person : population.getPersons().values()) {
            List<Activity> activities = TripStructureUtils.getActivities(person.getSelectedPlan(), TripStructureUtils.StageActivityHandling.StagesAsNormalActivities);
            List<Leg> legs = TripStructureUtils.getLegs(person.getSelectedPlan());
            System.out.println("Person " + person.getId() + " has " + activities.size() + " activities and " + legs.size() + " legs in their experienced plan.");
        }
    }
}
