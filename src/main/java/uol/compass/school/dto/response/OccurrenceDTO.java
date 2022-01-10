package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.CommunicationType;
import uol.compass.school.enums.OccurrenceType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OccurrenceDTO {

    private Long id;

    private LocalDate date;

    private String description;

    private CommunicationType communicationType;

    private OccurrenceType occurrenceType;

    private StudentDTO student;
}

	
	