package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// BASED ON WORKROOM EXAMPLE FOR CPSC 210

public class JsonWriterTest {

    Library l1;
    Library l2;
    Library l3;
    Book b1;
    Book b2;
    Book b3;
    Book b4;

    @BeforeEach
    public void setUpFields() {
        l1 = new Library("Want To Read");
        l2 = new Library("In Progress");
        l3 = new Library("Completed");
        b1 = new Book("b1", "b");
        b2 = new Book("b2", "b");
        b3 = new Book("b3", "b");
        b4 = new Book("b4", "b");

    }


    @Test
    public void testWriterInValidFile() {
        try {
            l1.insert(b1);
            l2.insert(b2);
            l3.insert(b3);
            l1.insert(b4);
            LibraryDirectory ld = new LibraryDirectory(l1, l2, l3);
            JsonWriter writer = new JsonWriter("");
            writer.open();
            fail("Exception was not thrown");
        } catch (IOException e) {
            // all good

        }
    }

    @Test
    public void testWriterEmptyLibraryDirectory() {
        try {
            LibraryDirectory ld = new LibraryDirectory(l1, l2, l3);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLibraryDirectory.json");
            writer.open();
            writer.write(ld);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLibraryDirectory.json");
            ld = reader.read();
            assertEquals(0, ld.getInProgress().getSize());
            assertEquals(0, ld.getCompleted().getSize());
            assertEquals(0, ld.getWantToRead().getSize());
            assertEquals("Want To Read", ld.getWantToRead().getName());
            assertEquals("Completed", ld.getCompleted().getName());
            assertEquals("In Progress", ld.getInProgress().getName());
        } catch (IOException e) {
            fail("Exception was thrown when it shouldn't have");
        }
    }

    @Test
    public void testWriterLibraryDirectoryNotEmpty() {
        try {
            l1.insert(b1);
            l2.insert(b2);
            l3.insert(b3);
            l1.insert(b4);
            LibraryDirectory ld = new LibraryDirectory(l1, l2, l3);
            JsonWriter writer = new JsonWriter("./data/testWriterLibraryDirectoryNotEmpty.json");
            writer.open();
            writer.write(ld);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterLibraryDirectoryNotEmpty.json");
            ld = reader.read();
            assertEquals(2, ld.getWantToRead().getSize());
            assertEquals(1, ld.getInProgress().getSize());
            assertEquals(1, ld.getCompleted().getSize());
        } catch (IOException e) {
            fail("Exception was thown when it shouldn't have been");
        }
    }
}
