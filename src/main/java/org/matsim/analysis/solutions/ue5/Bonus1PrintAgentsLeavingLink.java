package org.matsim.analysis.solutions.ue5;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.PersonEntersVehicleEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.events.handler.PersonEntersVehicleEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.vehicles.Vehicle;

import java.util.LinkedHashMap;
import java.util.Map;

public class Bonus1PrintAgentsLeavingLink {
    public static void main(String[] args) {
        MyEventHandler myHandler = new MyEventHandler(Id.createLinkId("1"));

        EventsManager eventsManager = EventsUtils.createEventsManager();
        eventsManager.addHandler(myHandler);
        EventsUtils.readEvents(eventsManager, "output/output_events.xml.zst");
    }

    static class MyEventHandler implements LinkLeaveEventHandler, PersonEntersVehicleEventHandler {
        private final Id<Link> linkId;
        private final Map<Id<Vehicle>, Id<Person>> personByVehicleId = new LinkedHashMap<>();

        MyEventHandler(Id<Link> linkId) {
            this.linkId = linkId;
        }

        @Override
        public void handleEvent(PersonEntersVehicleEvent event) {
            personByVehicleId.put(event.getVehicleId(), event.getPersonId());
        }

        @Override
        public void handleEvent(LinkLeaveEvent event) {
            if (event.getLinkId().equals(linkId)) {
                Id<Person> personId = personByVehicleId.get(event.getVehicleId());

                System.out.println("Vehicle " + event.getVehicleId()
                        + " with person " + personId + " left link " + linkId + ".");
            }
        }
    }
}
