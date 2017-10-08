package com.pcbe.cell.setup;

public class CellConfigException extends RuntimeException {
    public CellConfigException(String message) {
        super("Failed to initialize cell: " + message);
    }
}