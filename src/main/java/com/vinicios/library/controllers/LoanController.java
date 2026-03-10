package com.vinicios.library.controllers;

import com.vinicios.library.dtos.LoanCreateDTO;
import com.vinicios.library.dtos.LoanResponseDTO;
import com.vinicios.library.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
@Tag(name = "Loans", description = "API for managing loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping
    @Operation(summary = "Get all loans", description = "Retrieve a list of all loans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<LoanResponseDTO>> getAllLoans() {
        List<LoanResponseDTO> loans = loanService.findAllLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan by ID", description = "Retrieve a loan by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved loan"),
        @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long id) {
        Optional<LoanResponseDTO> loan = loanService.findLoanById(id);
        return loan.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new loan", description = "Create a new loan with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Loan created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<LoanResponseDTO> createLoan(@RequestBody @Valid LoanCreateDTO dto) {
        LoanResponseDTO saved = loanService.createLoan(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}/return")
    @Operation(summary = "Return book", description = "Mark a loan as returned")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book returned successfully"),
        @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public ResponseEntity<LoanResponseDTO> returnBook(@PathVariable Long id) {
            LoanResponseDTO updated = loanService.returnBook(id);
            return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete loan", description = "Delete a loan by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Loan deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Loan not found")
    })
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        try {
            loanService.deleteLoan(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get loans by user", description = "Retrieve loans for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<LoanResponseDTO>> getLoansByUser(@PathVariable Long userId) {
        List<LoanResponseDTO> loans = loanService.findLoansByUser(userId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/book/{bookId}")
    @Operation(summary = "Get loans by book", description = "Retrieve loans for a specific book")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<LoanResponseDTO>> getLoansByBook(@PathVariable Long bookId) {
        List<LoanResponseDTO> loans = loanService.findLoansByBook(bookId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active loans", description = "Retrieve a list of active loans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<LoanResponseDTO>> getActiveLoans() {
        List<LoanResponseDTO> loans = loanService.findActiveLoans();
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue loans", description = "Retrieve a list of overdue loans")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<LoanResponseDTO>> getOverdueLoans() {
        List<LoanResponseDTO> loans = loanService.findOverdueLoans();
        return ResponseEntity.ok(loans);
    }
}
