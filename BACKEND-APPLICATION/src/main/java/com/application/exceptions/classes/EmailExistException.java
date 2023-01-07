package com.application.exceptions.classes;

public class EmailExistException extends Exception{
    public EmailExistException(String message) {
        super(message);
    }
}
