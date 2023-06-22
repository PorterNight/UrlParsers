package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
