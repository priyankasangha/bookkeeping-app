package persistence;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.LibraryDirectory;
import org.json.JSONObject;

// REPRESENTS A WRITER THAT WRITES JSON REPRESENTATION OF LIBRARY DIRECTORY TO FILE
// BASED ON WORKROOM EXAMPLE FOR CPSC 210


public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String location;


    // EFFECTS: CONSTRUCTS A WRITER TO WRITE TO LOCATION FILE
    public JsonWriter(String location) {
        this.location = location;
    }

    // MODIFIES: THIS
    // EFFECTS: OPENS THE WRITER; THROWS FILENOTFOUNDEXCEPTION IF LOCATION FOR FILE
    // CAN'T BE OPENED FOR WRITING
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(location));
    }

    // MODIFIES: THIS
    // EFFECTS: WRITES JSON REPRESENTATION OF LIBRARY DIRECTORY TO FILE
    public void write(LibraryDirectory l) {
        JSONObject json = l.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: THIS
    // EFFECTS: CLOSES THE WRITER
    public void close() {
        writer.close();
    }


    // MODIFIES: THIS
    // EFFECTS: WRITES THE STRINGS TO THE FILE
    private void saveToFile(String json) {
        writer.print(json);
    }


}
