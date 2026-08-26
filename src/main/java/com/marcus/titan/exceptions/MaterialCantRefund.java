package com.marcus.titan.exceptions;

public class MaterialCantRefund extends RuntimeException {
    public MaterialCantRefund() {
        super("Material can't refund!");
    }
}
