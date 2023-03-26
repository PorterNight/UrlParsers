package ru.tinkoff.edu.java.bot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateErrorResponse;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class BotControllerExceptionHandler {

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<LinkUpdateErrorResponse> handleNoSuchElementException(NoSuchElementException ex) {
        LinkUpdateErrorResponse errorResponse = new LinkUpdateErrorResponse();
        errorResponse.setExceptionMessage(ex.getMessage());
        errorResponse.setExceptionName(ex.getClass().toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse); // 404
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<LinkUpdateErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        LinkUpdateErrorResponse errorResponse = new LinkUpdateErrorResponse();
        errorResponse.setExceptionMessage(ex.getMessage());
        errorResponse.setExceptionName(ex.getClass().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);  // 400
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<LinkUpdateErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        LinkUpdateErrorResponse errorResponse = new LinkUpdateErrorResponse();
        errorResponse.setExceptionMessage(ex.getMessage());
        errorResponse.setExceptionName(ex.getClass().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);  // 400
    }

}
