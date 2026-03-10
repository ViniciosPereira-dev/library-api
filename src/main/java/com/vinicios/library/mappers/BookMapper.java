package com.vinicios.library.mappers;

import com.vinicios.library.dtos.BookCreateDTO;
import com.vinicios.library.dtos.BookResponseDTO;
import com.vinicios.library.entities.Book;

import java.util.List;

public class BookMapper {

    public static Book toEntity(BookCreateDTO dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setAvailable(true);
        return book;
    }

    public static BookResponseDTO toResponseDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setAvailable(book.isAvailable());
        return dto;
    }

    public static List<BookResponseDTO> toResponseList(List<Book> books) {
        return books.stream()
                .map(BookMapper::toResponseDTO)
                .toList();
    }
}

