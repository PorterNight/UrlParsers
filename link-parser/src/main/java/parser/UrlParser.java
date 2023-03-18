//package ru.tinkoff.edu.java.linkParser;
package parser;

public sealed interface UrlParser permits GithubUrlParser, StackOverflowUrlParser {
    ParsedUrl parseLink(String url);
}
