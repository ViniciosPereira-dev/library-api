package com.vinicios.library.controllers;

import com.vinicios.library.dtos.BookCreateDTO;
import com.vinicios.library.dtos.BookResponseDTO;
import com.vinicios.library.services.BookService;
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

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API for managing books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieve a book by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved book"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    @Operation(summary = "Create a new book", description = "Create a new book with the provided data")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Book created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookCreateDTO dto) {
        BookResponseDTO saved = bookService.createBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Update an existing book by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Book updated successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id,
            @RequestBody @Valid BookCreateDTO dto) {
        BookResponseDTO updatedBook = bookService.updateBook(id, dto);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Delete a book by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Book deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (bookService.bookExists(id)) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/available")
    @Operation(summary = "Get available books", description = "Retrieve a list of available books")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<BookResponseDTO>> getAvailableBooks() {
        List<BookResponseDTO> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/author/{author}")
    @Operation(summary = "Get books by author", description = "Retrieve books by author name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<BookResponseDTO>> getBooksByAuthor(@PathVariable String author) {
        List<BookResponseDTO> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/title/{title}")
    @Operation(summary = "Get books by title", description = "Retrieve books by title")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<BookResponseDTO>> getBooksByTitle(@PathVariable String title) {
        List<BookResponseDTO> books = bookService.getBooksByTitle(title);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Get book by ISBN", description = "Retrieve a book by its ISBN")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved book"),
        @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponseDTO> getBookByIsbn(@PathVariable String isbn) {
        BookResponseDTO book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(book);
    }
}
