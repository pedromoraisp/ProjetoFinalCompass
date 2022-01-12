package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomDTO {

    private Long id;

    private Boolean status;

    private LocalDate initialDate;

    private LocalDate finalDate;
}
