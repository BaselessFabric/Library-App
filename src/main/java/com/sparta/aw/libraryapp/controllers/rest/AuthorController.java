package com.sparta.aw.libraryapp.controllers.rest;

import com.sparta.aw.libraryapp.model.entities.Author;
import com.sparta.aw.libraryapp.model.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // get author by name
    @GetMapping("/author/get/name")
    public List<Author> getAuthorByName(@RequestParam(name ="nm", required = false) String name) {
        return authorRepository.findByFullName(name);
//        return authorRepository.findAll()
//                .stream()
//                .filter(author -> author.getFullName().contains(name))
//                        .toList();
    }

    // get author by id
    @GetMapping("/author/get/{id}")
    public EntityModel<Author> getAuthorById(@PathVariable Integer id) {
        return authorRepository.findById(id)
                .map(author -> EntityModel.of(author,
                        linkTo(methodOn(AuthorController.class).getAuthorById(id)).withSelfRel(),
                        linkTo(methodOn(AuthorController.class).getAllAuthors()).withRel("authors"),
                        WebMvcLinkBuilder.linkTo(methodOn(BookController.class).getBooksByAuthor(author.getId())).withRel("all books by author")))
                .orElseThrow();
    }

    // create new author
    @PostMapping("/author/post")
    public ResponseEntity<String> addAuthor(@RequestBody Author author) {
        authorRepository.save(author);
        return new ResponseEntity<>("Author saved: " + author.getFullName(), HttpStatus.CREATED);
    }

    // update author by id
    @PutMapping("/author/patch/{id}")
    public Author patchAuthorById(@PathVariable Integer id, @RequestBody Author author) {
        // Get the author that needs to be updated
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No author with found id " + id));
        // Update that author object
        authorToUpdate.setFullName(author.getFullName());
        // Save the updated author object back to the database
        return authorRepository.save(authorToUpdate);

    }

    //delete author by id
    @DeleteMapping("/author/delete/{id}")
    public String deleteAuthorById(@PathVariable Integer id) {
        Author authorToDelete = authorRepository.findById(id).get();
        String authorName = authorToDelete.getFullName();
        authorRepository.delete(authorToDelete);
        return "Author deleted: " + authorToDelete;
    }



}
