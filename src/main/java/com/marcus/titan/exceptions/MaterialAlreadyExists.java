package com.marcus.titan.exceptions;

public class MaterialAlreadyExists extends RuntimeException {
    public MaterialAlreadyExists() {
        super("The supply already exists!");
    }
}
