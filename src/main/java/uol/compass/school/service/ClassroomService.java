package uol.compass.school.service;

import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.ClassroomDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.StudentNameDTO;

import java.util.List;
import java.util.Set;

public interface ClassroomService {

    MessageResponseDTO create(ClassroomRequestDTO classroomRequestDTO);

    List<ClassroomDTO> findAll(Boolean status);

    ClassroomDTO findById(Long id);

    MessageResponseDTO update(Long id, ClassroomRequestDTO classroomRequestDTO);

    MessageResponseDTO delete(Long id);

//    MessageResponseDTO linkACourse(Long classroomId, Long courseId);
//
//    MessageResponseDTO unlinkACourse(Long classroomId, Long courseId);
//
//    List<CoursesDTO> getAllCourses(Long id);

    MessageResponseDTO linkAStudent(Long classroomId, Long studentId);

    MessageResponseDTO unlinkAStudent(Long classroomId, Long studentId);

    Set<StudentNameDTO> getAllStudents(Long id);
}
