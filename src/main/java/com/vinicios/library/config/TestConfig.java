package com.vinicios.library.config;

import com.vinicios.library.entities.Book;
import com.vinicios.library.entities.Loan;
import com.vinicios.library.entities.User;
import com.vinicios.library.entities.enums.LoanStatus;
import com.vinicios.library.repositories.BookRepository;
import com.vinicios.library.repositories.LoanRepository;
import com.vinicios.library.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public TestConfig(
            UserRepository userRepository,
            BookRepository bookRepository,
            LoanRepository loanRepository) {

        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) {

        User u1 = new User();
        u1.setName("Vinicios");
        u1.setEmail("vinicios@email.com");
        u1.setPassword("123456");

        User u2 = new User();
        u2.setName("Camille");
        u2.setEmail("camille@email.com");
        u2.setPassword("123456");

        userRepository.save(u1);
        userRepository.save(u2);

        Book b1 = new Book();
        b1.setTitle("Clean Code");
        b1.setAuthor("Robert C. Martin");
        b1.setIsbn("9780132350884");
        b1.setAvailable(true);

        Book b2 = new Book();
        b2.setTitle("Effective Java");
        b2.setAuthor("Joshua Bloch");
        b2.setIsbn("9780134685991");
        b2.setAvailable(true);

        Book b3 = new Book();
        b3.setTitle("Spring in Action");
        b3.setAuthor("Craig Walls");
        b3.setIsbn("9781617294945");
        b3.setAvailable(true);

        Book b4 = new Book();
        b4.setTitle("Refactoring");
        b4.setAuthor("Martin Fowler");
        b4.setIsbn("9780201485677");
        b4.setAvailable(true);

        Book b5 = new Book();
        b5.setTitle("Nada Pode Me Ferir");
        b5.setAuthor("David Goggins");
        b5.setIsbn("9781544512280");
        b5.setAvailable(true);

        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);
        bookRepository.save(b4);
        bookRepository.save(b5);

        Loan l1 = new Loan();
        l1.setUser(u1);
        l1.setBook(b1);
        l1.setLoanDate(LocalDate.now().minusDays(3));
        l1.setReturnDate(null);
        l1.setStatus(LoanStatus.BORROWED);
        loanRepository.save(l1);

        Loan l2 = new Loan();
        l2.setUser(u1);
        l2.setBook(b2);
        l2.setLoanDate(LocalDate.now().minusDays(1));
        l2.setReturnDate(null);
        l2.setStatus(LoanStatus.BORROWED);
        loanRepository.save(l2);

        Loan l3 = new Loan();
        l3.setUser(u2);
        l3.setBook(b3);
        l3.setLoanDate(LocalDate.now().minusDays(7));
        l3.setReturnDate(LocalDate.now().minusDays(2));
        l3.setStatus(LoanStatus.RETURNED);
        loanRepository.save(l3);

        b1.setAvailable(false);
        b2.setAvailable(false);

        bookRepository.save(b1);
        bookRepository.save(b2);
    }
}