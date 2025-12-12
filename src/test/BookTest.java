package test;

import model.Book;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    void borrowCopy_reducesAvailableCopies_whenAvailable() {
        Book book = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 2);

        boolean result = book.borrowCopy();

        assertTrue(result);
        assertEquals(1, book.getAvailableCopies());
    }

    @Test
    void borrowCopy_returnsFalse_whenNoCopiesLeft() {
        Book book = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 1);

        book.borrowCopy();                 
        boolean result = book.borrowCopy(); 

        assertFalse(result);
        assertEquals(0, book.getAvailableCopies());
    }

    @Test
    void returnCopy_increasesAvailableCopies_butNotAboveTotal() {
        Book book = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 1);

        book.borrowCopy();  
        book.returnCopy();  
        book.returnCopy();  

        assertEquals(1, book.getAvailableCopies());
    }
}
