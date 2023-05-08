package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

// all tests for Event class
// test cases created w help of CPSC 210 alarm system example project
public class EventTest {

    private Event e;

    private Date d;

    @BeforeEach
    public void setUp(){
        e = new Event("Book added to Library");
        d = Calendar.getInstance().getTime();
    }

    @Test
    public void testEvent(){
        assertEquals("Book added to Library", e.getDescription());
        assertEquals(d, e.getDate());
    }

    @Test
    public void testToString(){
        assertEquals(d.toString() + "\n" + "Book added to Library", e.toString());
    }
}
