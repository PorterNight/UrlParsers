package ru.tinkoff.edu.java.bot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateErrorResponse;

@RestControllerAdvice
public class BotControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BotControllerException.class})
    public ResponseEntity<LinkUpdateErrorResponse> botClientException(BotControllerException ex) {

        LinkUpdateErrorResponse errorResponse = new LinkUpdateErrorResponse(
                "Некорректные параметры запроса",                 // String description,
                String.valueOf(ex.getCode()),    // String code,
                ex.getClass().toString(),        // String exceptionName,
                ex.getMessage(),                 // String exceptionMessage,
                null                             // List<String> stacktrace
        );

        return ResponseEntity.status(ex.getCode()).body(errorResponse);
    }
}
