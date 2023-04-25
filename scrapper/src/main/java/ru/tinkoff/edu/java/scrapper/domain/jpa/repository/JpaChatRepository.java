package ru.tinkoff.edu.java.scrapper.domain.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.Chat;

import java.util.Optional;

@Repository
public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findById(long tgChatId);
}
