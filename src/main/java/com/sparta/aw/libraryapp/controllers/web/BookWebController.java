package com.sparta.aw.libraryapp.controllers.web;

import com.sparta.aw.libraryapp.model.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookWebController {

    private BookRepository bookRepository;

    public BookWebController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/web/books")
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookRepository.findAll());
                return "books";
    }
}
