package uol.compass.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.school.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByNameStartingWith(String name);
}
