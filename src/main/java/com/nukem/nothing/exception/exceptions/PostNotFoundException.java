package com.nukem.nothing.exception.exceptions;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(Long id) {
        super("Could not find post with id:" + id);
    }
}
