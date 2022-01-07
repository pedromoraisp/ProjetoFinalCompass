package uol.compass.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.school.entity.Responsible;


import java.util.List;

public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {
    List<Responsible> findByNameStartingWith(String name);
}