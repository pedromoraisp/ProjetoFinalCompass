package uol.compass.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.exception.dto.ErrorMessageDTO;
import uol.compass.school.exception.dto.MethodNotAllowedDTO;
import uol.compass.school.exception.dto.RequestValidationErrorDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDTO handleResponseStatusException(ResponseStatusException exception) {
        return ErrorMessageDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(exception.getStatus())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<RequestValidationErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<RequestValidationErrorDTO> requestValidationErrorsDTO = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            RequestValidationErrorDTO requestValidationErrorDTO = new RequestValidationErrorDTO(e.getField(), e.getDefaultMessage());
            requestValidationErrorsDTO.add(requestValidationErrorDTO);
        });

        return requestValidationErrorsDTO;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDTO handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ErrorMessageDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public MethodNotAllowedDTO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return MethodNotAllowedDTO.builder()
                .timestamp(LocalDateTime.now())
                .method(exception.getMethod())
                .message(exception.getMessage())
                .build();
    }
}