package uol.compass.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.Utils.ClassroomUtils;
import uol.compass.school.Utils.CourseUtils;
import uol.compass.school.Utils.StudentUtils;
import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.ClassroomDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.Classroom;
import uol.compass.school.entity.Course;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.ClassroomRepository;
import uol.compass.school.repository.CourseRepository;
import uol.compass.school.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClassroomServiceImplTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @Test
    void whenClassroomIsInformedThenShouldBeCreated() {
        ClassroomRequestDTO classroomRequestDTO = ClassroomUtils.createClassroomRequestDTO();
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Class with id 1 was successfully created")
                .build();

        when(classroomRepository.save(expectedClassroom)).thenReturn(expectedClassroom);
        when(modelMapper.map(classroomRequestDTO, Classroom.class)).thenReturn(expectedClassroom);

        MessageResponseDTO messageResponseDTO = classroomService.create(classroomRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenFindAllWithoutParameterIsCalledThenReturnAllClassrooms() {
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        ClassroomDTO expectedClassroomDTO = ClassroomUtils.createClassroomDTO();
        List<ClassroomDTO> expectedClassroomsDTO = Collections.singletonList(expectedClassroomDTO);

        when(classroomRepository.findAll()).thenReturn(Collections.singletonList(expectedClassroom));
        when(modelMapper.map(expectedClassroom, ClassroomDTO.class)).thenReturn(expectedClassroomDTO);

        List<ClassroomDTO> ClassroomsDTO = classroomService.findAll(null);

        assertEquals(ClassroomsDTO, expectedClassroomsDTO);
    }

    @Test
    void whenFindAllWithParameterIsCalledThenReturnFilteredClassrooms() {
        Boolean status = Boolean.TRUE;
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        ClassroomDTO expectedClassroomDTO = ClassroomUtils.createClassroomDTO();
        List<ClassroomDTO> expectedClassroomsDTO = Collections.singletonList(expectedClassroomDTO);

        when(classroomRepository.findAllByStatus(status)).thenReturn(Collections.singletonList(expectedClassroom));
        when(modelMapper.map(expectedClassroom, ClassroomDTO.class)).thenReturn(expectedClassroomDTO);

        List<ClassroomDTO> ClassroomsDTO = classroomService.findAll(status);

        assertEquals(expectedClassroomsDTO, ClassroomsDTO);
    }

    @Test
    void whenFindAllWithNonexistentParameterIsCalledThenReturnEmptyList() {
        Boolean status = Boolean.TRUE;
        ClassroomDTO expectedClassroomDTO = ClassroomUtils.createClassroomDTO();
        List<ClassroomDTO> expectedClassroomsDTO = Collections.singletonList(expectedClassroomDTO);

        when(classroomRepository.findAllByStatus(status)).thenReturn(Collections.EMPTY_LIST);

        List<ClassroomDTO> ClassroomsDTO = classroomService.findAll(status);

        assertEquals(Collections.emptyList(), ClassroomsDTO);
    }

    @Test
    void whenClassroomIsInformedThenItShouldBeReturned() {
        Long id = 1L;
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        ClassroomDTO expectedClassroomDTO = ClassroomUtils.createClassroomDTO();

        when(classroomRepository.findById(id)).thenReturn(Optional.of(expectedClassroom));
        when(modelMapper.map(expectedClassroom, ClassroomDTO.class)).thenReturn(expectedClassroomDTO);

        ClassroomDTO classroomFound = classroomService.findById(id);

        assertEquals(expectedClassroomDTO, classroomFound);
    }

    @Test
    void whenANonexistentClassroomIdIsInformedThenShouldReturnResponseStatusException() {
        Long id = 1L;

        when(classroomRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> classroomService.findById(id));
    }

    @Test
    void whenClassroomIsInformedThenShouldBeUpdated() {
        Long id = 1L;
        ClassroomRequestDTO classroomRequestDTO = ClassroomUtils.createClassroomRequestDTO();
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Class with id 1 was successfully updated")
                .build();

        when(classroomRepository.findById(id)).thenReturn(Optional.of(expectedClassroom));
        when(modelMapper.map(classroomRequestDTO, Classroom.class)).thenReturn(expectedClassroom);
        when(classroomRepository.save(expectedClassroom)).thenReturn(expectedClassroom);

        MessageResponseDTO messageResponseDTO = classroomService.update(expectedClassroom.getId(), classroomRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenClassroomIsInformedThenItShouldBeDeleted() {
        Long id = 1L;
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Classroom with id 1 was successfully deleted")
                .build();

        when(classroomRepository.findById(id)).thenReturn(Optional.of(expectedClassroom));

        MessageResponseDTO messageResponseDTO = classroomService.delete(id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenClassroomIdAndCourseIdAreInformedThenTheyShouldBeLinked() {
        Classroom classroom = ClassroomUtils.createClassroomWithStudentsAndCourses();
        Course course = CourseUtils.createCourse();
        course.setId(2L);
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Classroom with id 1 was linked to the course with id 2 successfully")
                .build();

        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        MessageResponseDTO messageResponseDTO = classroomService.linkACourse(classroom.getId(), course.getId());

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenUnlinkedClassroomIdAndCourseIdAreInformedThenShouldReturnAnException() {
        Long id = 1L;
        Classroom expectedClassroom = ClassroomUtils.createClassroomWithStudentsAndCourses();
        Course course = CourseUtils.createCourse();
        course.setId(2L);

        when(classroomRepository.findById(id)).thenReturn(Optional.of(expectedClassroom));
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        assertThrows(ResponseStatusException.class, () -> classroomService.unlinkACourse(id, id), "This course is not linked to the class");
    }

    @Test
    void whenClassroomIdAndStudentIdAreInformedThenTheyShouldBeLinked() {
        Classroom classroom = ClassroomUtils.createClassroomWithStudentsAndCourses();
        Student student = StudentUtils.createStudent();
        student.setId(2L);
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Classroom with id 1 was linked to the student with id 2 successfully")
                .build();

        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        MessageResponseDTO messageResponseDTO = classroomService.linkAStudent(classroom.getId(), student.getId());

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenUnlinkedClassroomIdAndStudentIdAreInformedThenShouldReturnAnException() {
        Long id = 1L;
        Classroom expectedClassroom = ClassroomUtils.createClassroomWithStudentsAndCourses();
        Student student = StudentUtils.createStudent();
        student.setId(2L);

        when(classroomRepository.findById(id)).thenReturn(Optional.of(expectedClassroom));
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        assertThrows(ResponseStatusException.class, () -> classroomService.unlinkAStudent(id, id), "This student is not linked to the class");
    }

    @Test
    void whenNonexistentCourseIdIsGivenThenReturnAnException() {
        Long id = 1L;

        assertThrows(ResponseStatusException.class, () -> classroomService.linkACourse(id, id), "Unable to find course with id 1");
    }

    @Test
    void whenNonexistentStudentIdIsGivenThenReturnAnException() {
        Long id = 1L;

        assertThrows(ResponseStatusException.class, () -> classroomService.linkAStudent(id, id), "Unable to find student with id 1");
    }
}