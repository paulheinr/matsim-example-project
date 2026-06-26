package org.matsim.analysis2;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;

public class EventsHandlerEx2 implements ActivityEndEventHandler {
    private Id<Person> referencePerson = Id.createPersonId("1");

    @Override
    public void handleEvent(ActivityEndEvent event) {
        if (event.getPersonId().equals(referencePerson)) {
            System.out.println(event.getCoord());
        }
    }
}
