package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.ClassroomDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.StudentNameDTO;
import uol.compass.school.entity.Classroom;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.ClassroomRepository;
import uol.compass.school.repository.StudentRepository;
import uol.compass.school.service.ClassroomService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    private ClassroomRepository classroomRepository;

    //private CourseRepository courseRepository;

    private StudentRepository studentRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository classroomRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.classroomRepository = classroomRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(ClassroomRequestDTO classroomRequestDTO) {
        Classroom classToSave = modelMapper.map(classroomRequestDTO, Classroom.class);
        Classroom savedClass = classroomRepository.save(classToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Class with id %s was successfully created", savedClass.getId()))
                .build();
    }

    @Override
    public List<ClassroomDTO> findAll(Boolean status) {
        List<Classroom> classrooms =
                (status == null) ? classroomRepository.findAll() : classroomRepository.findAllByStatus(status);

        return classrooms.stream().map(classroom -> modelMapper.map(classroom, ClassroomDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ClassroomDTO findById(Long id) {
        Classroom classroom = checkIfClassroomExists(id);

        return modelMapper.map(classroom, ClassroomDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, ClassroomRequestDTO classroomRequestDTO) {
        classroomRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find classroom with id %s", id)));

        Classroom classroomToUpdate = modelMapper.map(classroomRequestDTO, Classroom.class);
        classroomToUpdate.setId(id);

        Classroom updatedClassroom = classroomRepository.save(classroomToUpdate);

        return MessageResponseDTO.builder()
                .message(String.format("Class with id %s was successfully updated", updatedClassroom.getId()))
                .build();
    }

    @Override
    public MessageResponseDTO delete(Long id) {
        classroomRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find classroom with id %s", id)));

        classroomRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Classroom with id %s was successfully deleted", id))
                .build();
    }

//    @Override
//    public MessageResponseDTO linkACourse(Long classroomId, Long courseId) {
//        Classroom classroom = checkIfClassroomExists(classroomId);
//
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find course with id %s", courseId)));
//
//        classroom.getCourses().add(course);
//
//        classroomRepository.save(classroom);
//
//        return MessageResponseDTO.builder()
//                .message(String.format("Classroom with id %s was linked to the course with id %s successfully", classroom.getId(), course.getId()))
//                .build();
//    }
//
//    @Override
//    public MessageResponseDTO unlinkACourse(Long classroomId, Long courseId) {
//        Classroom classroom = checkIfClassroomExists(classroomId);
//
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() ->
//                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find course with id %s", courseId)));
//
//        classroom.getCourses().remove(course);
//
//        classroomRepository.save(classroom);
//
//        return MessageResponseDTO.builder()
//                .message(String.format("Classroom with id %s was unlinked to the course with id %s successfully", classroom.getId(), course.getId()))
//                .build();
//    }
//
//      @Override
//      public Set<CoursesDTO> getAllCourses(Long id) {
//          Classroom classroom = checkIfClassroomExists(id);
//
//        return classroom.getCourses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toSet());
//    }

    @Override
    public MessageResponseDTO linkAStudent(Long classroomId, Long studentId) {
        Classroom classroom = checkIfClassroomExists(classroomId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", studentId)));

        if (!(classroom.getStudents() == null)) {
            classroom.getStudents().add(student);
        }

        classroomRepository.save(classroom);

        return MessageResponseDTO.builder()
                .message(String.format("Classroom with id %s was linked to the student with id %s successfully", classroom.getId(), student.getId()))
                .build();
    }

    @Override
    public MessageResponseDTO unlinkAStudent(Long classroomId, Long studentId) {
        Classroom classroom = checkIfClassroomExists(classroomId);

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", studentId)));

        if (!(classroom.getStudents() == null)) {
            classroom.getStudents().remove(student);
        }

        classroomRepository.save(classroom);

        return MessageResponseDTO.builder()
                .message(String.format("Classroom with id %s was unlinked to the student with id %s successfully", classroom.getId(), student.getId()))
                .build();
    }

    @Override
    public Set<StudentNameDTO> getAllStudents(Long id) {
        Classroom classroom = checkIfClassroomExists(id);

        if (!(classroom.getStudents() == null)) {
            return classroom.getStudents().stream().map(student -> modelMapper.map(student, StudentNameDTO.class)).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }

    private Classroom checkIfClassroomExists(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find classroom with id %s", id)));
    }
}