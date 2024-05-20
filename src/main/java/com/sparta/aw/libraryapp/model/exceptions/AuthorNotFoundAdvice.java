package com.sparta.aw.libraryapp.model.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AuthorNotFoundAdvice {

    @ExceptionHandler(AuthorNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<AuthorNotFoundResponse> authorNotFoundHandler(AuthorNotFoundException e, HttpServletRequest request) {  // object that represents a full HTTP response
        AuthorNotFoundResponse response = new AuthorNotFoundResponse(e.getMessage(), 400, request.getRequestURL().toString());
        return ResponseEntity.badRequest().body(response);  // functional programming - ResponseEntity now holds the badrequestcode, and the error message
    }
}
