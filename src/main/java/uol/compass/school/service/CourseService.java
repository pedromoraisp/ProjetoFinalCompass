package uol.compass.school.service;

import uol.compass.school.dto.request.CourseRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.dto.response.MessageResponseDTO;

import java.util.List;

public interface CourseService {

    MessageResponseDTO create(CourseRequestDTO courseRequestDTO);

    List<CourseDTO> findAll(String name);

    CourseDTO findById(Long id);

    MessageResponseDTO update(Long id, CourseRequestDTO courseRequestDTO);

    MessageResponseDTO deleteById(Long id);

    MessageResponseDTO linkAEducator(Long courseId, Long educatorId);

    MessageResponseDTO unlinkAEducator(Long courseId, Long educatorId);
}