//package ru.tinkoff.edu.java.linkParser;
package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GithubUrlParser implements UrlParser {

    private final UrlParser nextParser;
    private final Pattern pattern;

    public GithubUrlParser(UrlParser nextParser) {
        this.nextParser = nextParser;
        this.pattern = Pattern.compile("https://github.com/([\\w-]+)/([\\w.-]+)/?");
    }

    public ParsedUrl parseLink(String url) {
        // check if the URL matches the GitHub pattern

        Matcher matcher = pattern.matcher(url);

        if (matcher.matches()) {
            String username = matcher.group(1);
            String repoName = matcher.group(2);
            return new GithubParsedUrl(username, repoName);
        }

        // if no match, go to the next parser
        if (nextParser != null) {
            return nextParser.parseLink(url);
        }

        return null;
    }
}
