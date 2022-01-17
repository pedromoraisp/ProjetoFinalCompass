package uol.compass.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.school.entity.Coordinator;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
}
