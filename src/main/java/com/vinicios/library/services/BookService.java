package com.vinicios.library.services;

import com.vinicios.library.entities.Book;
import com.vinicios.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Optional<Book> updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id).map(existingBook -> {
            if (bookDetails.getTitle() != null) {
                existingBook.setTitle(bookDetails.getTitle());
            }
            if (bookDetails.getAuthor() != null) {
                existingBook.setAuthor(bookDetails.getAuthor());
            }
            if (bookDetails.getIsbn() != null) {
                existingBook.setIsbn(bookDetails.getIsbn());
            }
            existingBook.setAvailable(bookDetails.isAvailable());
            return bookRepository.save(existingBook);
        });
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public boolean bookExists(Long id) {
        return bookRepository.existsById(id);
    }

    public long getBookCount() {
        return bookRepository.count();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.findAll().stream()
                .filter(Book::isAvailable)
                .toList();
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findAll().stream()
                .filter(book -> author.equalsIgnoreCase(book.getAuthor()))
                .toList();
    }

    public List<Book> getBooksByTitle(String title) {
        return bookRepository.findAll().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findAll().stream()
                .filter(book -> isbn.equals(book.getIsbn()))
                .findFirst();
    }
}
