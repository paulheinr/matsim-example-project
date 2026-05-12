package org.matsim.analysis.solutions.ue5;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

public class Task12PrintActivityLocationsOfAgent {
    public static void main(String[] args) {
        MyEventHandler myHandler = new MyEventHandler(Id.createPersonId("4"));

        EventsManager eventsManager = EventsUtils.createEventsManager();
        eventsManager.addHandler(myHandler);
        EventsUtils.readEvents(eventsManager, "output/output_events.xml.zst");
    }

    static class MyEventHandler implements ActivityEndEventHandler {
        private final Id<Person> personId;

        MyEventHandler(Id<Person> personId) {
            this.personId = personId;
        }

        @Override
        public void handleEvent(ActivityEndEvent event) {
            if (event.getPersonId().equals(personId)) {
                String location = "link " + event.getLinkId();

                Coord coord = event.getCoord();
                if (coord != null) {
                    location += ", coord (" + coord.getX() + ", " + coord.getY() + ")";
                }

                System.out.println("Person " + personId + " ended activity "
                        + event.getActType() + " at " + location + ".");
            }
        }
    }
}
