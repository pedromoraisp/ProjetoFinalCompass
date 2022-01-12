package uol.compass.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.school.entity.Classroom;

import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    List<Classroom> findAllByStatus(Boolean status);
}
