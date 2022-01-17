package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentOccurrenceDTO {

    private Long id;

    private String name;

    private List<OccurrenceToStudentDTO> occurrences;
}
