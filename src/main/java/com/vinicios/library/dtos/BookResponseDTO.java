package com.vinicios.library.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Book response data")
public class BookResponseDTO {

    @Schema(description = "Book ID", example = "1")
    private Long id;

    @Schema(description = "Book title", example = "The Great Gatsby")
    private String title;

    @Schema(description = "Book author", example = "F. Scott Fitzgerald")
    private String author;

    @Schema(description = "Book ISBN", example = "978-0-7432-7356-5")
    private String isbn;

    @Schema(description = "Availability status", example = "true")
    private boolean available;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
}
