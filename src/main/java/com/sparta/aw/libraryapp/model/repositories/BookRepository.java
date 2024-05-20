package com.sparta.aw.libraryapp.model.repositories;

import com.sparta.aw.libraryapp.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
//    List<Book> findAll();
}