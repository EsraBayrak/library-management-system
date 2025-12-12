package app;

import model.Book;
import model.Member;
import model.StudentMember;
import service.LibraryManager;

import java.time.LocalDate;

public class LibraryApp {

    public static void main(String[] args) {

        LibraryManager manager = new LibraryManager();

       
        Book b1 = new Book("B1", "Clean Code", "Robert C. Martin", "1111", 2);
        Book b2 = new Book("B2", "Effective Java", "Joshua Bloch", "2222", 1);

        manager.addBook(b1);
        manager.addBook(b2);

       
        Member normalMember = new Member("M1", "Normal User", "normal@mail.com");
        StudentMember student = new StudentMember("S1", "Student User", "student@mail.com", "Computer Engineering");

        manager.addMember(normalMember);
        manager.addMember(student);

     
        LocalDate today = LocalDate.now();
        LocalDate dueNormal = today.plusDays(7);     
        LocalDate dueStudent = today.minusDays(3);   

       
        System.out.println("=== BORROW BOOKS ===");
        manager.borrowBook("L1", "B1", "M1", today, dueNormal);
        manager.borrowBook("L2", "B2", "S1", today, dueStudent);

    
        System.out.println("\n=== RETURN BOOKS ===");
        manager.returnBook("L1");   
        manager.returnBook("L2");   

       
        System.out.println("\n=== BOOK STATES ===");
        for (Book book : manager.getBooks()) {
            System.out.println(book.getId() + " - " + book.getTitle()
                    + " | available: " + book.getAvailableCopies()
                    + "/" + book.getTotalCopies());
        }
    }
}

