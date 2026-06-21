package com.dhaval.library;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(long id) {
        super("Book not found with ID: " + id);
    }
}
