package uol.compass.school.Utils;

import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.entity.Occurrence;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;
import lombok.Builder;
import uol.compass.school.enums.CommunicationType;
import uol.compass.school.enums.OccurrenceType;
import uol.compass.school.entity.Occurrence.OccurrenceBuilder;

import java.time.LocalDate;

public class OccurrenceUtils {

    public static Occurrence createOccurrence() {
        return Occurrence.builder()
                .id(1L)
                .student("Pedro Henrique")
                .communicationType(OccurrenceType.DESOBEDIENCIA)
                .date(LocalDate.now())
                .description("Se negou a ficar sentado")
                .build();
    }

    public static OccurrenceDTO createOccurrenceDTO() {
        return OccurrenceDTO.builder()
                .id(1L)
                .student("Pedro Henrique")
                .communicationType(OccurrenceType.DESOBEDIENCIA)
                .date(LocalDate.now())
                .description("Se negou a ficar sentado")
                .build();
    }

    public static OccurrenceRequestDTO createOccurrenceRequestDTO() {
        return OccurrenceRequestDTO.builder()
        		.student("Pedro Henrique")
                .communicationType(OccurrenceType.DESOBEDIENCIA)
                .date(LocalDate.now())
                .description("Se negou a ficar sentado")
                .build();
    }
}
