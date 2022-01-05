package uol.compass.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.exception.dto.ErrorMessage;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResponseStatusException(ResponseStatusException e) {

        return ErrorMessage.builder()
                .status(e.getStatus())
                .message(e.getReason())
                .build();
    }
}
