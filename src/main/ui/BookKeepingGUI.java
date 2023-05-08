package ui;


import model.Book;
import model.Event;
import model.EventLog;
import model.Library;
import model.LibraryDirectory;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


import static java.awt.Font.BOLD;


// REPRESENTS BOOKKEEPING APPLICATION'S PRIMARY WINDOW FRAME

public class BookKeepingGUI extends JFrame {

    private JInternalFrame wantToReadFrame = new JInternalFrame("WANT TO READ LIBRARY:)");
    private JInternalFrame inProgressFrame = new JInternalFrame("IN PROGRESS LIBRARY :)");
    private JInternalFrame completedFrame = new JInternalFrame("COMPLETED LIBRARY :)");

    private JButton j1;
    private JButton j2;
    private JButton j3;

    private JDesktopPane desktop;
    private JInternalFrame libraryOptions;
    private static final int WIDTH = 3000;
    private static final int HEIGHT = 2000;
    private LibraryDirectory allLibraries;

    private static final String JSON_STORE = "./data/library.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // MODIFIES: THIS
    // EFFECTS: INITIALIZES ALL FIELDS, BUTTONS AND PANELS, AND KEEPS GUI RUNNING
    public BookKeepingGUI() {
        initializeAllBackgroundAndButtons();
        initializeLibraries();
    }

    // MODIFIES: THIS
    // EFFECTS: INITIALIZES LIBRARIES AND JSON WRITER/READER
    public void initializeLibraries() {
        Library wantToRead = new Library("Want To Read");
        Library inProgress = new Library("In Progress");
        Library completed = new Library("Completed");
        allLibraries = new LibraryDirectory(wantToRead, inProgress, completed);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: THIS
    // EFFECTS: SETS UP BACKGROUND COLOR, LIBRARY VIEWING OPTIONS, AND BUTTONS
    public void initializeAllBackgroundAndButtons() {
        desktop = new JDesktopPane();
        desktop.setBackground(new Color(203, 195, 227));
        setContentPane(desktop);
        setTitle("Bookkeeping");
        setSize(WIDTH, HEIGHT);
        desktop.addMouseListener(new DesktopAction());

        libraryOptions = new JInternalFrame("Select A Library To View, Bestie!", false, false,
                false, false);
        setUpLibraryOptions();
        makeMainLabel();


        addLibraryList();
        addMenu();
        libraryOptions.setVisible(true);
        desktop.add(libraryOptions);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                printLog();


            }
        });
        centreOnScreen();
        setVisible(true);


    }

    // MODIFIES: THIS
    // EFFECTS: PRINTS THE EVENT LOG TO THE CONSOLE
    public void printLog() {
        for (Event e : EventLog.getInstance()) {
            System.out.println(e);
        }
        System.exit(0);

    }

    // MODIFIES: THIS
    // EFFECTS: CREATES THE MAIN TEXT IN THE TOP LEFT CORNER OF THE APP
    public void makeMainLabel() {
        JLabel label = new JLabel();
        label.setText("BookKeeping: intended for use by certified icons only");
        label.setBounds(20, 1, 2000, 45);
        label.setFont(new Font("idk", BOLD, 30));
        label.setForeground(Color.BLACK);
        label.setVisible(true);
        desktop.add(label);

    }


    // MODIFIES: THIS
    // EFFECTS: SPECIFIES LAYOUT INFORMATION ABOUT THE LIBRARY OPTIONS PANEL
    public void setUpLibraryOptions() {
        libraryOptions.setSize(500, 100);
        libraryOptions.setLayout(new BorderLayout());
        libraryOptions.setLocation(950, 0);
    }


    // EFFECTS: CENTERS COMPONENTS IN TEH CENTER OF THE SCREEN
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    // MODIFIES: THIS
    // EFFECTS: SETS UP BUTTONS TO VIEW DIFFERENT LIBRARIES
    private void addLibraryList() {
        JPanel button = new JPanel();
        button.setBackground(new Color(162, 138, 210));
        j1 = new JButton(new ViewWantToReadAction());
        j2 = new JButton(new ViewInProgressAction());
        j3 = new JButton(new ViewCompletedAction());
        button.add(j1);
        button.add(j2);
        button.add(j3);
        libraryOptions.add(button);

    }


    // MODIFIES: THIS
    // EFFECTS: CREATES MENU OPTIONS AT THE TOP LEFT OF THE APP
    public void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu editLibrariesMenu = new JMenu("Edit Library");
        addMenuItem(editLibrariesMenu, new AddBookAction());
        addMenuItem(editLibrariesMenu, new RemoveBookAction());
        menuBar.add(editLibrariesMenu);

        JMenu saveLibrariesMenu = new JMenu("Save");
        addMenuItem(saveLibrariesMenu, new SaveLibrariesAction());
        menuBar.add(saveLibrariesMenu);

        JMenu reLoadLibrariesMenu = new JMenu("ReLoad");
        addMenuItem(reLoadLibrariesMenu, new ReLoadLibrariesAction());
        menuBar.add(reLoadLibrariesMenu);

        setJMenuBar(menuBar);

    }



    // MODIFIES: THIS
    // EFFECTS: CREATES A MENU ITEM WITH THE SPECIFIED ACTION
    public void addMenuItem(JMenu m, AbstractAction action) {
        JMenuItem menuItem = new JMenuItem(action);
        m.add(menuItem);

    }


    // REPRESENTS ACTION THAT NEEDS TO BE TAKEN WHEN USER CLICKS DESKTOP TO SWITCH FOCUS
    public class DesktopAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            BookKeepingGUI.this.requestFocusInWindow();
        }


    }


    // REPRESENTS THE ACTION TO BE TAKEN WHEN THE USER WANTS TO
    // VIEW THE WANT TO READ LIBRARY
    private class ViewWantToReadAction extends AbstractAction {

        ViewWantToReadAction() {
            super("Want To Read");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == j1) {
                wantToReadFrame.dispose();
                wantToReadFrame = new JInternalFrame("WANT TO READ LIBRARY:)");
                JTable t1;
                t1 = new JTable(new MyJtableModel(allLibraries.getWantToRead().getListOfBook()));
                JScrollPane scroll =
                        new JScrollPane(t1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                scroll.setColumnHeaderView(t1.getTableHeader());
                scroll.setVisible(true);


                wantToReadFrame.setSize(400, 600);
                wantToReadFrame.setLocation(100, 130);
                t1.setGridColor(new Color(177, 156, 217));
                t1.setVisible(true);
                wantToReadFrame.add(scroll);
                setUpLibraryListCloseButton(wantToReadFrame);
                filter(wantToReadFrame, t1);

                wantToReadFrame.setVisible(true);
                wantToReadFrame.revalidate();
                desktop.add(wantToReadFrame);
                desktop.revalidate();


            }

        }
    }


    // MODIFIES: JINTERNALFRAME
    // EFFECTS: ADDS CLOSE BUTTON TO THE JINTERNALFRAME
    public void setUpLibraryListCloseButton(JInternalFrame j) {
        JButton closeButton = new JButton("close");
        closeButton.addActionListener(event -> {
            j.dispose();
        });

        closeButton.setBackground(new Color(177, 156, 217));
        closeButton.setOpaque(true);
        closeButton.setBorderPainted(false);
        closeButton.setVisible(true);
        j.add(closeButton, BorderLayout.SOUTH);


    }


    // REPRESENTS A CUSTOM JTABLEMODEL THAT DISPLAYS BOOK TITLES AND AUTHORS
    // THAT ARE ADDED TO A CERTAIN LIBRARY
    // NOTE: Custom JTable code was inspired by https://www.youtube.com/watch?v=AD0GQxYOI_Y&t=385s
    private class MyJtableModel extends AbstractTableModel {
        private final String[] columnName = new String[]{"BOOK TITLE", "AUTHOR NAME"};
        private final ArrayList<Book> books;

        private MyJtableModel(ArrayList<Book> books) {
            this.books = books;
        }


        @Override
        public String getColumnName(int col) {
            return columnName[col];
        }

        @Override
        public Class<?> getColumnClass(int col) {
            if (getValueAt(0, col) != null) {
                return getValueAt(0, col).getClass();
            } else {
                return Object.class;
            }
        }


        @Override
        public int getRowCount() {
            return books.size();


        }


        @Override
        public int getColumnCount() {
            return columnName.length;
        }

        @Override
        public String getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {


                try {
                    return books.get(rowIndex).getTitle();
                } catch (IndexOutOfBoundsException ex) {
                    // all good
                }
            }
            if (columnIndex == 1) {
                try {
                    return books.get(rowIndex).getAuthorln();
                } catch (IndexOutOfBoundsException ex) {
                    // all good
                }

            }

            return "";
        }


    }


    // REPRESENTS THE ACTION THAT NEEDS TO BE TAKEN WHEN A USER WANTS TO VIEW
    // THE IN PROGRESS LIBRARY
    private class ViewInProgressAction extends AbstractAction {

        ViewInProgressAction() {
            super("In Progress");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            inProgressFrame.dispose();
            inProgressFrame = new JInternalFrame("IN PROGRESS LIBRARY:)");
            JTable t1;
            t1 = new JTable(new MyJtableModel(allLibraries.getInProgress().getListOfBook()));
            JScrollPane scroll =
                    new JScrollPane(t1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setColumnHeaderView(t1.getTableHeader());
            scroll.setVisible(true);
            inProgressFrame.setSize(400, 600);
            inProgressFrame.setLocation(500, 130);
            t1.setGridColor(new Color(177, 156, 217));


            t1.setVisible(true);
            inProgressFrame.add(scroll);
            setUpLibraryListCloseButton(inProgressFrame);
            filter(inProgressFrame, t1);

            inProgressFrame.setVisible(true);
            inProgressFrame.revalidate();
            desktop.add(inProgressFrame);
            desktop.revalidate();
        }
    }


    // REPRESENTS THE ACTION NEEDED TO BE TAKEN WHE A VIEW WANTS TO VIEW
    // THE COMPLETED LIBRARY
    private class ViewCompletedAction extends AbstractAction {

        ViewCompletedAction() {
            super("Completed");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            completedFrame.dispose();
            completedFrame = new JInternalFrame("COMPLETED LIBRARY :)");

            JTable t1;
            t1 = new JTable(new MyJtableModel(allLibraries.getCompleted().getListOfBook()));


            JScrollPane scroll =
                    new JScrollPane(t1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setColumnHeaderView(t1.getTableHeader());
            scroll.setVisible(true);
            t1.setSize(400, 550);

            completedFrame.setSize(400, 600);
            completedFrame.setLocation(900, 130);
            t1.setGridColor(new Color(177, 156, 217));


            t1.setVisible(true);
            completedFrame.add(scroll);
            setUpLibraryListCloseButton(completedFrame);
            filter(completedFrame, t1);


            completedFrame.setVisible(true);
            completedFrame.revalidate();
            desktop.add(completedFrame);
            desktop.revalidate();
        }
    }


    // MODIFIES: JTABLE AND JINTERNALFRAME
    // EFFECTS: FILTERS THE JTABLE TO ONLY SHOW VALUES CONTAINING THE SAME
    // STRING KEYWORDS THAT THE USER INDICATES
    // NOTE: FILTER CODE WAS INSPIRED BY https://www.youtube.com/watch?v=Hm14C30b1hg&t=876s
    public void filter(JInternalFrame j1, JTable t1) {
        JTextField tf = new JTextField();
        tf.setBackground(new Color(177, 156, 217));
        tf.setOpaque(true);
        tf.setVisible(true);
        j1.add(tf, BorderLayout.NORTH);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(t1.getModel());
        t1.setRowSorter(sorter);
        JButton filter = new JButton("Filter");
        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = tf.getText();
                if (text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch (IndexOutOfBoundsException ex) {
                        // all good
                    }
                }
            }
        });
        j1.add(filter, BorderLayout.EAST);


    }


    // REPRESENTS THE ACTION THAT NEEDS TO BE TAKEN WHEN A USER WANTS TO ADD
    // A BOOK TO A LIBRARY
    private class AddBookAction extends AbstractAction {

        AddBookAction() {
            super("Add Book");

        }

        public void wantToReadLibraryAddOptions(Book b) {
            String[] priorityOptions = {"Select a Priority Between 1 and 5. 1 being low, 5 being high"};
            String priority = JOptionPane.showInputDialog(null, priorityOptions,
                    "Library Selection", JOptionPane.INFORMATION_MESSAGE);
            b.setPriority(Integer.parseInt(priority));
            allLibraries.getWantToRead().insert(b);

        }

        public void completedLibraryAddOptions(Book b) {
            String[] ratingOptions = {"Select a Rating Between 1 and 5. 1 being low, 5 being high"};
            String rating = JOptionPane.showInputDialog(null, ratingOptions,
                    "Library Selection", JOptionPane.INFORMATION_MESSAGE);
            b.setRating(Integer.parseInt(rating));
            allLibraries.getCompleted().insert(b);
            setUpImage();

        }


        @Override
        public void actionPerformed(ActionEvent e) {
            String bookTitle = JOptionPane.showInputDialog(null, "Enter the book's title",
                    "Book Title",
                    JOptionPane.QUESTION_MESSAGE);
            String authorLastname = JOptionPane.showInputDialog(null,
                    "Enter the author's last name", "Author Last Name",
                    JOptionPane.QUESTION_MESSAGE);
            String[] libraryOptions = {"Select a Library:", "A) Want to Read", "B) In Progress", "C) Completed"};
            String libraryType = JOptionPane.showInputDialog(null, libraryOptions,
                    "Library Selection", JOptionPane.INFORMATION_MESSAGE);
            if ((bookTitle != null) && (authorLastname != null)) {
                Book b = new Book(bookTitle, authorLastname);
                if (libraryType.equals("A")) {
                    wantToReadLibraryAddOptions(b);

                }
                if (libraryType.equals("B")) {
                    allLibraries.getInProgress().insert(b);
                }
                if (libraryType.equals("C")) {
                    completedLibraryAddOptions(b);


                }
            }
        }

        // MODIFIES: THIS
        // EFFECTS: CREATES A POPUP IMAGE WHEN USERS ADD A BOOK TO A COMPLETED LIBRARY
        public void setUpImage() {
            JInternalFrame imageFrame = new JInternalFrame();
            setUpImageCloseButton(imageFrame);


            imageFrame.setLocation(500, 170);

            JPanel imagePanel = new JPanel();

            ImageIcon image = new ImageIcon("./data/img_1.png");
            JLabel imageMessage = new JLabel("YOU'RE A CERTIFIED ICON!");
            imageMessage.setFont(new Font("idk", BOLD, 20));
            imageMessage.setForeground(Color.BLACK);
            imageMessage.setBounds(60, 2, 400, 80);
            imageMessage.setVisible(true);


            JLabel imageLabel = new JLabel(image);
            imageLabel.add(imageMessage);

            imageLabel.setVisible(true);
            imagePanel.add(imageLabel);


            imagePanel.setVisible(true);
            imageFrame.setSize(400, 400);


            imageFrame.setVisible(true);
            imageFrame.add(imagePanel);

            desktop.add(imageFrame);
            imageFrame.setLayer(JLayeredPane.POPUP_LAYER);


            desktop.revalidate();
        }

        // MODIFIES: JINTERNALFRAME
        // EFFECTS: SETS UP THE BUTTON TO REMOVE THE POPUP IMAGE
        // FROM THE JINTERNALFRAME
        public void setUpImageCloseButton(JInternalFrame j) {
            JButton closeButton = new JButton("close");
            closeButton.addActionListener(event -> {
                j.dispose();
            });
            closeButton.setSize(400, 400);
            closeButton.setVisible(true);
            j.setContentPane(closeButton);


        }


    }

    // REPRESENTS THE ACTION THAT NEEDS TO BE TAKEN WHEN A USER
    // WANTS TO REMOVE A BOOK FROM A LIBRARY
    class RemoveBookAction extends AbstractAction {

        RemoveBookAction() {
            super("Remove Book");
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            String bookTitle = JOptionPane.showInputDialog(null, "Enter the book's title",
                    "Book Title",
                    JOptionPane.INFORMATION_MESSAGE);
            String authorLastname = JOptionPane.showInputDialog(null,
                    "Enter the author's last name", "Author Last Name",
                    JOptionPane.INFORMATION_MESSAGE);
            String[] libraryOptions = {"Select a Library:", "A) Want to Read", "B) In Progress", "C) Completed"};
            String libraryType = JOptionPane.showInputDialog(null, libraryOptions,
                    "Library Selection", JOptionPane.INFORMATION_MESSAGE);
            if ((bookTitle != null) && (authorLastname != null)) {
                Book b = new Book(bookTitle, authorLastname);
                if (libraryType.equals("A")) {
                    allLibraries.getWantToRead().remove(b);
                }
                if (libraryType.equals("B")) {
                    allLibraries.getInProgress().remove(b);
                }
                if (libraryType.equals("C")) {
                    allLibraries.getCompleted().remove(b);
                }

            }
        }
    }

    // REPRESENTS THE ACTIONS THAT NEED TO BE TAKEN WHE A USER
    // WANTS TO SAVE THEIR LIBRARIES
    class SaveLibrariesAction extends AbstractAction {

        SaveLibrariesAction() {
            super("Save All");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(allLibraries);
                jsonWriter.close();
            } catch (FileNotFoundException f) {
                JOptionPane.showMessageDialog(null, "oh no, bestie, we couldn't save your libraries:(");
            }
            JOptionPane.showMessageDialog(null, "Yaaaaay! Your Libraries Have Been Saved");
        }
    }


    // EFFECTS: REPRESENTS THE ACTION THAT NEEDS TO BE TAKEN WHEN
    // A USER WANTS TO RELOAD THEIR PREVIOUS LIBRARIES.
    class ReLoadLibrariesAction extends AbstractAction {

        ReLoadLibrariesAction() {
            super("ReLoad All");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                allLibraries = jsonReader.read();
            } catch (IOException f) {
                JOptionPane.showMessageDialog(null, "yikes, we couldn't load your libraries");
            }
            JOptionPane.showMessageDialog(null, "Yaaaaay! Your Libraries Have Been Loaded");
        }
    }


}
