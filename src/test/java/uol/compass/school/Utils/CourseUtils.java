package uol.compass.school.Utils;

import uol.compass.school.dto.request.CourseRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.entity.Course;

public class CourseUtils {

    public static Course createCourse(){
        return Course.builder()
                .id(1L)
                .name("Music")
                .build();
    }

    public static CourseDTO createCourseDTO(){
        return CourseDTO.builder()
                .id(1L)
                .name("Music")
                .build();
    }

    public static CourseRequestDTO createCourseRequestDTO(){
        return CourseRequestDTO.builder()
                .name("Music")
                .build();
    }
}
