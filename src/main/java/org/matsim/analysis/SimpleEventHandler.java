package org.matsim.analysis;

import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

public class SimpleEventHandler {
    static void main() {
        EventsManager eventsManager = EventsUtils.createEventsManager();
        MyEventHandler myHandler = new MyEventHandler();
        eventsManager.addHandler(myHandler);
        EventsUtils.readEvents(eventsManager, "output/output_events.xml.zst");
    }

    static class MyEventHandler implements LinkEnterEventHandler {
        @Override
        public void handleEvent(LinkEnterEvent event) {
            System.out.println("Vehicle " + event.getVehicleId() + " entered link " + event.getLinkId() + " at time " + event.getTime());
        }
    }
}
