package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// BASED ON WORKROOM EXAMPLE FOR CPSC 210

public class JsonReaderTest {

    @Test
    public void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/noFile.json");
        try {
            LibraryDirectory ld = reader.read();
            fail("No exception was thrown");
        } catch (IOException e) {
            // all good
        }
    }


    @Test
    public void testReaderEmptyLibraryDirectory(){
        JsonReader reader = new JsonReader("./data/testReaderEmptyLibraryDirectory.json");
        try {
            LibraryDirectory ld = reader.read();
            assertEquals(0, ld.getCompleted().getSize());
            assertEquals(0, ld.getWantToRead().getSize());
            assertEquals(0, ld.getInProgress().getSize());
        } catch (IOException e) {
            fail("Exception was thrown when it shouldn't be");
        }
    }

    @Test
    public void testReaderLibraryDirectoryNotEmpty(){
        JsonReader reader = new JsonReader("./data/testReaderLibraryDirectoryNotEmpty.json");
        try{
            LibraryDirectory ld = reader.read();
            assertEquals(2, ld.getCompleted().getSize());
            assertEquals(3, ld.getWantToRead().getSize());
            assertEquals(1, ld.getInProgress().getSize());
        } catch (IOException e) {
            fail("Exception was thrown when it shouldn't be");
        }

    }
}