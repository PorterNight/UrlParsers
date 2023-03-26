package ru.tinkoff.edu.java.scrapper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

@RestControllerAdvice
public class ScrapperControllerExceptionHandler {

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequestException(BadRequestException ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setExceptionName(ex.getClass().toString());
        errorResponse.setExceptionMessage(ex.getMessage().toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // 400
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(NotFoundException ex) {

        ApiErrorResponse errorResponse = new ApiErrorResponse();
        errorResponse.setExceptionName(ex.getClass().toString());
        errorResponse.setExceptionMessage(ex.getMessage().toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND); // 404
    }

}
