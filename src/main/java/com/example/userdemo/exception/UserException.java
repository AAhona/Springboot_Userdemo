package com.example.userdemo.exception;

import org.springframework.http.HttpStatus;

public class UserException {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus htttpStatus;

    //Constructors
    public UserException(String message, Throwable throwable, HttpStatus htttpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.htttpStatus = htttpStatus;
    }

    //Getters
    public HttpStatus getHtttpStatus() {
        return htttpStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public String getMessage() {
        return message;
    }

}
