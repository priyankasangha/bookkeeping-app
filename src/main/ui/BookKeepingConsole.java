package ui;

import model.Library;
import model.Book;
import model.LibraryDirectory;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// BOOKKEEPING APPLICATION
public class BookKeepingConsole {

    private LibraryDirectory allLibraries;

    private Scanner input;
    private static final String JSON_STORE = "./data/library.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;




    // EFFECTS: RUNS THE BOOKKEEPING APP
    public BookKeepingConsole() {
        initialize();
        runApp();
    }

    // MODIFIES: THIS
    // EFFECTS: PROCESSES/COMPUTES THE USER'S INPUT
    private void runApp() {
        boolean keepGoing = true;
        String command = null;
        displayMenu();

        while (keepGoing) {
            command = input.next();
            if (command.equals("EX")) {
                keepGoing = false;
            } else {
                computeMainMenuCommand(command);
            }

        }
        System.out.println("have a good day:)");

    }

    // EFFECTS: DISPLAYS MENU OF MAIN OPTIONS TO USER
    public void displayMenu() {
        System.out.println("\nWould you like to:");
        System.out.println("\n1) View a library");
        System.out.println("\n2) Add a book to a library");
        System.out.println("\n3) Remove a book from a library");
        System.out.println("\n4) Save all libraries");
        System.out.println("\n5) Load previous libraries");
        System.out.println("\nEX) Exit");

    }

    // MODIFIES: THIS
    // EFFECTS: PROCESS USER COMMAND
    public void computeMainMenuCommand(String command) {
        if (command.equals("1")) {
            viewLibrary(command);
        } else if (command.equals("2")) {
            addBook(command);
        } else if (command.equals("3")) {
            removeBook(command);
        } else if (command.equals("4")) {
            saveLibraryDirectory();
        } else if (command.equals("5")) {
            loadLibraryDirectory();
        } else {
            System.out.println("oh no you didn't select a valid option");
        }

    }

    // EFFECTS: SAVES THE LIBRARY DIRECTORY TO FILE
    private void saveLibraryDirectory() {
        try {
            jsonWriter.open();
            jsonWriter.write(allLibraries);
            jsonWriter.close();
            System.out.println("You have saved all your libraries to " + JSON_STORE);
            carryOutBackToMainMenu();
        } catch (FileNotFoundException f) {
            System.out.println("oh no:( we couldn't save your library to " + JSON_STORE);
        }
    }

    // MODIFIES: THIS
    // EFFECTS: LOADS THE LIBRARY DIRECTORY FROM FILE
    private void loadLibraryDirectory() {
        try {
            allLibraries = jsonReader.read();
            System.out.println("loaded your library from " + JSON_STORE);
            carryOutBackToMainMenu();
        } catch (IOException f) {
            System.out.println("oh no :( we couldn't load your library");
        }
    }

    // MODIFIES: THIS
    // EFFECTS: PROCESS USER COMMAND TO REMOVE BOOK FROM SPECIFIC LIBRARY
    public void removeBook(String command) {
        System.out.println("What is the title of the book you would like to remove");
        String x1 = input.next();
        System.out.println("Please enter the author's last name");
        String x2 = input.next();
        Book b = new Book(x1, x2);
        removeBookLibraryMenu();
        String input1 = input.next();
        if (input1.equals("J")) {
            removeWantToRead(b);
        } else if (input1.equals("K")) {
            removeInProgress(b);
        } else if (input1.equals("L")) {
            removeCompleted(b);
        }
        carryOutBackToMainMenu();
    }

    // MODIFIES: THIS
    // EFFECTS: REMOVES BOOK FROM WANTTOREAD LIBRARY
    public void removeWantToRead(Book b) {
        allLibraries.getWantToRead().remove(b);
        System.out.print("You have removed ");
        printBook(b);
        System.out.println(" from the want to read library");

    }

    // MODIFIES: THIS
    // EFFECTS: REMOVES BOOK FROM INPROGRESS LIBRARY
    public void removeInProgress(Book b) {
        allLibraries.getInProgress().remove(b);
        System.out.print("You have removed ");
        printBook(b);
        System.out.println(" from the in Progress library");
    }

    // MODIFIES: THIS
    // EFFECTS: REMOVES BOOK FROM COMPLETED LIBRARY
    public void removeCompleted(Book b) {
        allLibraries.getCompleted().remove(b);
        System.out.print("You have removed ");
        printBook(b);
        System.out.println(" from the Completed library");
    }

    // EFFECTS: DISPLAYS REMOVE BOOK MENU
    public void removeBookLibraryMenu() {
        System.out.println("Please select which library you would like to remove the book from:");
        System.out.println("J) Want to Read");
        System.out.println("K) in Progress");
        System.out.println("L) Completed");
    }


    // MODIFIES: THIS
    // EFFECTS: PROCESSES USER COMMAND
    public void viewLibrary(String command) {
        viewLibraryMenu(command);
        command = input.next();
        if (command.equals("D")) {
            printWantToReadLibrary();
        } else if (command.equals("E")) {
            printInProgressLibrary();
        } else if (command.equals("F")) {
            printCompleted();
        }
        carryOutBackToMainMenu();


    }

    // EFFECTS: DISPLAYS MENU OF LIBRARIES TO VIEW
    public void viewLibraryMenu(String command) {
        System.out.println("Which library would you like to view?");
        System.out.println("D) Want to Read");
        System.out.println("E) in Progress");
        System.out.println("F) Completed");
    }

    // MODIFIES: THIS
    // EFFECTS: ADDS BOOK TO SPECIFIC LIBRARY
    public void addBook(String command) {
        System.out.println("What is the title of the book?");
        String x1 = input.next();
        System.out.println("What is the author's last name?");
        String x2 = input.next();
        Book b = new Book(x1, x2);
        displayWhichLibraryToAddMenu(b);
        carryOutWhichLibraryToAdd(b, command);

    }

    // MODIFIES: THIS
    // EFFECTS: PROCESS USER COMMAND
    public void carryOutWhichLibraryToAdd(Book b, String command) {
        command = input.next();
        if (command.equals("G")) {
            displayPriority();
            b.setPriority(Integer.parseInt(input.next()));
            allLibraries.getWantToRead().insert(b);
            System.out.println("You have added ");
            printBook(b);
            System.out.println("To the want to read library at priority level " + b.getPriority());
            carryOutBackToMainMenu();
        } else if (command.equals("H")) {
            allLibraries.getInProgress().insert(b);
            carryOutInProgress(b);
        } else if (command.equals("I")) {
            displayRating(b);
            b.setRating(Integer.parseInt(input.next()));
            System.out.println("You have added ");
            printBook(b);
            System.out.print("To the completed library at rating " + b.getRating());
            allLibraries.getCompleted().insert(b);
            carryOutBackToMainMenu();
        }

    }

    // EFFECTS: DISPLAYS THAT BOOK HAS BEEN ADDED TO LIBRARY
    public void carryOutInProgress(Book b) {
        System.out.println("You have added ");
        printBook(b);
        System.out.println("To the in progress library.");
        carryOutBackToMainMenu();
    }

    // MODIFIES: THIS
    // EFFECTS: INITIALIZES LIBRARY DIRECTORIES

    public void initialize() {
        Library wantToRead = new Library("Want To Read");
        Library inProgress = new Library("In Progress");
        Library completed = new Library("Completed");
        allLibraries = new LibraryDirectory(wantToRead, inProgress, completed);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: DISPLAYS MENU OF OPTIONS OF LIBRARIES TO ADD BOOK TO
    public void displayWhichLibraryToAddMenu(Book b) {
        System.out.println("Which library would you like to add");
        printBook(b);
        System.out.println("to?");
        System.out.println("G) Want To Read");
        System.out.println("H) in Progress");
        System.out.println("I) Completed");

    }

    // MODIFIES: THIS
    // EFFECTS: PROMPTS USER TO RETURN TO MAIN MENU AND EXECUTES RETURN TO MAIN MENU
    public void carryOutBackToMainMenu() {
        System.out.println(" Please Press Z to return to Main Menu");
        if (input.next().equals("Z")) {
            runApp();
        } else {
            carryOutBackToMainMenu();
        }
    }


    // EFFECTS: DISPLAYS RATING OPTIONS
    public void displayRating(Book b) {
        System.out.println("What would you like to rate this book? 1 being poor, 5 being excellent.");
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");

    }

    // EFFECTS: DISPLAYS PRIORITY OPTIONS
    public void displayPriority() {
        System.out.println("At what priority would you like to place this book? With 1 being low and 5 being high.");
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
    }

    // EFFECTS: PRINTS BOOK TITLE AND AUTHOR
    public void printBook(Book b) {
        System.out.println(b.getTitle() + " by " + b.getAuthorln());
    }

    // EFFECTS: PRINTS ALL THE TITLES AND AUTHORS OF BOOKS IN THE IN PROGRESS LIBRARY
    public void printInProgressLibrary() {
        for (Book b : allLibraries.getInProgress().getListOfBook()) {
            System.out.println(b.getTitle() + " by " + b.getAuthorln());
        }
    }

    //EFFECTS: PRINTS ALL THE TITLES AND AUTHORS OF BOOKS IN THE WANT TO READ LIBRARY
    public void printWantToReadLibrary() {
        for (Book b : allLibraries.getWantToRead().getListOfBook()) {
            System.out.println(b.getTitle() + " by " + b.getAuthorln());
        }

    }

    // EFFECTS: PRINTS ALL THE TITLES AND AUTHORS OF THE BOOKS IN THE COMPLETED LIBRARY
    public void printCompleted() {
        for (Book b : allLibraries.getCompleted().getListOfBook()) {
            System.out.println(b.getTitle() + " by " + b.getAuthorln());
        }
    }


}




