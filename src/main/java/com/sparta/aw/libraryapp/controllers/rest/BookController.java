package com.sparta.aw.libraryapp.controllers.rest;

import com.sparta.aw.libraryapp.model.entities.Author;
import com.sparta.aw.libraryapp.model.entities.Book;
import com.sparta.aw.libraryapp.model.exceptions.AuthorNotFoundException;
import com.sparta.aw.libraryapp.model.exceptions.BookNotFoundException;
import com.sparta.aw.libraryapp.model.repositories.AuthorRepository;
import com.sparta.aw.libraryapp.model.repositories.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }


    // add book
    @PostMapping("/book")
    public String addBook(@RequestBody Book book) throws AuthorNotFoundException {
        Optional<Author> author = authorRepository.findAuthorByFullName(book.getAuthor().getFullName());
        if (author.isEmpty()) {
            throw new AuthorNotFoundException(book.getAuthor().getFullName());
        } else {
            book.setAuthor(author.get());
            bookRepository.save(book);
            return "book has been saved";
        }
    }

    //get book by id
//    @PostMapping("book/get/{id")
//    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book found with id " + id));
//        return new ResponseEntity<>(book, HttpStatus.OK);
//    }

    //update book by id
    @PostMapping("/book/update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book book) throws BookNotFoundException {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(book.getTitle()));
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());

        Book updatedBook = bookRepository.save(bookToUpdate);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    // Delete a book by ID
    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Integer id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/books/author/{authorId}")
    public List<Book> getBooksByAuthor(@PathVariable Integer authorId) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.getAuthor().getId().equals(authorId))
                .toList();
    }
}
