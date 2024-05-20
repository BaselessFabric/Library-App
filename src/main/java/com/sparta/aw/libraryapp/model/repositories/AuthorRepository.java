package com.sparta.aw.libraryapp.model.repositories;

import com.sparta.aw.libraryapp.model.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
//    List<Author> findAll();
    List<Author> findByFullName(String fullName); //Spring expression
//    Optional<Author> findById(Integer id);
    Optional<Author> findAuthorByFullName(String fullName);

}