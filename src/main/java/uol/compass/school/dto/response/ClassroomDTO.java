package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.entity.Course;
import uol.compass.school.entity.Student;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassroomDTO {

    private Long id;

    private Boolean status;

    private LocalDate initialDate;

    private LocalDate finalDate;

    private Set<StudentNameDTO> students = new HashSet<>();

    private Set<CourseDTO> courses = new HashSet<>();
}
