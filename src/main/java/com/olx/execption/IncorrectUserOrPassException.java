package com.olx.execption;

public class IncorrectUserOrPassException  extends RuntimeException{

    public IncorrectUserOrPassException(String message) {
        super(message);
    }

    public IncorrectUserOrPassException(String message, Throwable cause) {
        super(message, cause);
    }
}
