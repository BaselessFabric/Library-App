package com.sparta.aw.libraryapp;

import com.sparta.aw.libraryapp.controllers.rest.AuthorController;
import com.sparta.aw.libraryapp.model.entities.Author;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
class LibraryAppApplicationTests {

    @Test
    void contextLoads() {  // can write tests in here to ensure app starts the way it should
    }

    private WebTestClient webTestClient;

    @Autowired
    private AuthorController authorController;

    @BeforeEach
    public void setup() {
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:8080").build();
    }

    @Test
    @DisplayName("Check that the status code is 200")
    @Transactional
    void checkThatTheStatusCodeIs200() {
        webTestClient
                .get()
                .uri("http://localhost:8080/authors")
                .exchange()
                .expectStatus()
                .is2xxSuccessful();
    }

    @Test
    @DisplayName("Check that the first author is J.K Rowling")
    void checkThatTheFirstAuthorIsJKRowling() {

        webTestClient
                .get()
                .uri("http://localhost:8080/author/get/1")
                .exchange()
                .expectBody(Author.class)
                .value(author -> Assertions.assertEquals("Manish", author.getFullName()));
    }
}
