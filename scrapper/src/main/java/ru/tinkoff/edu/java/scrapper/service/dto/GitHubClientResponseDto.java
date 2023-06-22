package ru.tinkoff.edu.java.scrapper.service.dto;

public record GitHubClientResponseDto(Long id, String url, String type, PayloadRecord payload, String created_at){}
