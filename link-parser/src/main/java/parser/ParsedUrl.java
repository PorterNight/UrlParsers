//package ru.tinkoff.edu.java.linkParser;
package parser;

public sealed interface ParsedUrl permits GithubParsedUrl, StackOverflowParsedUrl {}
