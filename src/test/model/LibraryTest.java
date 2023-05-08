package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    Library l;
    Book b1;
    Book b2;
    Book b3;
    Book b4;

    Library l2;

    @BeforeEach
    public void setUp() {
        l = new Library("in progress");
        b1 = new Book("Red Queen", "Aveyard");
        b2 = new Book("Cruel Prince", "Black");
        b3 = new Book("Wicked King", "Black");
        b4 = new Book("Queen of Nothing", "Black");
        l2 = new Library("want to read");
        l2.insert(b1);
        l2.insert(b2);
    }

    @Test
    // HOW TO WRITE TESTS WITH LISTS? WHAT WILL
    // JAVA RECOGNIZE AS EQUAL?
    public void testLibrary() {
        assertEquals(0, l.getSize());
        assertEquals("in progress", l.getName());
    }

    @Test
    public void testInsertOnce() {
        l.insert(b1);
        assertTrue(l.contains(b1));
        assertEquals(1, l.getSize());
        assertEquals(l.getIndex(0), b1);

    }

    @Test
    public void testGetBookTitlesNoBooks(){
        assertEquals(0, l.getBookTitles().size());
    }

    @Test
    public void testGetBookTitlesTwoBooks(){
        assertEquals(2, l2.getBookTitles().size());
        assertEquals("Cruel Prince", l2.getBookTitles().get(0));
        assertEquals("Red Queen", l2.getBookTitles().get(1));
    }

    @Test
    public void testGetBookAuthorsNoAuthors(){
        assertEquals(0, l.getBookTitles().size());
    }

    @Test
    public void testGetBookAuthorsTwoAuthors(){
        assertEquals(2, l2.getBookAuthors().size());
        assertEquals("Black", l2.getBookAuthors().get(0));
        assertEquals("Aveyard", l2.getBookAuthors().get(1));


    }

    @Test
    public void testInsertTwiceSameBook() {
        l.insert(b1);
        assertTrue(l.contains(b1));
        assertEquals(1, l.getSize());
        l.insert(b1);
        assertTrue(l.contains(b1));
        assertEquals(1, l.getSize());
        assertEquals(l.getIndex(0), b1);

    }

    @Test
    public void testInsertTwiceDifferentBook() {
        l.insert(b1);
        assertTrue(l.contains(b1));
        assertEquals(1, l.getSize());
        l.insert(b2);
        assertTrue(l.contains(b2));
        assertEquals(2, l.getSize());
        assertEquals(l.getIndex(1), b1);
        assertEquals(l.getIndex(0), b2);

    }

    @Test
    public void testRemoveEmptyLibrary() {
        assertEquals(0, l.getSize());
        l.remove(b1);
        assertEquals(0, l.getSize());

    }

    @Test
    public void testRemoveOneBook() {
        l.insert(b1);
        l.remove(b1);
        assertEquals(0, l.getSize());

    }


    @Test
    public void testRemoveMultiBooks() {
        l.insert(b1);
        l.insert(b2);
        l.remove(b1);
        assertEquals(1, l.getSize());
        assertTrue(l.contains(b2));
        assertFalse(l.contains(b1));
        l.remove(b2);
        assertEquals(0, l.getSize());
        assertFalse(l.contains(b2));

    }

    @Test
    public void testGetListOfBook() {
        ArrayList<Book> x = l2.getListOfBook();
        assertEquals(x.size(), 2);
        assertEquals(x.get(0), b2);
        assertEquals(x.get(1), b1);
    }


}
