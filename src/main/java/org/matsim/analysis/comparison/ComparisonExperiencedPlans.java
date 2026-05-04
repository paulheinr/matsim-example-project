package org.matsim.analysis.comparison;

import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.router.TripStructureUtils;

import java.util.List;

public class ComparisonExperiencedPlans {
    static void main() {
        Population basePopulation = PopulationUtils.readPopulation("base-population");
        Population policyPopulation = PopulationUtils.readPopulation("policy-population");

        for (Person basePerson : basePopulation.getPersons().values()) {
            Double baseScore = basePerson.getSelectedPlan().getScore();

            Person policyPerson = policyPopulation.getPersons().get(basePerson.getId());
            Double score = policyPerson.getSelectedPlan().getScore();

            System.out.println("Person " + basePerson.getId() + " had score " + baseScore + " in base scenario and " + score + " in policy scenario.");
        }
    }
}
