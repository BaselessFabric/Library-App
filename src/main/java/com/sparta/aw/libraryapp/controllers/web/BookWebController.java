package com.sparta.aw.libraryapp.controllers.web;

import com.sparta.aw.libraryapp.model.entities.Author;
import com.sparta.aw.libraryapp.model.entities.Book;
import com.sparta.aw.libraryapp.model.repositories.AuthorRepository;
import com.sparta.aw.libraryapp.model.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookWebController {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public BookWebController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @GetMapping("/web/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());

        if (model.containsAttribute("message")) {
            model.addAttribute("message", model.getAttribute("message"));
        }
        return "books";
    }

    @GetMapping("/web/add-book")
    public String addBookPage(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        Book book = new Book();
        model.addAttribute("book", book);
        return "add-book";
    }

    @PostMapping("/web/add-book")
    public String addBook(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes) {


        Author author = book.getAuthor();
        authorRepository.save(author);


        book.setAuthor(author);
        bookRepository.save(book);

        String message = "Book '" + book.getTitle() + "' added successfully!";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/web/books";
    }

    @GetMapping("/web/book/delete/{id}")
    public String deleteBook(@PathVariable Integer id) {
        bookRepository.deleteById(id);
        return "redirect:/web/books";
    }

    @GetMapping("/web/book/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Book book = bookRepository.findById(id).orElse(null);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorRepository.findAll());
        return "update-book";
    }

    //update book
    @PostMapping("/web/book/edit/{id}")
    public String updateWebBook(@ModelAttribute("book") Book book, @PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Book bookToUpdate = bookRepository.findById(id).orElse(null);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookRepository.save(bookToUpdate);
        String message = "Book '" + book.getTitle() + "' updated successfully!";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/web/books";
    }

}
