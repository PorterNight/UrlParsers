package ru.tinkoff.edu.java.scrapper.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import parsers.GithubUrlParser;
import parsers.StackOverflowUrlParser;
import parsers.UrlParser;
import parsers.parsedUrl.GithubParsedUrl;
import parsers.parsedUrl.ParsedUrl;
import parsers.parsedUrl.StackOverflowParsedUrl;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.domain.repository.LinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.domain.repository.ListLinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.scheduler.LinkUpdaterScheduler;
import ru.tinkoff.edu.java.scrapper.service.dto.GitHubClientResponseDto;
import ru.tinkoff.edu.java.scrapper.service.dto.LinkUpdateNotifyRequestDto;
import ru.tinkoff.edu.java.scrapper.service.dto.StackOverflowListAnswersDto;
import ru.tinkoff.edu.java.scrapper.webclients.GitHubClient;
import ru.tinkoff.edu.java.scrapper.webclients.ScrapperToBotClient;
import ru.tinkoff.edu.java.scrapper.webclients.StackOverflowClient;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Arrays;

import static ru.tinkoff.edu.java.scrapper.service.UnixToOffsetDateTime.getUnixtoOffsetDateTime;

@Component
public class LinkUpdaterImpl implements LinkUpdater {

    private static final Logger log = LoggerFactory.getLogger(LinkUpdaterScheduler.class);
    private final LinkBaseService linkBaseService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final ScrapperNotifierService scrapperNotifierService;

    @Value("${link.timeout.minutes}")
    private int linkTimeoutMinutes;

    @Autowired
    public LinkUpdaterImpl(LinkBaseService linkBaseService, GitHubClient gitHubClient, StackOverflowClient stackOverflowClient, ScrapperNotifierService scrapperNotifierService) {
        this.linkBaseService = linkBaseService;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.scrapperNotifierService = scrapperNotifierService;
    }

    @Override
    public int updateLinks() {


        ListLinkWithTimeRepository res = linkBaseService.findAllFilteredByTimeout(linkTimeoutMinutes);  // get only links were added more than N minites ago;

        Arrays.stream(res.linksWithTime()).forEach(link -> log.info(link.url().toString() + " : " + link.newEventCreatedAt()));

        if (res.size() > 0) {  // there are unupdated links

            for (LinkWithTimeRepository db_data : res.linksWithTime()) {

                UrlParser parser = new GithubUrlParser(new StackOverflowUrlParser(null));
                ParsedUrl parsedLink = parser.parseLink(db_data.url().toString());

                updateGitHubLink(parsedLink, db_data);
                updateStackOverflowLink(parsedLink, db_data);

            }
        }
        return 0;
    }



    private void updateLink(LinkWithTimeRepository db_data, String descriptionPrefix, OffsetDateTime updateTimeFromInternet) {

        OffsetDateTime updateTimeFromDB = db_data.newEventCreatedAt();
        URI url = db_data.url();

        if (updateTimeFromDB == null) { // if no newEventCreatedAt time in table link
            linkBaseService.addTime(url, updateTimeFromInternet);
        } else {
            //if (updateTimeFromInternet.isBefore(updateTimeFromDB) || updateTimeFromInternet.isEqual(updateTimeFromDB)) { // there is a new update
            if (updateTimeFromInternet.isAfter(updateTimeFromDB)) { // there is a new update
                linkBaseService.addTime(url, updateTimeFromInternet);

                String description = descriptionPrefix;
                log.info(description);

                LinkUpdateNotifyRequestDto data = new LinkUpdateNotifyRequestDto(db_data.url_id(), url, description, db_data.tgChatIds());
                scrapperNotifierService.send(data);
            }
        }
    }

    private void updateGitHubLink(ParsedUrl parsedLink, LinkWithTimeRepository db_data) {

        if (parsedLink instanceof GithubParsedUrl) {

            String owner = ((GithubParsedUrl) parsedLink).username();
            String repo = ((GithubParsedUrl) parsedLink).repoName();

            GitHubClientResponseDto repoResponse = gitHubClient.getListRepoEvents(owner, repo).blockLast();
            OffsetDateTime updateTimeFromInternet = OffsetDateTime.parse(repoResponse.created_at()); // updated time from github

            String descriptionPrefix = "";
            if (repoResponse.payload().action().equals("started")) { // repo achieved a new star from some user
                descriptionPrefix += "Github: репозиторий получил новую звезду";
            }
            if (repoResponse.payload().action().equals("created")) { // a new commit comment event
                descriptionPrefix += "Github: новый комментарий к commit";
            }
            if (!descriptionPrefix.isEmpty()) {
                updateLink(db_data, descriptionPrefix, updateTimeFromInternet);
            }
        }
    }

    private void updateStackOverflowLink(ParsedUrl parsedLink, LinkWithTimeRepository db_data) {

        if (parsedLink instanceof StackOverflowParsedUrl) {
            int id = ((StackOverflowParsedUrl) parsedLink).id();

            StackOverflowListAnswersDto repoResponse = stackOverflowClient.getAnswersByID(id).blockLast();
            OffsetDateTime updateTimeFromInternet = getUnixtoOffsetDateTime(repoResponse.items().get(0).last_edit_date()); // updated time from stackOverflow

            String descriptionPrefix = "StackOverflow: новый ответ на вопрос";
            updateLink(db_data, descriptionPrefix, updateTimeFromInternet);
        }
    }
}
