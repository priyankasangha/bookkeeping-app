package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BookTest {

    Book b;

    @BeforeEach
    public void setUp() {
        b = new Book("Red Queen", "Aveyard");
    }

    @Test
    public void testBook() {
        assertEquals("Red Queen", b.getTitle());
        assertEquals("Aveyard", b.getAuthorln());
        assertEquals(0, b.getPriority());
        assertEquals(0, b.getRating());
    }

    @Test
    public void testSetPriorityMax() {
        b.setPriority(5);
        assertEquals(5, b.getPriority());
    }

    @Test
    public void testSetPriorityMin() {
        b.setPriority(1);
        assertEquals(1, b.getPriority());
    }

    @Test
    public void testSetPriorityMid() {
        b.setPriority(3);
        assertEquals(3, b.getPriority());
    }

    @Test
    public void testSetPriorityMulti() {
        b.setPriority(1);
        assertEquals(1, b.getPriority());
        b.setPriority(5);
        assertEquals(5, b.getPriority());
        b.setPriority(3);
        assertEquals(3, b.getPriority());

    }

    @Test
    public void testSetRatingMax() {
        b.setRating(5);
        assertEquals(5, b.getRating());
    }

    @Test
    public void testSetRatingMin() {
        b.setRating(1);
        assertEquals(1, b.getRating());
    }

    @Test
    public void testSetRatingMid() {
        b.setRating(3);
        assertEquals(3, b.getRating());
    }

    @Test
    public void testSetRatingMulti() {
        b.setRating(1);
        assertEquals(1, b.getRating());
        b.setRating(5);
        assertEquals(5, b.getRating());
        b.setRating(3);
        assertEquals(3, b.getRating());
    }
}