package com.sparta.aw.libraryapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryAppApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runner(AuthorRepository authorRepository) {
//        return args -> System.out.println(authorRepository.findAll());
//    }
}
