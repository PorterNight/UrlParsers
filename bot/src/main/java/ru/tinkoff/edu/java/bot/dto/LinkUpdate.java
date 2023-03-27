package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.Valid;

import java.util.List;

public record LinkUpdate(

        long id,
        String url,
        String description,
        List<Long> tgChatIds
) {}

//public class LinkUpdate {
//
//    private long id;
//    private String url;
//    private String description;
//    private List<Long> tgChatIds;
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public List<Long> getTgChatIds() {
//        return tgChatIds;
//    }
//    public void setTgChatIds(List<Long> tgChatIds) {
//        this.tgChatIds = tgChatIds;
//    }
//
//    @Override
//    public String toString() {
//        return "LinkUpdate{" +
//                "id=" + id +
//                ", url='" + url + '\'' +
//                ", description='" + description + '\'' +
//                ", tgChatIds=" + tgChatIds +
//                '}';
//    }
//}
