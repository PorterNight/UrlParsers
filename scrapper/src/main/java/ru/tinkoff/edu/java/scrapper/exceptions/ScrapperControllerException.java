package ru.tinkoff.edu.java.scrapper.exceptions;

public class ScrapperControllerException extends RuntimeException {

    private int code;

    public ScrapperControllerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
