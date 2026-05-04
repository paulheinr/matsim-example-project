package org.matsim.analysis.comparison;

import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.handler.EventHandler;

public class ComparisonEventHandler {
    static void main() {
        MyEventHandler baseHandler = new MyEventHandler();
        readEvents(baseHandler, "base-path");

        MyEventHandler policyHandler = new MyEventHandler();
        readEvents(policyHandler, "policy-path");

        System.out.println("Base scenario had " + baseHandler.getEventCount() + " link enter events.");
        System.out.println("Policy scenario had " + policyHandler.getEventCount() + " link enter events.");
    }

    static void readEvents(EventHandler handler, String path) {
        EventsManager baseEventsManager = EventsUtils.createEventsManager();
        baseEventsManager.addHandler(handler);
        EventsUtils.readEvents(baseEventsManager, path);
    }

    static class MyEventHandler implements LinkEnterEventHandler {
        private int eventCount = 0;

        @Override
        public void handleEvent(LinkEnterEvent event) {
            eventCount++;
        }

        public int getEventCount() {
            return eventCount;
        }
    }
}
