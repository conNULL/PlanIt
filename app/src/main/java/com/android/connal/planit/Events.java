package com.android.connal.planit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* Class to describe event list contents (time, name and details) */

public class Events {

    // Test input array list of event names
    public static List<String> inputEventNames;// = new ArrayList<String>();

    // Array containing all event items
    public static final List<EventItem> eventItems = new ArrayList<EventItem>();

    // Map matching event names to their associated objects
    public static final Map<String, EventItem> eventMap = new HashMap<String, EventItem>();

    public static void initialize(String[] things){

        inputEventNames = new ArrayList<String>();
        for(int i=0; i < 10; i++) {
            inputEventNames.add(things[i]);
        }

        Iterator<String> eventNamesIt = inputEventNames.iterator();

        int currentEventHour = 10;
        String currentEventMinute1 = "0";
        String currentEventMinute2 = "0";

        while (eventNamesIt.hasNext()) {
            String currentEventName = eventNamesIt.next();
            String currentEventDetails = "";
            String currentEventTime = Integer.toString(currentEventHour) + ":" + currentEventMinute1 + currentEventMinute2;
            EventItem currentEventObj = createEventItem(currentEventTime, currentEventName, currentEventDetails);
            addItem(currentEventObj);
            currentEventHour++;
        }
    }


   // static {


        /*
        Iterator<EventItem> eventItemsIt = inputEventItems.iterator();

        // Iterate through all scheduled event times and add their information
        while (eventItemsIt.hasNext()) {

            // No notion of "current" with Java iterators
            EventItem currentEventItem = eventItemsIt.next();
            addItem(currentEventItem);
        }
        */
  //  }

    private static void addItem(EventItem item) {
        eventItems.add(item);
        eventMap.put(item.eventTime, item);
    }

    private static EventItem createEventItem(String eventTime, String eventName, String eventDetails) {
        return new EventItem(eventTime, eventName, makeDetails(eventTime, eventName, eventDetails));
    }

    private static String makeDetails(String eventTime, String eventName, String eventDetails) {
        StringBuilder builder = new StringBuilder();
        builder.append("Time: ").append(eventTime).append("\n\n").append(eventDetails);
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class EventItem {
        public final String eventTime;
        public final String eventName;
        public final String eventDetails;

        public EventItem(String eventTime, String eventName, String eventDetails) {
            this.eventTime = eventTime;
            this.eventName = eventName;
            this.eventDetails = eventDetails;
        }

        @Override
        public String toString() {
            return eventName;
        }
    }
}
