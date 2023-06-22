package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "link_chat")
public class LinkChat {

    @EmbeddedId
    private LinkChatId id;

    @ManyToOne
    @JoinColumn(name = "link_id", insertable = false, updatable = false)
    private Link link;

    @ManyToOne
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    private Chat chat;

    public LinkChatId getId() {
        return id;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setId(LinkChatId id) {
        this.id = id;
    }
}
