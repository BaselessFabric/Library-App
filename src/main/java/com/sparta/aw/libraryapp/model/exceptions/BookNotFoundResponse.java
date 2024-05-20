package com.sparta.aw.libraryapp.model.exceptions;

public record BookNotFoundResponse(String url, int statusCode, String message) {}