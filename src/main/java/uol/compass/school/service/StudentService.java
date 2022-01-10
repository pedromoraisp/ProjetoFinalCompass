package uol.compass.school.service;

import uol.compass.school.dto.request.StudentRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.dto.response.StudentDTO;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {

    MessageResponseDTO create(StudentRequestDTO studentRequestDTO);

    List<StudentDTO> findAll(String name);

    StudentDTO findById(Long id);

    MessageResponseDTO update(Long id, StudentRequestDTO studentRequestDTO);

    MessageResponseDTO deleteById(Long id);

    List<OccurrenceDTO> findAllOccurrences(Long id, LocalDate initialDate, LocalDate finalDate);
}
