package com.sparta.aw.libraryapp.model.exceptions;

//JSON Body of our ResponseEntity
public record AuthorNotFoundResponse(String url, int statusCode, String message) {}