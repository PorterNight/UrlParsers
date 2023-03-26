package ru.tinkoff.edu.java.scrapper.dto;

public class ApiErrorResponse {

    private String description="Некорректные параметры запроса";
    private String code="";
    private String exceptionName="";
    private String exceptionMessage="";
    private String[] stacktrace;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String[] getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String[] stacktrace) {
        this.stacktrace = stacktrace;
    }



}
