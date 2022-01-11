package uol.compass.school.service;

import uol.compass.school.dto.request.ResponsibleRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.ResponsibleDTO;
import uol.compass.school.dto.response.StudentDTO;


import java.util.List;
import java.util.Set;

public interface ResponsibleService {

    MessageResponseDTO create(ResponsibleRequestDTO responsibleRequestDTO);

    List<ResponsibleDTO> findAll(String name);

    ResponsibleDTO findById(Long id);

    MessageResponseDTO update(Long id, ResponsibleRequestDTO responsibleRequestDTO);

    MessageResponseDTO deleteById(Long id);

    Set<StudentDTO> findAllStudents(Long id);
}
