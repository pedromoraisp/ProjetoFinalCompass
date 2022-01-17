package uol.compass.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.Utils.ClassroomUtils;
import uol.compass.school.Utils.StudentUtils;
import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.ClassroomDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.StudentNameDTO;
import uol.compass.school.entity.Classroom;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.ClassroomRepository;
import uol.compass.school.repository.StudentRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    }

    @Test
    void whenClassroomIdAndCourseIdAreInformedThenTheyShouldBeUnlinked() {

    }

    @Test
    void whenClassroomIdAndStudentIdAreInformedThenTheyShouldBeLinked() {
        Classroom classroom = ClassroomUtils.createClassroomWithStudents();
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
    void whenClassroomIdAndStudentIdAreInformedThenTheyShouldBeUnlinked() {
        Long id = 1L;
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        Student expectedStudent = StudentUtils.createStudent();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Classroom with id 1 was unlinked to the student with id 1 successfully")
                .build();

        when(classroomRepository.findById(id)).thenReturn(Optional.of(expectedClassroom));
        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));

        MessageResponseDTO messageResponseDTO = classroomService.unlinkAStudent(id, id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenClassroomIdIsInformedThenReturnItReturnItsStudents() {
        Long id = 1L;
        Classroom expectedClassroom = ClassroomUtils.createClassroom();
        Student student = StudentUtils.createStudent();
        expectedClassroom.setStudents(Collections.singleton(student));
        StudentNameDTO studentNameDTO = StudentUtils.createStudentNameDTO();

        when(classroomRepository.findById(id)).thenReturn(Optional.of(expectedClassroom));
        when(modelMapper.map(student, StudentNameDTO.class)).thenReturn(studentNameDTO);

        Set<StudentNameDTO> allStudents = classroomService.getAllStudents(id);

        assertEquals(Collections.singleton(studentNameDTO), allStudents);
    }
}