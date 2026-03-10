package com.vinicios.library.repositories;

import com.vinicios.library.entities.Book;
import com.vinicios.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
