package uol.compass.school.service;

import uol.compass.school.dto.request.StudentFormDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.StudentDTO;

import java.util.List;

public interface StudentService {

    MessageResponseDTO create(StudentFormDTO studentFormDTO);

    List<StudentDTO> findAll(String name);

    StudentDTO findById(Long id);

    MessageResponseDTO update(Long id, StudentFormDTO studentFormDTO);

    MessageResponseDTO deleteById(Long id);
}
