package com.sparta.aw.libraryapp.model.exceptions;

public class BookNotFoundException extends Exception{
    public BookNotFoundException(String bookTitle) {
        super("Could not find book: " + bookTitle);
    }
}
