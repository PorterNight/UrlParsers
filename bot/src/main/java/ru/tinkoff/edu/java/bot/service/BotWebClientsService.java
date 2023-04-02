package ru.tinkoff.edu.java.bot.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dto.GitHubFetchedData;
import ru.tinkoff.edu.java.scrapper.dto.StackOverFlowFetchedData;
import ru.tinkoff.edu.java.scrapper.service.dto.GitHubClientResponseDto;
import ru.tinkoff.edu.java.scrapper.service.dto.StackOverflowInfoDto;

import java.time.OffsetDateTime;

import static ru.tinkoff.edu.java.scrapper.service.UnixToOffsetDateTime.getUnixtoOffsetDateTime;

@Service
public class BotWebClientsService {

    public StackOverFlowFetchedData getStackOverflowInfo(StackOverflowInfoDto info) {

        OffsetDateTime creation_date_offset = getUnixtoOffsetDateTime(info.items().get(0).creation_date());

        return new StackOverFlowFetchedData(
                info.items().get(0).link(),
                info.items().get(0).title(),
                info.items().get(0).tags(),
                creation_date_offset);
    }

    public GitHubFetchedData getGitHubInfo(GitHubClientResponseDto info) {

        OffsetDateTime creation_date_offset = OffsetDateTime.parse(info.created_at());

        return new GitHubFetchedData(
                info.id(),
                info.type(),
                creation_date_offset);
    }

}
