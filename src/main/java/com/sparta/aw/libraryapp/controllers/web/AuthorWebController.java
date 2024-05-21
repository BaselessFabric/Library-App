package com.sparta.aw.libraryapp.controllers.web;

import com.sparta.aw.libraryapp.model.entities.Author;
import com.sparta.aw.libraryapp.model.entities.Book;
import com.sparta.aw.libraryapp.model.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/web")
    public String home(Model model) {
        model.addAttribute("hello", LocalDateTime.now());
        return "home";
    }

    @GetMapping("/web/authors")
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorRepository.findAll());
        return "authors"; // authors.html
    }

    @GetMapping("/web/author/{id}")
    public String getAuthorById(@PathVariable Integer id, Model model) {
        Author author = authorRepository.findById(id).orElse(null);
        model.addAttribute("author", author);
        return "author";
    }

    @GetMapping("web/add-author")
    public String addAuthor(Model model) {
        Author author = new Author();
        model.addAttribute("author", author);
        return "add-author";
    }

    @PostMapping("/web/save-author")
    public String saveAuthor(@ModelAttribute("author") Author author, Model model) {
        authorRepository.save(author);
//        model.addAttribute("authors", authorRepository.findAll());
        return "redirect:/web/authors";
    }

    //delete author by id
    @GetMapping("/web/author/delete/{id}")
    public String deleteAuthor(@PathVariable Integer id) {
        Author authorToDelete = authorRepository.findById(id).orElse(null);
        if (authorToDelete != null) {
            authorRepository.delete(authorToDelete);
        }
//        String authorName = authorToDelete.getFullName();
        return "redirect:/web/authors";
    }

    @GetMapping("/web/author/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Author author = authorRepository.findById(id).orElse(null);
        model.addAttribute("author", author);
        return "update-author";
    }

    @PostMapping("/web/author/edit/{id}")
    public String updateWebAuthor(@PathVariable Integer id,  @ModelAttribute("author") Author author, RedirectAttributes redirectAttributes) {
        Author authorToUpdate = authorRepository.findById(id).orElse(null);
        authorToUpdate.setFullName(author.getFullName());
        authorRepository.save(authorToUpdate);
        String message = "Author '" + author.getFullName() + "' updated successfully!";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/web/authors";
    }

}
