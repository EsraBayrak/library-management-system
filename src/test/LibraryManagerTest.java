package test;

import model.Book;
import model.Member;
import model.StudentMember;
import model.Loan;
import org.junit.jupiter.api.Test;
import service.LibraryManager;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryManagerTest {

    @Test
    void addBook_and_findBookById_works() {
        LibraryManager manager = new LibraryManager();

        Book book = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 2);
        manager.addBook(book);

        Book found = manager.findBookById("B1");

        assertNotNull(found);
        assertEquals("Clean Code", found.getTitle());
    }

    @Test
    void borrowBook_success_reducesAvailableCopies_andCreatesLoan() {
        LibraryManager manager = new LibraryManager();

        Book book = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 1);
        manager.addBook(book);

        Member member = new Member("M1", "Normal User", "normal@mail.com");
        manager.addMember(member);

        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(7);

        boolean result = manager.borrowBook("L1", "B1", "M1", today, due);

        assertTrue(result);
        assertEquals(0, book.getAvailableCopies());

        List<Loan> loans = manager.getLoans();
        assertEquals(1, loans.size());
        assertEquals("L1", loans.get(0).getLoanId());
    }

    @Test
    void borrowBook_fails_whenNoAvailableCopies() {
        LibraryManager manager = new LibraryManager();

        Book book = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 1);
        manager.addBook(book);

        Member m1 = new Member("M1", "User1", "u1@mail.com");
        Member m2 = new Member("M2", "User2", "u2@mail.com");
        manager.addMember(m1);
        manager.addMember(m2);

        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(7);

        boolean first = manager.borrowBook("L1", "B1", "M1", today, due);
        boolean second = manager.borrowBook("L2", "B1", "M2", today, due);

        assertTrue(first);
        assertFalse(second);              
        assertEquals(0, book.getAvailableCopies());
        assertEquals(1, manager.getLoans().size());
    }

    @Test
    void returnBook_marksLoanReturned_andUpdatesCopies() {
        LibraryManager manager = new LibraryManager();

        Book book = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 1);
        manager.addBook(book);

        StudentMember student = new StudentMember("S1", "Student", "s@mail.com", "CS");
        manager.addMember(student);

        LocalDate today = LocalDate.now();
        LocalDate due = today.minusDays(2);  

        manager.borrowBook("L1", "B1", "S1", today, due);

        boolean returned = manager.returnBook("L1");

        assertTrue(returned);
        assertEquals(1, book.getAvailableCopies());

        Loan loan = manager.getLoans().get(0);
        assertTrue(loan.isReturned());
        assertTrue(loan.getLateDays() >= 2);
    }
}

