package uol.compass.school.Utils;

import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.dto.response.OccurrenceToStudentDTO;
import uol.compass.school.entity.Occurrence;
import uol.compass.school.enums.CommunicationType;
import uol.compass.school.enums.OccurrenceType;

import java.time.LocalDate;

public class OccurrenceUtils {

    public static Occurrence createOccurrence() {
        return Occurrence.builder()
                .id(1L)
                .communicationType(CommunicationType.ESCRITO)
                .occurrenceType(OccurrenceType.DESOBEDIENCIA)
                .date(LocalDate.now())
                .description("Se negou a ficar sentado")
                .build();
    }

    public static OccurrenceDTO createOccurrenceDTO() {
        return OccurrenceDTO.builder()
                .id(1L)
                .communicationType(CommunicationType.ESCRITO)
                .occurrenceType(OccurrenceType.DESOBEDIENCIA)
                .date(LocalDate.now())
                .description("Se negou a ficar sentado")
                .build();
    }

    public static OccurrenceRequestDTO createOccurrenceRequestDTO() {
        return OccurrenceRequestDTO.builder()
                .communicationType(CommunicationType.ESCRITO)
                .occurrenceType(OccurrenceType.DESOBEDIENCIA)
                .date(LocalDate.now())
                .description("Se negou a ficar sentado")
                .build();
    }

    public static OccurrenceToStudentDTO createOccurrenceToStudentDTO() {
        return OccurrenceToStudentDTO.builder()
                .date(LocalDate.now())
                .description("Se negou a ficar sentado")
                .communicationType(CommunicationType.ESCRITO)
                .occurrenceType(OccurrenceType.DESOBEDIENCIA)
                .build();
    }
}
