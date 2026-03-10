package com.vinicios.library.services;

import com.vinicios.library.dtos.LoanCreateDTO;
import com.vinicios.library.dtos.LoanResponseDTO;
import com.vinicios.library.entities.Book;
import com.vinicios.library.entities.Loan;
import com.vinicios.library.entities.User;
import com.vinicios.library.entities.enums.LoanStatus;
import com.vinicios.library.mappers.LoanMapper;
import com.vinicios.library.repositories.BookRepository;
import com.vinicios.library.repositories.LoanRepository;
import com.vinicios.library.repositories.UserRepository;
import com.vinicios.library.services.exceptions.BusinessException;
import com.vinicios.library.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<LoanResponseDTO> findAllLoans() {
        return LoanMapper.toResponseList(loanRepository.findAll());
    }

    public Optional<LoanResponseDTO> findLoanById(Long id) {
        return loanRepository.findById(id)
                .map(LoanMapper::toResponseDTO);
    }

    @Transactional
    public LoanResponseDTO createLoan(LoanCreateDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));

        boolean bookAlreadyLoaned =
                loanRepository.existsByBookIdAndReturnDateIsNull(dto.getBookId());

        if (bookAlreadyLoaned) {
            throw new BusinessException("Livro já está emprestado");
        }

        long activeLoans =
                loanRepository.countByUserIdAndReturnDateIsNull(dto.getUserId());

        if (activeLoans >= 3) {
            throw new BusinessException("Usuário atingiu o limite de empréstimos");
        }

        LocalDate today = LocalDate.now();
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setLoanDate(today);
        loan.setStatus(LoanStatus.BORROWED);

        book.setAvailable(false);
        bookRepository.save(book);
        Loan saved = loanRepository.save(loan);
        return LoanMapper.toResponseDTO(saved);
    }

    @Transactional
    public LoanResponseDTO returnBook(Long loanId) {

        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        if (loan.getReturnDate() != null) {
            throw new BusinessException("Livro já foi devolvido");
        }

        LocalDate today = LocalDate.now();
        loan.setReturnDate(today);
        loan.setStatus(LoanStatus.RETURNED);

        Book book = loan.getBook();
        book.setAvailable(true);
        bookRepository.save(book);
        Loan saved = loanRepository.save(loan);
        return LoanMapper.toResponseDTO(saved);
    }

    public List<LoanResponseDTO> findLoansByUser(Long userId) {
        return LoanMapper.toResponseList(loanRepository.findByUserId(userId));
    }

    public List<LoanResponseDTO> findLoansByBook(Long bookId) {
        return LoanMapper.toResponseList(loanRepository.findByBookId(bookId));
    }

    public List<LoanResponseDTO> findActiveLoans() {
        return LoanMapper.toResponseList(loanRepository.findByReturnDateIsNull());
    }

    public List<LoanResponseDTO> findOverdueLoans() {

        LocalDate today = LocalDate.now();

        return loanRepository.findByReturnDateIsNull()
                .stream()
                .filter(loan -> loan.getLoanDate().plusDays(14).isBefore(today))
                .map(LoanMapper::toResponseDTO)
                .toList();
    }

    public void deleteLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empréstimo não encontrado"));

        loanRepository.delete(loan);
    }
}