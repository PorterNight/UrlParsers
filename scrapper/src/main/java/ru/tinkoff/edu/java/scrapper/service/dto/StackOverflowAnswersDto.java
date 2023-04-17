package ru.tinkoff.edu.java.scrapper.service.dto;

public record StackOverflowAnswersDto(Long score, String question_id, String answer_id, long last_edit_date) {}

