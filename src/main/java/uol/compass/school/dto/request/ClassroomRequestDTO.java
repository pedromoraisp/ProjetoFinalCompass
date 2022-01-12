package uol.compass.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomRequestDTO {

    @NotNull
    private Boolean status;

    @NotNull
    private LocalDate initialDate;

    @NotNull
    private LocalDate finalDate;
}
