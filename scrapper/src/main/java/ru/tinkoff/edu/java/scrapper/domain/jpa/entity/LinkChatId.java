package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class LinkChatId implements Serializable {

    @Column(name = "link_id")
    private Long linkId;

    @Column(name = "chat_id")
    private Long chatId;

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
