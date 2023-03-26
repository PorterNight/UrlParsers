package ru.tinkoff.edu.java.scrapper.dto;

import ru.tinkoff.edu.java.scrapper.exceptions.IsValidURL;

public class RemoveLinkRequest {

    private String link;

    public String getLink() {
        return link;
    }

    public void setUrl(String url) {
        if (IsValidURL.isValidURL(url)){
            this.link = link;
        } else
            throw new IllegalArgumentException("Invalid URL: " + url);

    }


}
