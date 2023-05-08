package persistence;

import model.Book;
import model.Library;
import model.LibraryDirectory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// REPRESENTS A READER THAT READS JSON DATA FROM THE FILE AND RETURNS A LIBRARY DIRECTORY
// BASED ON WORKROOM EXAMPLE FOR CPSC 210

public class JsonReader {

    private String source;

    // EFFECTS: CREATES A READER TO READ FROM FILE
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: READS LIBRARY FROM FILE AND RETURNS THE LIBRARY
    // THROWS IOEXCEPTION IF AN ERROR OCCURS WHEN READING FILE'S DATA
    public LibraryDirectory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return analyzeLibraryDirectory(jsonObject);
    }

    // EFFECTS: READS SOURCE FILE AS A STRING AND RETURNS IT
    public String readFile(String source) throws IOException {
        StringBuilder b = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> b.append(s));
        }
        return b.toString();
    }

    // EFFECTS: PARSES LIBRARY DIRECTORY FORM JSON OBJECT AND RETURNS IT AS A LIBRARY DIRECTORY
    public LibraryDirectory analyzeLibraryDirectory(JSONObject j) {
        JSONObject o1 = j.getJSONObject("wantToRead");
        JSONObject o2 = j.getJSONObject("inProgress");
        JSONObject o3 = j.getJSONObject("completed");
        LibraryDirectory ld;
        Library wantToRead = new Library("Want To Read");
        wantToRead = jsonObjectToLibrary(o1, wantToRead);
        wantToRead.sortWantToRead();
        Library inProgress = new Library("In Progress");
        inProgress = jsonObjectToLibrary(o2, inProgress);
        Library completed = new Library("Completed");
        completed = jsonObjectToLibrary(o3, completed);
        ld = new LibraryDirectory(wantToRead, inProgress, completed);
        return ld;
    }

    // MODIFIES: l
    // EFFECTS: PARSES BOOKS FROM JSON ARRAY AND ADDS THEM TO THE LIBRARY
    public Library jsonObjectToLibrary(JSONObject j, Library l) {
        JSONArray jsonArray = j.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(l, nextBook);
        }
        return l;
    }

    // MODIFIES: l
    // EFFECTS: PARSES BOOK FROM JSON OBJECT AND ADDS IT TO THE LIBRARY
    public void addBook(Library l, JSONObject j) {
        String title = j.getString("title");
        String authorln = j.getString("authorln");
        int priority = j.getInt("priority");
        int rating = j.getInt("rating");
        Book b = new Book(title, authorln);
        b.setPriority(priority);
        b.setRating(rating);
        l.insert(b);
    }
}
