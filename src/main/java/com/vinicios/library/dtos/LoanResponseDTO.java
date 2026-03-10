package com.vinicios.library.dtos;

import com.vinicios.library.entities.enums.LoanStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Loan response data")
public class LoanResponseDTO {

    @Schema(description = "Loan ID", example = "1")
    private Long id;

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "User name", example = "John Doe")
    private String userName;

    @Schema(description = "Book ID", example = "1")
    private Long bookId;

    @Schema(description = "Book title", example = "The Great Gatsby")
    private String bookTitle;

    @Schema(description = "Loan date", example = "2023-01-01")
    private LocalDate loanDate;

    @Schema(description = "Return date", example = "2023-01-15")
    private LocalDate returnDate;

    @Schema(description = "Loan status", example = "ACTIVE")
    private LoanStatus status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long UserId) { this.userId = UserId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }

}
