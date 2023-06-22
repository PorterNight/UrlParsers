package ru.tinkoff.edu.java.scrapper.domain.jpa.entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "new_event_created_at")
    private OffsetDateTime newEventCreatedAt;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OffsetDateTime getNewEventCreatedAt() {
        return newEventCreatedAt;
    }

    public void setNewEventCreatedAt(OffsetDateTime newEventCreatedAt) {
        this.newEventCreatedAt = newEventCreatedAt;
    }

    public Long getId() {
        return id;
    }
}
