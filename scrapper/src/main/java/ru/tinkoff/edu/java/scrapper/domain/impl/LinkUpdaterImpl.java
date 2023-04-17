package ru.tinkoff.edu.java.scrapper.domain.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import parsers.GithubUrlParser;
import parsers.StackOverflowUrlParser;
import parsers.UrlParser;
import parsers.parsedUrl.GithubParsedUrl;
import parsers.parsedUrl.ParsedUrl;
import parsers.parsedUrl.StackOverflowParsedUrl;
import ru.tinkoff.edu.java.scrapper.domain.LinkBaseService;
import ru.tinkoff.edu.java.scrapper.domain.LinkUpdater;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcLinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.domain.jdbc.repository.JdbcListLinkWithTimeRepository;
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
    private final LinkBaseService jdbcLinkBaseService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final ScrapperToBotClient scrapperToBotClient;

    @Autowired
    public LinkUpdaterImpl(LinkBaseService jdbcLinkBaseService, GitHubClient gitHubClient, StackOverflowClient stackOverflowClient, ScrapperToBotClient scrapperToBotClient) {
        this.jdbcLinkBaseService = jdbcLinkBaseService;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.scrapperToBotClient = scrapperToBotClient;
    }

    @Override
    public int updateLinks() {

        JdbcListLinkWithTimeRepository res = jdbcLinkBaseService.findAllFilteredByTimeout(1);  // get only links were added more than N minites ago

        Arrays.stream(res.linksWithTime()).forEach(link -> log.info(link.url().toString() + " : " + link.newEventCreatedAt()));

        if (res.size() > 0) {  // there are unupdated links

            for (JdbcLinkWithTimeRepository i : res.linksWithTime()) {

                URI link = i.url();

                UrlParser parser = new GithubUrlParser(new StackOverflowUrlParser(null));
                ParsedUrl parsedLink = parser.parseLink(link.toString());

                updateGitHubLink(link, parsedLink, i.newEventCreatedAt());
                updateStackOverflowLink(link, parsedLink, i.newEventCreatedAt());

            }
        }
        return 0;
    }

    private void updateGitHubLink(URI url, ParsedUrl parsedLink, OffsetDateTime updateTimeFromDB) {

        if (parsedLink instanceof GithubParsedUrl) {

            String owner = ((GithubParsedUrl) parsedLink).username();
            String repo = ((GithubParsedUrl) parsedLink).repoName();

            GitHubClientResponseDto repoResponse = gitHubClient.getListRepoEvents(owner, repo).blockLast();

            OffsetDateTime updateTimeFromInternet = OffsetDateTime.parse(repoResponse.created_at());  // updated time from github

            log.info("updateGitHubLink:  updateTimeFromDB:" + updateTimeFromDB + "<>" + "updateTimeFromInternet:" + updateTimeFromInternet);

            if (updateTimeFromDB == null) {  // if no newEventCreatedAt time in table link
                jdbcLinkBaseService.addTime(url, updateTimeFromInternet);
            } else {

                if (updateTimeFromInternet.isAfter(updateTimeFromDB)) {  // there is a new update

                    jdbcLinkBaseService.addTime(url, updateTimeFromInternet);

                    String description = "";
                    if (repoResponse.payload().action().equals("started")) {        // repo achived a new star from some user
                        description += "Github: new star achived ";
                    }
                    if (repoResponse.payload().action().equals("created")) {        // a new commit comment event
                        description += "Github: new commit comment eventd";
                    }

                    if (!description.isEmpty()) {
                        log.info(description);

                        LinkUpdateNotifyRequestDto data = new LinkUpdateNotifyRequestDto(0, url, description, null);
                        scrapperToBotClient.sendTrackedLinkNotify(data).blockLast();
                    }
                }

                log.info("github updateTimeFromDB time : " + updateTimeFromDB);
            }
        }
    }

    private void updateStackOverflowLink(URI url, ParsedUrl parsedLink, OffsetDateTime updateTimeFromDB) {

        if (parsedLink instanceof StackOverflowParsedUrl) {

            int id = ((StackOverflowParsedUrl) parsedLink).id();

            StackOverflowListAnswersDto repoResponse = stackOverflowClient.getAnswersByID(id).blockLast();

            OffsetDateTime updateTimeFromInternet = getUnixtoOffsetDateTime(repoResponse.items().get(0).last_edit_date());  // updated time from stackOverflow

            if (updateTimeFromDB == null) {  // if no newEventCreatedAt time in table link
                jdbcLinkBaseService.addTime(url, updateTimeFromInternet);
            } else {

                String description = "";
                if (updateTimeFromInternet.isAfter(updateTimeFromDB)) {  // there is a new update

                    jdbcLinkBaseService.addTime(url, updateTimeFromInternet);

                    description = "StackOverflow: new answer for a question";
                    log.info(description);

                    LinkUpdateNotifyRequestDto data = new LinkUpdateNotifyRequestDto(0, url, description, null);
                    scrapperToBotClient.sendTrackedLinkNotify(data).blockLast();

                }
            }
        }
    }


}
