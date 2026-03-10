package com.vinicios.library.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Loan creation data")
public class LoanCreateDTO {

    @Schema(description = "User ID", example = "1")
    @NotNull(message = "User ID é obrigatório")
    private Long userId;

    @Schema(description = "Book ID", example = "1")
    @NotNull(message = "Book ID é obrigatório")
    private Long bookId;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
}
