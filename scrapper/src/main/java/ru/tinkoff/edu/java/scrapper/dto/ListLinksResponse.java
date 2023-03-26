package ru.tinkoff.edu.java.scrapper.dto;

public class ListLinksResponse {

    private LinkResponse[] links;
    private int size=0;

    public LinkResponse[] getLinks() {
        return links;
    }

    public void setLinks(LinkResponse[] links) {
        this.links = links;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
