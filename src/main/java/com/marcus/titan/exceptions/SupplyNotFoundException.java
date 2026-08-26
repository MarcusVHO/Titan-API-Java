package com.marcus.titan.exceptions;

public class SupplyNotFoundException extends RuntimeException {
    public SupplyNotFoundException() {
        super("Supply not found!");
    }
}
