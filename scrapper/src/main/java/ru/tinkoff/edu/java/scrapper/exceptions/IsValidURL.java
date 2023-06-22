package ru.tinkoff.edu.java.scrapper.exceptions;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class IsValidURL {

    public static boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
