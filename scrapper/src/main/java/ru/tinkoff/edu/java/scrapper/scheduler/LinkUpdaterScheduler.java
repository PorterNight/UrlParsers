package ru.tinkoff.edu.java.scrapper.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import parsers.GithubUrlParser;
import parsers.StackOverflowUrlParser;
import parsers.UrlParser;
import parsers.parsedUrl.GithubParsedUrl;
import parsers.parsedUrl.ParsedUrl;
import parsers.parsedUrl.StackOverflowParsedUrl;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcLinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.repository.JdbcListLinkWithTimeRepository;
import ru.tinkoff.edu.java.scrapper.jdbc.service.JdbcLinkBaseService;
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
public class LinkUpdaterScheduler {

    private static final Logger log = LoggerFactory.getLogger(LinkUpdaterScheduler.class);
    private final String interval;
    private final JdbcLinkBaseService jdbcLinkBaseService;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final ScrapperToBotClient scrapperToBotClient;

    @Autowired
    public LinkUpdaterScheduler(Environment env, JdbcLinkBaseService jdbcLinkBaseService, GitHubClient gitHubClient, StackOverflowClient stackOverflowClient, ScrapperToBotClient scrapperToBotClient) {
        this.interval = env.getProperty("scheduler.interval");
        this.jdbcLinkBaseService = jdbcLinkBaseService;
        this.gitHubClient = gitHubClient;
        this.stackOverflowClient = stackOverflowClient;
        this.scrapperToBotClient = scrapperToBotClient;
    }

    @Scheduled(fixedDelayString = "#{T(java.time.Duration).parse('${scheduler.interval}').toMillis()}")
    public void updateLinks() {

        log.info("Updating links from data base...");

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
    }

    private void updateGitHubLink(URI url, ParsedUrl parsedLink, OffsetDateTime newEventCreatedAt) {

        if (parsedLink instanceof GithubParsedUrl) {

            String owner = ((GithubParsedUrl) parsedLink).username();
            String repo = ((GithubParsedUrl) parsedLink).repoName();

            GitHubClientResponseDto repoResponse = gitHubClient.getListRepoEvents(owner, repo).blockLast();

            OffsetDateTime time = OffsetDateTime.parse(repoResponse.created_at());  // updated time from github


            if (newEventCreatedAt == null) {  // if no newEventCreatedAt time in table link
                jdbcLinkBaseService.addTime(url, time);
            } else {

                if (time.isBefore(newEventCreatedAt)) {  // there is a new update

                    jdbcLinkBaseService.addTime(url, time);
                    String description="";
                    if (repoResponse.payload().action().equals("started")) {         // repo achived a new star from some user
                        log.info("Github: new star achived");
                        description = "Github: new star achived";
                    } else if (repoResponse.payload().action().equals("created")) {  // a new commit comment event
                        log.info("Github: new commit comment event");
                        description = "Github: new commit comment eventd";
                    }

                    LinkUpdateNotifyRequestDto data = new LinkUpdateNotifyRequestDto(0, url, description, null);
                    scrapperToBotClient.sendTrackedLinkNotify(data).blockLast();
                }

                log.info("github newEventCreatedAt time : " + newEventCreatedAt.toString());
            }

            log.info("github created_at time : " + time.toString());


            System.out.println(repoResponse.payload().action() + " =:= " + repoResponse.id() + " =:= " + repoResponse.created_at() + " =:= " + repoResponse.type() + " =:= " + repoResponse.url());
        }

    }

    private void updateStackOverflowLink(URI url, ParsedUrl parsedLink, OffsetDateTime newEventCreatedAt) {

        if (parsedLink instanceof StackOverflowParsedUrl) {

            int id = ((StackOverflowParsedUrl) parsedLink).id();

            StackOverflowListAnswersDto repoResponse = stackOverflowClient.getAnswersByID(id).blockLast();

            OffsetDateTime time = getUnixtoOffsetDateTime(repoResponse.items().get(0).last_edit_date());  // updated time from stackOverflow


            if (newEventCreatedAt == null) {  // if no newEventCreatedAt time in table link
                jdbcLinkBaseService.addTime(url, time);
            } else {

                String description="";
                if (time.isAfter(newEventCreatedAt)) {  // there is a new update
                    jdbcLinkBaseService.addTime(url, time);
                    description="StackOverflow: new answer for a question";
                    log.info("StackOverflow: new answer for a question");

                    LinkUpdateNotifyRequestDto data = new LinkUpdateNotifyRequestDto(0, url, description, null);
                    scrapperToBotClient.sendTrackedLinkNotify(data).blockLast();

                }

                log.info("stackOverflow newEventCreatedAt time : " + newEventCreatedAt.toString());
            }

            log.info("stackOverflow created_at time : " + time.toString());


            System.out.println(repoResponse.items().get(0).last_edit_date() + " =:= " + repoResponse.items().get(0).question_id() + " =:= " + repoResponse.items().get(0).answer_id());
        }
    }
}