package com.android.connal.planit.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by time of occurence
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static final int COUNT = 12;

    static {
        int hourDigit = 9;
        int minuteDigit1 = 0;
        int minuteDigit2 = 0;

        String eventName = "This is an event";
        String eventDetails = "Details... yeah";

        // Add scheduled activities
        for (int i = 1; i <= COUNT; i++) {
            String time = hourDigit + ":" + minuteDigit1 + minuteDigit2;
            addItem(createDummyItem(time, eventName, eventDetails));
            hourDigit ++;
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.time, item);
    }

    private static DummyItem createDummyItem(String time, String eventName, String eventDetails) {
        return new DummyItem(time, eventName, makeDetails(time, eventName, eventDetails));
    }

    private static String makeDetails(String time, String eventName, String eventDetails) {
        StringBuilder builder = new StringBuilder();
        builder.append("Time: ").append(time).append("\n\n").append(eventDetails);
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String time;
        public final String eventName;
        public final String eventDetails;

        public DummyItem(String time, String eventName, String eventDetails) {
            this.time = time;
            this.eventName = eventName;
            this.eventDetails = eventDetails;
        }

        @Override
        public String toString() {
            return eventName;
        }
    }
}
