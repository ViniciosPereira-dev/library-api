package com.vinicios.library.mappers;

import com.vinicios.library.dtos.LoanCreateDTO;
import com.vinicios.library.dtos.LoanResponseDTO;
import com.vinicios.library.entities.Loan;

import java.util.List;

public class LoanMapper {

    public static LoanResponseDTO toResponseDTO(Loan loan) {
        LoanResponseDTO dto = new LoanResponseDTO();
        dto.setId(loan.getId());
        dto.setUserId(loan.getUser().getId());
        dto.setUserName(loan.getUser().getName());
        dto.setBookId(loan.getBook().getId());
        dto.setBookTitle(loan.getBook().getTitle());
        dto.setLoanDate(loan.getLoanDate());
        dto.setReturnDate(loan.getReturnDate());
        dto.setStatus(loan.getStatus());
        return dto;
    }

    public static List<LoanResponseDTO> toResponseList(List<Loan> loans) {
        return loans.stream()
                .map(LoanMapper::toResponseDTO)
                .toList();
    }
}
