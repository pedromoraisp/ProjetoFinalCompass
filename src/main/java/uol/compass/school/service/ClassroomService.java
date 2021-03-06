package uol.compass.school.service;

import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.ClassroomDTO;
import uol.compass.school.dto.response.MessageResponseDTO;

import java.util.List;

public interface ClassroomService {

    MessageResponseDTO create(ClassroomRequestDTO classroomRequestDTO);

    List<ClassroomDTO> findAll(Boolean status);

    ClassroomDTO findById(Long id);

    MessageResponseDTO update(Long id, ClassroomRequestDTO classroomRequestDTO);

    MessageResponseDTO delete(Long id);

    MessageResponseDTO linkACourse(Long classroomId, Long courseId);

    MessageResponseDTO unlinkACourse(Long classroomId, Long courseId);

    MessageResponseDTO linkAStudent(Long classroomId, Long studentId);

    MessageResponseDTO unlinkAStudent(Long classroomId, Long studentId);
}