package org.matsim.analysis.solutions.ue5;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

import java.util.Map;
import java.util.TreeMap;

public class Task13CountVehiclesLeavingLinkPerHour {
    private static final Id<Link> LINK_ID = Id.createLinkId("20");

    public static void main(String[] args) {
        MyEventHandler myHandler = new MyEventHandler(LINK_ID);

        EventsManager eventsManager = EventsUtils.createEventsManager();
        eventsManager.addHandler(myHandler);
        EventsUtils.readEvents(eventsManager, "output/output_events.xml.zst");

        System.out.println("Vehicles leaving link " + LINK_ID + " per hour:");

        for (Map.Entry<Integer, Integer> entry : myHandler.getCountsByHour().entrySet()) {
            System.out.println("hour " + entry.getKey() + ": " + entry.getValue());
        }
    }

    static class MyEventHandler implements LinkLeaveEventHandler {
        private final Id<Link> linkId;
        private final Map<Integer, Integer> countsByHour = new TreeMap<>();

        MyEventHandler(Id<Link> linkId) {
            this.linkId = linkId;
        }

        @Override
        public void handleEvent(LinkLeaveEvent event) {
            if (event.getLinkId().equals(linkId)) {
                int hour = (int) (event.getTime() / 3600);
                int oldCount = countsByHour.getOrDefault(hour, 0);
                countsByHour.put(hour, oldCount + 1);
            }
        }

        Map<Integer, Integer> getCountsByHour() {
            return countsByHour;
        }
    }
}
