import static org.junit.Assert.assertEquals;

import org.junit.Test;
import parsers.*;
import parsers.GithubUrlParser;
import parsers.UrlParser;
import parsers.parsedUrl.ParsedUrl;

public class LinkParserTest {

    @Test
    public void githubCheckTest() {
    // test github valid urls

        UrlParser parser = new GithubUrlParser(new StackOverflowUrlParser(null));

        String[] urls = {
                "https://github.com/sanyarnd/tinkoff-java-course-2022/",
                "https://github.com/PorterNight/hw1",
                "https://github.com/THUDM/ChatGLM-6B/",
                "https://github.com/AykutSarac/jsoncrack.com",
                "https://github.com/202252197/ChatGPT_JCM",
        };

        String[] answers = {
                "GithubParsedUrl[username=sanyarnd, repoName=tinkoff-java-course-2022]",
                "GithubParsedUrl[username=PorterNight, repoName=hw1]",
                "GithubParsedUrl[username=THUDM, repoName=ChatGLM-6B]",
                "GithubParsedUrl[username=AykutSarac, repoName=jsoncrack.com]",
                "GithubParsedUrl[username=202252197, repoName=ChatGPT_JCM]",
        };

        for (int i = 0; i < urls.length; i++) {
            ParsedUrl parsedLink = parser.parseLink(urls[i]);
            assertEquals(answers[i], parsedLink.toString());
        }
    }


    @Test
    public void stackOverflowCheckTest() {
    // test stackoverflow valid urls

        UrlParser parser = new GithubUrlParser(new StackOverflowUrlParser(null));

        String[] urls = {
                "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                "https://stackoverflow.com/questions/75764611/flutter-tabbar-wrap-on-overflow",
                "https://stackoverflow.com/questions/75764579/error-dagger-dependencycycle-found-dependency-cycle/",
        };

        String[] answers = {
                "StackOverflowParsedUrl[id=1642028]",
                "StackOverflowParsedUrl[id=75764611]",
                "StackOverflowParsedUrl[id=75764579]",
        };

        for (int i = 0; i < urls.length; i++) {
            ParsedUrl parsedLink = parser.parseLink(urls[i]);
            assertEquals(answers[i], parsedLink.toString());
        }
    }

    @Test
    public void nullCheckTest() {
    // test invalid urls

        UrlParser parser = new GithubUrlParser(new StackOverflowUrlParser(null));

        String[] urls = {
                "https://github.com/AykutSarac?jsoncrack.com",
                "https://github.com/202252197-ChatGPT_JCM",
                "https://stackoverflow.com/questions/75764579.error-dagger-dependencycycle-found-dependency-cycle/",
                "https://stackoverflow.com/search?q=unsupported%20link"
        };

        for (String url : urls) {
            ParsedUrl parsedLink = parser.parseLink(url);
            assertEquals(null, parsedLink);
        }
    }

}
