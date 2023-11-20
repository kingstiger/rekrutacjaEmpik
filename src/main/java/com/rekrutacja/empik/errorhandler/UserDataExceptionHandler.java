package com.rekrutacja.empik.errorhandler;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.rekrutacja.empik.model.ErrorMessage;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Date;
import java.time.Instant;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class UserDataExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFoundException(UserNotFoundException ex, WebRequest request) {
        return ErrorMessage.builder()
                .statusCode(404)
                .timestamp(Date.from(Instant.now()))
                .message(String.format("User not found for request %s", ((ServletWebRequest)request).getRequest().getRequestURI()))
                .build();
    }

    @ExceptionHandler(value = {GitHubServiceException.class, UnknownServiceError.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage serviceErrorException(GitHubServiceException ex, WebRequest request) {
        return ErrorMessage.builder()
                .statusCode(503)
                .timestamp(Date.from(Instant.now()))
                .message(String.format("Error %s for request %s", ex.getMessage(), ((ServletWebRequest)request).getRequest().getRequestURI()))
                .build();
    }

    @ExceptionHandler(value = {WrongLoginException.class, RestClientException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public <T extends RuntimeException> ErrorMessage wrongLoginException(T ex, WebRequest request) {

        if(ex instanceof RestClientException && !(((RestClientException) ex).getRootCause() instanceof MismatchedInputException)) {
            throw new UnknownServiceError("Unknown error while processing request");
        }

        return ErrorMessage.builder()
                .statusCode(400)
                .timestamp(Date.from(Instant.now()))
                .message(String.format("Wrong login for lookup was provided for request %s", ((ServletWebRequest) request).getRequest().getRequestURI()))
                .build();

    }

    @ExceptionHandler(CalculationInfiniteException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage wrongLoginException(CalculationInfiniteException ex, WebRequest request) {
        return ErrorMessage.builder()
                .statusCode(422)
                .timestamp(Date.from(Instant.now()))
                .message(String.format("Calculations resulted in infinity for request %s", ((ServletWebRequest)request).getRequest().getRequestURI()))
                .build();
    }
}
