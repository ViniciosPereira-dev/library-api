package com.vinicios.library.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Book creation data")
public class BookCreateDTO {

    @Schema(description = "Book title", example = "The Great Gatsby")
    @NotBlank(message = "Título é obrigatório")
    private String title;

    @Schema(description = "Book author", example = "F. Scott Fitzgerald")
    @NotBlank(message = "Autor é obrigatório")
    private String author;

    @Schema(description = "Book ISBN", example = "978-0-7432-7356-5")
    @NotBlank(message = "ISBN é obrigatório")
    private String isbn;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
}
