package uol.compass.school.service;

import uol.compass.school.dto.request.CourseRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.dto.response.MessageResponseDTO;

import java.util.List;
import java.util.Set;

public interface CourseService {

    MessageResponseDTO create(CourseRequestDTO courseRequestDTO);

    List<CourseDTO> findAll(String name);

    CourseDTO findById(Long id);

    MessageResponseDTO update(Long id, CourseRequestDTO courseRequestDTO);

    MessageResponseDTO deleteById(Long id);

    Set<CourseDTO> findAllCourses(Long id);
}
