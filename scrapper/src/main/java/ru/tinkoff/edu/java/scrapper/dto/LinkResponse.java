package ru.tinkoff.edu.java.scrapper.dto;

import ru.tinkoff.edu.java.scrapper.exceptions.IsValidURL;

public class LinkResponse {

    private long id;
    private String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (IsValidURL.isValidURL(url)){
            this.url = url;
        } else
            throw new IllegalArgumentException("Invalid URI: " + url);

    }

}
