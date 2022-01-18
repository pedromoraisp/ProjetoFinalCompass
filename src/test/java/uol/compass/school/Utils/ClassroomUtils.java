package uol.compass.school.Utils;

import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.ClassroomDTO;
import uol.compass.school.entity.Classroom;

import java.time.LocalDate;
import java.util.Collections;

public class ClassroomUtils {

    public static Classroom createClassroom() {
        return Classroom.builder()
                .id(1L)
                .finalDate(LocalDate.now())
                .finalDate(LocalDate.now())
                .status(true)
                .build();
    }

    public static ClassroomDTO createClassroomDTO() {
        return ClassroomDTO.builder()
                .id(1L)
                .finalDate(LocalDate.now())
                .finalDate(LocalDate.now())
                .status(true)
                .build();
    }

    public static ClassroomRequestDTO createClassroomRequestDTO() {
        return ClassroomRequestDTO.builder()
                .status(Boolean.TRUE)
                .initialDate(LocalDate.now())
                .finalDate(LocalDate.now())
                .build();
    }

    public static Classroom createClassroomWithStudentsAndCourses() {
        return Classroom.builder()
                .id(1L)
                .finalDate(LocalDate.now())
                .finalDate(LocalDate.now())
                .status(true)
                .students(Collections.singleton(StudentUtils.createStudent()))
                .courses(Collections.singleton(CourseUtils.createCourse()))
                .build();
    }
}
