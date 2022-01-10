package uol.compass.school.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.exception.dto.ErrorMessage;
import uol.compass.school.exception.dto.RequestValidationErrorDTO;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage handleResponseStatusException(ResponseStatusException exception) {

        return ErrorMessage.builder()
                .status(exception.getStatus())
                .message(exception.getReason())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<RequestValidationErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<RequestValidationErrorDTO> requestValidationErrorDTOs = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {

            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            RequestValidationErrorDTO requestValidationErrorDTO = new RequestValidationErrorDTO(e.getField(), message);

            requestValidationErrorDTOs.add(requestValidationErrorDTO);
        });

        return requestValidationErrorDTOs;
    }
}