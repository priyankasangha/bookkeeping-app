package model;

// tests for the EventLog class
// test cases created w help of CPSC 210 alarm system example project

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {

    private Event e;
    private Event e1;
    private Event e2;

    @BeforeEach
    public void setUp(){
        e = new Event("event");
        e1 = new Event("event2");
        e2 = new Event("event3");
        EventLog log = EventLog.getInstance();
        log.logEvent(e);
        log.logEvent(e1);
        log.logEvent(e2);
    }

    @Test
    public void testLogEvent(){
        List<Event> l = new ArrayList<Event>();
        EventLog log = EventLog.getInstance();
        for (Event e : log){
            l.add(e);
        }

        assertEquals(3, l.size());
        assertTrue(l.contains(e));
        assertTrue(l.contains(e1));
        assertTrue(l.contains(e2));
    }


    @Test
    public void testClear(){
        EventLog log = EventLog.getInstance();
        log.clear();
        Iterator<Event> iterator = log.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("Event log cleared.", iterator.next().getDescription());
        assertFalse(iterator.hasNext());

    }
}
