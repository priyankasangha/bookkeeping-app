package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;


// REPRESENTS A LIST OF BOOKS
public class Library implements Writable {
    ArrayList<Book> listOfBook; // ARRAYLIST OF BOOKS
    String name;

    // EFFECTS: CREATES AN ARRAYLIST OF TYPE BOOK WITH 0 BOOKS
    public Library(String name) {
        listOfBook = new ArrayList<Book>();
        this.name = name;
    }

    public int getSize() {
        return listOfBook.size();
    }

    public ArrayList<Book> getListOfBook() {
        return listOfBook;
    }

    // EFFECTS: RETURNS TRUE IF THE BOOK IS IN THE LIST
    public Boolean contains(Book b) {
        return listOfBook.contains(b);
    }

    public String getName() {
        return name;
    }


    // MODIFIES: THIS
    // EFFECTS: SETS LOCAL VARIABLE LISTOFBOOK AS FIELD VALUE LISTOFBOOK
    public void setListOfBook(ArrayList<Book> listOfBook) {
        this.listOfBook = listOfBook;
    }

    // MODIFIES: THIS
    // EFFECTS: IF BOOK ISN'T ALREADY IN LIST, IT ADDS IT AND THEN SORTS THE LIST BASED OFF BOOK PRIORITY
    // (HIGHER PRIORITY AT THE TOP). IF BOOK IS IN LIST, DO NOTHING.
    public void insert(Book b) {
        if (!listOfBook.contains(b)) {
            listOfBook.add(b);
            Collections.sort(listOfBook, new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getPriority() - o2.getPriority(); // sort based on priority
                }
            });
            Collections.reverse(listOfBook); // reverses list so higher priority is at the top
            EventLog.getInstance().logEvent(new Event("Added Book: " + b.getTitle()
                    + " by " + b.getAuthorln() + " to " + name));


        }
    }

    // MODIFIES: THIS
    // EFFECTS: SORTS THE WANT TO READ LIBRARY BASED ON PRIORITY, HIGHER PRIORITY AT THE TOP
    public Library sortWantToRead() {

        Collections.sort(listOfBook, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o2.getPriority() - o1.getPriority();
            }
        });
        Library l = new Library("Want To Read");
        l.setListOfBook(listOfBook);
        return l;

    }

    public ArrayList<String> getBookTitles() {
        ArrayList<String> listOfTitles = new ArrayList<>();
        for (Book b : listOfBook) {
            listOfTitles.add(b.getTitle());
        }
        return listOfTitles;
    }

    public ArrayList<String> getBookAuthors() {
        ArrayList<String> listOfAuthors = new ArrayList<>();
        for (Book b : listOfBook) {
            listOfAuthors.add(b.getAuthorln());
        }
        return listOfAuthors;
    }


    // MODIFIES: THIS
    // EFFECTS: IF BOOK IS IN LIST, IT REMOVES IT. IF NOT, DO NOTHING.
    public void remove(Book b) {
        Book x = null;
        for (Book element : listOfBook) {
            if (Objects.equals(element.getTitle(), b.getTitle())) {
                x = element;
            }
        }
        listOfBook.remove(x);
        EventLog.getInstance().logEvent(new Event("Removed Book: " + x.getTitle()
                + " by " + x.getAuthorln() + " from " + name));
    }


    public Book getIndex(int x) {
        return listOfBook.get(x);
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", listOfBookToJson());
        return json;
    }


    private JSONArray listOfBookToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Book b : listOfBook) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }


}
