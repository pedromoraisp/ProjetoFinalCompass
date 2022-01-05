package uol.compass.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.school.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameStartingWith(String name);
}
