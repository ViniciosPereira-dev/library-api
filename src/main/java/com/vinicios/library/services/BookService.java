package com.vinicios.library.services;

import com.vinicios.library.dtos.BookCreateDTO;
import com.vinicios.library.dtos.BookResponseDTO;
import com.vinicios.library.entities.Book;
import com.vinicios.library.mappers.BookMapper;
import com.vinicios.library.repositories.BookRepository;
import com.vinicios.library.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookResponseDTO createBook(BookCreateDTO dto) {
        Book book = BookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);
        return BookMapper.toResponseDTO(saved);
    }

    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
        return BookMapper.toResponseDTO(book);
    }

    public BookResponseDTO updateBook(Long id, BookCreateDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        Book updated = bookRepository.save(book);
        return BookMapper.toResponseDTO(updated);
    }

    public void deleteBook(Long id) {
        bookRepository.findById(id)
                .ifPresentOrElse(
                        bookRepository::delete,
                        () -> { throw new ResourceNotFoundException("Livro não encontrado"); }
                );
    }

    public boolean bookExists(Long id) {
        return bookRepository.existsById(id);
    }

    public long getBookCount() {
        return bookRepository.count();
    }

    public List<BookResponseDTO> getAvailableBooks() {
        return bookRepository.findByAvailableTrue()
                .stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorIgnoreCase(author)
                .stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<BookResponseDTO> getBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public BookResponseDTO getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado"));
        return BookMapper.toResponseDTO(book);
    }
}
