package service;

import model.Book;
import model.Member;
import model.Loan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryManager {

    private List<Book> books;
    private List<Member> members;
    private List<Loan> loans;

    public LibraryManager() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

   
    public void addBook(Book book) {
        books.add(book);
    }

   
    public boolean removeBook(String bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return false;
        }

       
        if (book.getAvailableCopies() < book.getTotalCopies()) {
            System.out.println("Cannot remove book. It is currently loaned out.");
            return false;
        }

        return books.remove(book);
    }

  
    public void addMember(Member member) {
        members.add(member);
    }

   
    public Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(id)) {
                return book;
            }
        }
        return null;
    }

   
    public Member findMemberById(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }
        return null;
    }

 
    public Loan findLoanById(String loanId) {
        for (Loan loan : loans) {
            if (loan.getLoanId().equalsIgnoreCase(loanId)) {
                return loan;
            }
        }
        return null;
    }

 
    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.matches(query)) {
                result.add(book);
            }
        }
        return result;
    }

 
    public List<Member> searchMembers(String query) {
        List<Member> result = new ArrayList<>();
        for (Member member : members) {
            if (member.matches(query)) {
                result.add(member);
            }
        }
        return result;
    }

    
    public boolean borrowBook(String loanId, String bookId, String memberId,
                              LocalDate loanDate, LocalDate dueDate) {

        Book book = findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found: " + bookId);
            return false;
        }

        Member member = findMemberById(memberId);
        if (member == null) {
            System.out.println("Member not found: " + memberId);
            return false;
        }

        boolean success = book.borrowCopy();
        if (!success) {
            System.out.println("No available copies for book: " + bookId);
            return false;
        }

        Loan loan = new Loan(loanId, book, member, loanDate, dueDate);
        loans.add(loan);

        return true;
    }

  
    public boolean returnBook(String loanId) {
        Loan loan = findLoanById(loanId);
        if (loan == null) {
            System.out.println("Loan not found: " + loanId);
            return false;
        }

        if (loan.isReturned()) {
            System.out.println("Loan already returned: " + loanId);
            return false;
        }

        
        LocalDate returnDate = LocalDate.now();
        loan.returnBook(returnDate);

        
        int lateDays = loan.getLateDays();
        if (lateDays > 0) {
            double fee = loan.getMember().calculateFee(lateDays);
            System.out.println("Late return. Days: " + lateDays + ", Fee: " + fee);
        }

        return true;
    }

  
    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}

