package test;

import main.LibraryManagementSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryManagementSystemTest {

    @Test
    public void TestLoanBook() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();

        LibraryManagementSystem.Book book1 = new LibraryManagementSystem.Book("Book 1", 16, false);

        LibraryManagementSystem.Patron patron1 = new LibraryManagementSystem.Patron("John", "Doe", "123 Main St", "555-1234", 1);
        librarySystem.addBook(book1);
        librarySystem.loanBook(book1, patron1);

        assertEquals(librarySystem.getCurrentState(), LibraryManagementSystem.State.IDLE);
    }

    @Test
    public void TestBookUnavailable() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();

        LibraryManagementSystem.Book book1 = new LibraryManagementSystem.Book("Book 1", 16, false);

        LibraryManagementSystem.Patron patron1 = new LibraryManagementSystem.Patron("John", "Doe", "123 Main St", "555-1234", 1);

        librarySystem.loanBook(book1, patron1);

        assertEquals(librarySystem.getCurrentState(), LibraryManagementSystem.State.IDLE);
    }

    @Test
    public void TestBookDamaged() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        LibraryManagementSystem.Book book1 = new LibraryManagementSystem.Book("Book 1", 10, true);
        LibraryManagementSystem.Patron patron1 = new LibraryManagementSystem.Patron("John", "Doe", "123 Main St", "555-1234", 1);
        librarySystem.returnBook(book1, patron1);
        assertEquals(librarySystem.getCurrentState(), LibraryManagementSystem.State.IDLE);

    }

    @Test
    public void TestLateFee() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();

        LibraryManagementSystem.Book book1 = new LibraryManagementSystem.Book("Book 1", 112, false);

        LibraryManagementSystem.Patron patron1 = new LibraryManagementSystem.Patron("John", "Doe", "123 Main St", "555-1234", 1);

        librarySystem.returnBook(book1, patron1);

        assertEquals(librarySystem.getCurrentState(), LibraryManagementSystem.State.IDLE);
    }

    @Test
    public void TestHandleLostBook() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        LibraryManagementSystem.Book book1 = new LibraryManagementSystem.Book("Book 1", 9, false);
        LibraryManagementSystem.Patron patron1 = new LibraryManagementSystem.Patron("John", "Doe", "123 Main St", "555-1234", 1);
        librarySystem.addBook(book1);
        librarySystem.handleLostBook(book1, patron1);
        assertEquals(librarySystem.getCurrentState(), LibraryManagementSystem.State.IDLE);

    }


    @Test
    public void TestAdministrativeTast() {
        LibraryManagementSystem librarySystem = new LibraryManagementSystem();
        librarySystem.performAdministrativeTasks();
        assertEquals(librarySystem.getCurrentState(), LibraryManagementSystem.State.IDLE);
    }

}
