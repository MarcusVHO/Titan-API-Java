package com.marcus.titan.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {super("User not found!");}

    public UserNotFoundException(String message) {super(message);}
}
