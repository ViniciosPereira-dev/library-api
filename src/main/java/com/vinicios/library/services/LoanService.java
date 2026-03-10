package com.vinicios.library.services;

import com.vinicios.library.entities.Book;
import com.vinicios.library.entities.Loan;
import com.vinicios.library.entities.User;
import com.vinicios.library.repositories.BookRepository;
import com.vinicios.library.repositories.LoanRepository;
import com.vinicios.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    public Loan findLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
    }

    public Loan createLoan(Long userId, Long bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        boolean bookAlreadyLoaned =
                loanRepository.existsByBookIdAndReturnDateIsNull(bookId);

        if (bookAlreadyLoaned) {
            throw new RuntimeException("Livro já está emprestado");
        }

        long activeLoans =
                loanRepository.countByUserIdAndReturnDateIsNull(userId);

        if (activeLoans >= 3) {
            throw new RuntimeException("Usuário atingiu o limite de empréstimos");
        }

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    public Loan returnBook(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if (loan.getReturnDate() != null) {
            throw new RuntimeException("Livro já foi devolvido");
        }

        loan.setReturnDate(LocalDate.now());

        return loanRepository.save(loan);
    }

    public List<Loan> findLoansByUser(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    public List<Loan> findLoansByBook(Long bookId) {
        return loanRepository.findByBookId(bookId);
    }

    public List<Loan> findActiveLoans() {
        return loanRepository.findByReturnDateIsNull();
    }

    public List<Loan> findOverdueLoans() {

        LocalDate today = LocalDate.now();

        return loanRepository.findByReturnDateIsNull()
                .stream()
                .filter(loan -> loan.getLoanDate().plusDays(14).isBefore(today))
                .toList();
    }

    public void deleteLoan(Long id) {

        if (!loanRepository.existsById(id)) {
            throw new RuntimeException("Empréstimo não encontrado");
        }

        loanRepository.deleteById(id);
    }
}