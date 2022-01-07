package uol.compass.school.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uol.compass.school.entity.Occurrence;
import uol.compass.school.entity.Student;

public interface OccurrenceRepository extends JpaRepository<Occurrence, Long>{
	List<Occurrence> findByDateBetween(LocalDate initialDate,LocalDate finalDate);
	
}
