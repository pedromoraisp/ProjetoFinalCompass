package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.entity.Classroom;
import uol.compass.school.entity.Course;
import uol.compass.school.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducatorDTO {

    private Long id;

    private String name;

    private Gender gender;

    private String cpf;

    private CourseDTO course;
}
