package com.vinicios.library.repositories;

import com.vinicios.library.entities.Loan;
import com.vinicios.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserId(Long userId);

    List<Loan> findByBookId(Long bookId);

    List<Loan> findByReturnDateIsNull();

    boolean existsByBookIdAndReturnDateIsNull(Long bookId);

    long countByUserIdAndReturnDateIsNull(Long userId);

}
