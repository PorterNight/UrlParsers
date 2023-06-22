package ru.tinkoff.edu.java.bot.exceptions;

public class BotControllerException extends RuntimeException {

    private int code;

    public BotControllerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
