package com.sparta.aw.libraryapp.controllers.web;

import com.sparta.aw.libraryapp.model.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class AuthorWebController {
    private AuthorRepository authorRepository;

    public AuthorWebController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/") // root endpoint
    public String welcome(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "welcome"; // welcome.html
    }

    @GetMapping("/web/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "authors"; // authors.html
    }
}
