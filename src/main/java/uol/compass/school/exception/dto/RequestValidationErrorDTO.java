package uol.compass.school.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RequestValidationErrorDTO {

    private String field;
    private String message;
}
