package org.matsim.analysis.solutions.ue5;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

public class Task11CountVehiclesLeavingLink {
    private static final Id<Link> LINK_ID = Id.createLinkId("10");

    public static void main(String[] args) {
        MyEventHandler myHandler = new MyEventHandler(LINK_ID);

        EventsManager eventsManager = EventsUtils.createEventsManager();
        eventsManager.addHandler(myHandler);
        EventsUtils.readEvents(eventsManager, "output/output_events.xml.zst");

        System.out.println(myHandler.getCount() + " vehicles left link " + LINK_ID + ".");
    }

    static class MyEventHandler implements LinkLeaveEventHandler {
        private final Id<Link> linkId;
        private int count = 0;

        MyEventHandler(Id<Link> linkId) {
            this.linkId = linkId;
        }

        @Override
        public void handleEvent(LinkLeaveEvent event) {
            if (event.getLinkId().equals(linkId)) {
                count++;
            }
        }

        int getCount() {
            return count;
        }
    }
}
