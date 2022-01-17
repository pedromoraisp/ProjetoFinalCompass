package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.entity.Classroom;
import uol.compass.school.entity.Responsible;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {

    private Long id;

    private String name;

    private Gender gender;

    private String cpf;

    private String identityCard;

    private LocalDate birthdate;

    private AddressDTO address;

    private EducationalLevel educationalLevel;

    private String placeOfBirth;

    private String school;

    private Set<ResponsibleNameDTO> responsible;

    private List<ClassroomDTO> classrooms = new ArrayList<>();
}
