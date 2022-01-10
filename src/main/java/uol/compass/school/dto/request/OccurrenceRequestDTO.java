package uol.compass.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.CommunicationType;
import uol.compass.school.enums.OccurrenceType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OccurrenceRequestDTO {

    @NotNull
    private LocalDate date;

    @NotBlank
    private String description;

    @NotNull
    private CommunicationType communicationType;

    @NotNull
    private OccurrenceType occurrenceType;
}
