package ru.tinkoff.edu.java.scrapper.domain.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkChat;
import ru.tinkoff.edu.java.scrapper.domain.jpa.entity.LinkChatId;
import java.util.List;

@Repository
public interface JpaLinkChatRepository extends JpaRepository<LinkChat, LinkChatId> {
    List<LinkChat> findAllByChat_ChatId(Long chatId);
    List<LinkChat> findAllByLink_Id(Long linkId);
    List<LinkChat> findByChat_ChatIdAndLink_Id(Long chatId, Long linkId);
    List<LinkChat> findByChat_ChatId(Long chatId);

}
