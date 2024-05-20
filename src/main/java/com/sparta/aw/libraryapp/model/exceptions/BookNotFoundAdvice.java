package com.sparta.aw.libraryapp.model.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookNotFoundAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BookNotFoundResponse> bookNotFoundHandler(BookNotFoundException e, HttpServletRequest request) {
        BookNotFoundResponse response = new BookNotFoundResponse(request.getRequestURL().toString(), 400, e.getMessage());
        return ResponseEntity.badRequest().body(response);
    }
}
