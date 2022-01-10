package uol.compass.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.school.entity.Educator;

import java.util.List;

public interface EducatorRepository extends JpaRepository<Educator, Long> {
    List<Educator> findByNameStartingWith(String name);
}
