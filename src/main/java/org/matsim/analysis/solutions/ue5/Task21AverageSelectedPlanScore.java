package org.matsim.analysis.solutions.ue5;

import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.population.PopulationUtils;

public class Task21AverageSelectedPlanScore {
    public static void main(String[] args) {
        Population population = PopulationUtils.readPopulation("output/output_experienced_plans.xml");

        double scoreSum = 0.0;
        int numberOfPlansWithScore = 0;

        for (Person person : population.getPersons().values()) {
            Double score = person.getSelectedPlan().getScore();

            if (score != null) {
                scoreSum += score;
                numberOfPlansWithScore++;
            }
        }

        double averageScore = scoreSum / numberOfPlansWithScore;
        System.out.println("Average selected plan score: " + averageScore);
    }
}
