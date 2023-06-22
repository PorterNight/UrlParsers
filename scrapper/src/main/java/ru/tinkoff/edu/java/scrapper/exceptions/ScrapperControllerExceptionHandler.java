package ru.tinkoff.edu.java.scrapper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

@RestControllerAdvice
public class ScrapperControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ScrapperControllerException.class})
    public ResponseEntity<ApiErrorResponse> botClientException(ScrapperControllerException ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "Некорректные параметры запроса",                 // String description,
                String.valueOf(ex.getCode()),    // String code,
                ex.getClass().toString(),        // String exceptionName,
                ex.getMessage(),                 // String exceptionMessage,
                null                             // List<String> stacktrace
        );

        return ResponseEntity.status(ex.getCode()).body(errorResponse);
    }
}
