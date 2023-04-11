package parsers;

import parsers.parsedUrl.ParsedUrl;

public sealed interface UrlParser permits GithubUrlParser, StackOverflowUrlParser {
    ParsedUrl parseLink(String url);
}
