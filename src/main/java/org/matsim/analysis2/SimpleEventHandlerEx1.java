package org.matsim.analysis2;

import org.matsim.api.core.v01.events.LinkLeaveEvent;
import org.matsim.api.core.v01.events.handler.LinkLeaveEventHandler;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;

public class SimpleEventHandlerEx1 {
    static void main() {
        EventsManager eventsManager = EventsUtils.createEventsManager();

        SimpleLeaveCounter handler = new SimpleLeaveCounter();
        eventsManager.addHandler(handler);
        eventsManager.addHandler(new EventsHandlerEx2());

        int countBefore = handler.getCounter();

        EventsUtils.readEvents(eventsManager, "output/output_events.xml.zst");

        int countAfter = handler.getCounter();

        System.out.println("Count before: " + countBefore);
        System.out.println("Count after: " + countAfter);
    }

    public static class SimpleLeaveCounter implements LinkLeaveEventHandler {
        private int counter = 0;

        @Override
        public void handleEvent(LinkLeaveEvent event) {
    //        System.out.println("Counter status: " + counter);
            counter++;
        }

        public int getCounter(){
            return counter;
        }
    }
}
