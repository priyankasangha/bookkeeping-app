package model;

import org.json.JSONObject;
import persistence.Writable;

public class Book implements Writable {
    // Represents an account having a title, author, priority (on a scale of 1-5 inclusive) and rating
    // (on a scale of 1-5 inclusive)

    private String title;      // title of the book

    private String authorln;    // last name of author of the book

    private int priority;    // intended for use in "want to read" library, where you can set how urgently you want
    // to read the book. On a scale of [1-5] 1 being low and 5 being high.

    private int rating;     // intended for use in "completed" library. On a scale of [1-5], 5 being excellent,
    // 1 being poor.

    // REQUIRES: TITLE AND AUTHORLN HAVE NON-ZERO LENGTH
    // EFFECTS: TITLE OF BOOK IS SET TO TITLE; AUTHORLN OF BOOK IS SET TO AUTHORLN;
    //          PRIORITY AND RATING INITIALIZE AT 0.
    public Book(String title, String authorln) {
        this.title = title;
        this.authorln = authorln;
        priority = 0;
        rating = 0;
    }

    public String getTitle() {
        return title;
    }


    public String getAuthorln() {
        return authorln;
    }

    public int getRating() {
        return rating;
    }

    public int getPriority() {
        return priority;
    }

    // REQUIRES: p IS IN [1-5]
    // MODIFIES: THIS
    // EFFECTS: SETS PRIORITY TO p
    public void setPriority(int p) {
        priority = p;
    }

    // REQUIRES: r IS IN [1-5]
    // MODIFIES: THIS
    // EFFECTS: SETS RATING TO r
    public void setRating(int r) {
        rating = r;
    }



    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("authorln", authorln);
        json.put("priority", priority);
        json.put("rating", rating);
        return json;
    }
}
