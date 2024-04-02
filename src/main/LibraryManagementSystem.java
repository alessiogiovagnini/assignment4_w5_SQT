package main;

import java.util.ArrayList;


public class LibraryManagementSystem {
    // Enum for representing the state of the system
    public enum State {
        IDLE, LOAN_BOOK, RETURN_BOOK, LATE_FEE_MANAGEMENT, LOST_BOOK_HANDLING, ADMINISTRATIVE_TASKS, ERROR, DAMAGED_BOOK
    }

    // State variable to track the current state of the system
    private State currentState;

    // ArrayList to store books in the library
    private final ArrayList<Book> libraryBooks;

    // Constructor to initialize the Library Management System
    public LibraryManagementSystem() {
        currentState = State.IDLE;
        libraryBooks = new ArrayList<>();
    }

    // methods for testing
    public State getCurrentState() {
        return currentState;
    }

    public void addBook(Book book) {
        libraryBooks.add(book);
    }

    // Method to loan a book to a patron
    public void loanBook(Book book, Patron patron) {                                                                    // A: def: book, patron
        if (currentState == State.IDLE) {                                                                               // B: use: book
            // Check if the book is available in the library
            if (libraryBooks.contains(book)) {                                                                          // C: use: book, libraryBooks
                // Perform book loaning process
                currentState = State.LOAN_BOOK; // Transition to LOAN_BOOK state                                        // D: def: currentState
                // For simplicity, assume the book is successfully loaned to the patron
                System.out.println("Book '" + book.getTitle() + "' loaned to patron '" + patron.getName() + "'.");      // E: use: book, patron
                libraryBooks.remove(book); // Remove the book from the library (assuming it is loaned out               // F: use: book, libraryBooks
                currentState = State.IDLE; // Transition back to IDLE state                                             // G: def: currentState
            } else {
                System.out.println("Book '" + book.getTitle() + "' is not available in the library.");                  // H: use: book
                currentState = State.IDLE; // Transition back to IDLE state                                             // I: def: currentState
            }
        } else {
            System.out.println("Cannot loan a book in the current state.");
            libraryManagementError();
        }
    }


    // Method to return a borrowed book
    public void returnBook(Book book, Patron patron) {                                                                  // A: def: book, patron
        if (currentState == State.IDLE) {                                                                               // B: use: currentState
            // Perform book returning process
            currentState = State.RETURN_BOOK; // Transition to RETURN_BOOK state                                        // C: def: currentState
            // Check if the book is damaged and number of days overdue
            if (book.getDamage()) {                                                                                     // D: use: book
                // Handle damaged book
                currentState = State.DAMAGED_BOOK; // Transition to DAMAGED_BOOK state                                  // E: def: currentState
                System.out.println("Book '" + book.getTitle() + "' is damaged. Patron '" + patron.getID() + "'' will be charged 15$ for the damage."); // F: use: book, patron
            }
            else if (book.getDays() > 30) {                                                                             // G: use: book
                // Handle overdue book
                currentState = State.LATE_FEE_MANAGEMENT; // Transition to DAMAGED_BOOK state                           // H: def: currentState
                System.out.println("Book '" + book.getTitle() + "' is overdue by '" + book.getDays() + "'' days with a fee of '" + manageLateFees(book.getDays()) + "'' dollars for the patron '" + patron.getID() + "."); // I: use: book, patron
            }
            else {
                libraryBooks.add(book); // Assume the book is successfully returned to the library                      // J: use: book, libraryBooks
                System.out.println("Book '" + book.getTitle() + "' returned to the library by patron '" + patron.getID() + "."); // K: use: book
            }
            currentState = State.IDLE; // Transition back to IDLE state                                                 // L: defL currentState
        } else {
            System.out.println("Cannot return a book in the current state.");
            libraryManagementError();
        }
    }


    // Method to manage late fees for overdue books
    public float manageLateFees(int overdue) {                                                                          // A: def: overdue
        if (currentState == State.LATE_FEE_MANAGEMENT) {                                                                // B: use: currentState
            // Perform late fee management process
            System.out.println("Late fees for overdue books managed successfully.");
            return overdue * 0.5f; // Assume a late fee of 0.5$ per day                                                 // C: use: overdue
        }
        else {
            System.out.println("Cannot manage late fees in the current state.");
            libraryManagementError();
        }
        return 0.0f;
    }

    // Method to handle lost books and associated fees
    public void handleLostBook(Book book, Patron patron) {                                                              // A: def: book, patron
        if (currentState == State.IDLE) {                                                                               // B: use: currentState, State
            // Perform lost book handling process
            currentState = State.LOST_BOOK_HANDLING; // Transition to LOST_BOOK_HANDLING state                          // C: def: currentState
            libraryBooks.remove(book); // Remove the lost book from the library                                         // D: use: libraryBooks, book
            // For simplicity, assume the lost book is handled successfully
            System.out.println("Lost book '" + book.getTitle() + "' handled for patron '" + patron.getID() + ", who will be charged a fee of 20$."); // E: use: book, patron
            currentState = State.IDLE; // Transition back to IDLE state                                                 // F: def currentState
        }
        else {
            System.out.println("Cannot handle lost book in the current state.");
            libraryManagementError();
        }
    }


    // Method to perform administrative tasks
    public void performAdministrativeTasks() {
        if (currentState == State.IDLE) {                                                                               // A: use: currentState
            // Perform administrative tasks
            currentState = State.ADMINISTRATIVE_TASKS; // Transition to ADMINISTRATIVE_TASKS state                      // B: def: currentState
            // For simplicity, assume administrative tasks are performed successfully
            System.out.println("Administrative tasks performed successfully.");
            currentState = State.IDLE; // Transition back to IDLE state                                                 // C: def: currentState
        } else {
            System.out.println("Cannot perform administrative tasks in the current state.");
            libraryManagementError();
        }
    }


    // Method to handle errors encountered during operation
    public void libraryManagementError() {
        currentState = State.ERROR; // Transition to ERROR state                                                        // A: def: currentState
        System.out.println("Error: Operation failed due to unexpected state.");
    }


    // Inner class representing a book
    public static class Book {
        private String title;
        private int days;
        private boolean damaged;


        public Book(String title, int days, boolean damaged) {
            this.title = title;
            this.days = days;
            this.damaged = damaged;
        }

        public String getTitle() {
            return title;
        }

        public int getDays() {
            return days;
        }

        public boolean getDamage() {
            return damaged;
        }
    }


    // Inner class representing a library patron
    public static class Patron {
        private String name;
        private String surname;
        private String address;
        private String phone;
        private int ID;


        public Patron(String name, String surname, String address, String phone, int ID) {
            this.name = name;
            this.surname = surname;
            this.address = address;
            this.phone = phone;
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public String getSurname() {
            return surname;
        }

        public String getAddress() {
            return address;
        }

        public String getPhone() {
            return phone;
        }

        public int getID() {
            return ID;
        }
    }


    // Main method for testing
    public static void main(String[] args) {
        /*
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();

        // Initialize the library
        Book book1 = new Book("Book 1", 16, false);
        Book book2 = new Book("Book 2", 32, true);
        librarySystem.libraryBooks.add(book1);
        librarySystem.libraryBooks.add(book2);

        // Create patrons
        Patron patron1 = new Patron("John", "Doe", "123 Main St", "555-1234", 1);
        Patron patron2 = new Patron("Dave", "Scott", "134 Secondary St", "555-6789", 2);

        // Loan a book to a patron
        librarySystem.loanBook(book1, patron1);

        // Return a book
        librarySystem.returnBook(book1, patron1);

        // Loan a book to a patron
        librarySystem.loanBook(book2, patron2);

        // Return a book
        librarySystem.returnBook(book2, patron2);

        // Perform administrative tasks
        librarySystem.performAdministrativeTasks();*/
    }
}