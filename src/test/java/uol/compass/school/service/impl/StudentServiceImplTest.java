package uol.compass.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.Utils.OccurrenceUtils;
import uol.compass.school.Utils.StudentUtils;
import uol.compass.school.dto.request.StudentRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.dto.response.OccurrenceToStudentDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.StudentRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void whenStudentIsInformedThenShouldBeCreated() {
        StudentRequestDTO studentRequestDTO = StudentUtils.createStudentRequestDTO();
        Student expectedStudent = StudentUtils.createStudent();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Student Pedro Henrique with id 1 was successfully created")
                .build();

        when(modelMapper.map(studentRequestDTO, Student.class)).thenReturn(expectedStudent);
        when(studentRepository.save(expectedStudent)).thenReturn(expectedStudent);

        MessageResponseDTO messageResponseDTO = studentService.create(studentRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenFindAllWithoutNameIsCalledThenReturnAllStudents() {
        Student expectedStudent = StudentUtils.createStudent();
        StudentDTO expectedStudentDTO = StudentUtils.createStudentDTO();
        List<StudentDTO> expectedStudentsDTO = Collections.singletonList(expectedStudentDTO);

        when(studentRepository.findAll()).thenReturn(Collections.singletonList(expectedStudent));
        when(modelMapper.map(expectedStudent, StudentDTO.class)).thenReturn(expectedStudentDTO);

        List<StudentDTO> studentsDTO = studentService.findAll(null);

        assertEquals(studentsDTO, expectedStudentsDTO);
    }

    @Test
    void whenFindAllWithNameIsCalledThenReturnAllStudentsWithThisName() {
        String name = "Pedro";

        Student expectedStudent = StudentUtils.createStudent();
        StudentDTO expectedStudentDTO = StudentUtils.createStudentDTO();
        List<StudentDTO> expectedStudentsDTO = Collections.singletonList(expectedStudentDTO);

        when(studentRepository.findByNameStartingWith(name)).thenReturn(Collections.singletonList(expectedStudent));
        when(modelMapper.map(expectedStudent, StudentDTO.class)).thenReturn(expectedStudentDTO);

        List<StudentDTO> studentsDTO = studentService.findAll(name);

        assertEquals(studentsDTO, expectedStudentsDTO);
    }

    @Test
    void whenFindAllWithAnNonexistentNameIsCalledThenReturnAnEmptyList() {
        String name = "Pedro";

        when(studentRepository.findByNameStartingWith(name)).thenReturn(Collections.EMPTY_LIST);

        List<StudentDTO> studentsDTO = studentService.findAll(name);

        assertEquals(0, studentsDTO.size());
    }

    @Test
    void whenStudentIdIsInformedThenItShouldBeReturned() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudent();
        StudentDTO expectedStudentDTO = StudentUtils.createStudentDTO();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));
        when(modelMapper.map(expectedStudent, StudentDTO.class)).thenReturn(expectedStudentDTO);

        StudentDTO studentDTO = studentService.findById(id);

        assertEquals(expectedStudentDTO, studentDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToFindAStudentThenShouldReturnResponseStatusException() {
        Long id = 1L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> studentService.findById(id));
    }

    @Test
    void whenStudentIdIsInformedThenItShouldBeUpdated() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudent();
        StudentRequestDTO studentRequestDTO = StudentUtils.createStudentRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Student with id 1 was successfully updated")
                .build();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));
        when(modelMapper.map(studentRequestDTO, Student.class)).thenReturn(expectedStudent);

        MessageResponseDTO messageResponseDTO = studentService.update(id, studentRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToUpdateAStudentThenShouldReturnResponseStatusException() {
        Long id = 1L;
        StudentRequestDTO studentRequestDTO = StudentUtils.createStudentRequestDTO();

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> studentService.update(id, studentRequestDTO));
    }

    @Test
    void whenStudentIdIsInformedThenItShouldBeDeleted() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudent();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Student with id 1 was successfully deleted")
                .build();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));

        MessageResponseDTO messageResponseDTO = studentService.deleteById(id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToDeleteAStudentThenShouldReturnResponseStatusException() {
        Long id = 1L;

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> studentService.deleteById(id));
    }

    @Test
    void whenFindAllFromAStudentIsCalledWithoutFilterThenReturnAllOccurrences() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudentWithOccurrences();
        OccurrenceToStudentDTO occurrenceToStudentDTO = OccurrenceUtils.createOccurrenceToStudentDTO();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));
        when(modelMapper.map(expectedStudent.getOccurrences().get(0), OccurrenceToStudentDTO.class))
                .thenReturn(occurrenceToStudentDTO);

        List<OccurrenceToStudentDTO> allOccurrences = studentService.findAllOccurrences(id, null, null);

        assertEquals(Collections.singletonList(occurrenceToStudentDTO), allOccurrences);
    }

    @Test
    void whenFindAllIsCalledFromAStudentWithoutOccurrencesThenReturnAnEmptyList() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudent();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));

        List<OccurrenceToStudentDTO> allOccurrences = studentService.findAllOccurrences(id, null, null);

        assertEquals(Collections.emptyList(), allOccurrences);
    }

    @Test
    void whenFindAllFromAStudentIsCalledWithFilterThenReturnAllOccurrencesWithThisFilter() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudentWithOccurrences();
        expectedStudent.getOccurrences().get(0).setDate(LocalDate.of(2021, 01, 15));
        OccurrenceToStudentDTO occurrenceToStudentDTO = OccurrenceUtils.createOccurrenceToStudentDTO();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));
        when(modelMapper.map(expectedStudent.getOccurrences().get(0), OccurrenceToStudentDTO.class))
                .thenReturn(occurrenceToStudentDTO);

        List<OccurrenceToStudentDTO> allOccurrences = studentService.findAllOccurrences(
                id,
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 12, 31));

        assertEquals(Collections.singletonList(occurrenceToStudentDTO), allOccurrences);
    }

    @Test
    void whenFindAllFromAStudentIsCalledWithInitialDateThenReturnAllOccurrencesWithThisFilter() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudentWithOccurrences();
        expectedStudent.getOccurrences().get(0).setDate(LocalDate.of(2021, 01, 15));
        OccurrenceToStudentDTO occurrenceToStudentDTO = OccurrenceUtils.createOccurrenceToStudentDTO();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));
        when(modelMapper.map(expectedStudent.getOccurrences().get(0), OccurrenceToStudentDTO.class))
                .thenReturn(occurrenceToStudentDTO);

        List<OccurrenceToStudentDTO> allOccurrences = studentService.findAllOccurrences(
                id,
                LocalDate.of(2021, 1, 1),
                null);

        assertEquals(Collections.singletonList(occurrenceToStudentDTO), allOccurrences);
    }

    @Test
    void whenFindAllFromAStudentIsCalledWithFinalDateThenReturnAllOccurrencesWithThisFilter() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudentWithOccurrences();
        expectedStudent.getOccurrences().get(0).setDate(LocalDate.of(2021, 01, 15));
        OccurrenceToStudentDTO occurrenceToStudentDTO = OccurrenceUtils.createOccurrenceToStudentDTO();

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));
        when(modelMapper.map(expectedStudent.getOccurrences().get(0), OccurrenceToStudentDTO.class))
                .thenReturn(occurrenceToStudentDTO);

        List<OccurrenceToStudentDTO> allOccurrences = studentService.findAllOccurrences(
                id,
                null,
                LocalDate.of(2021, 12, 31));

        assertEquals(Collections.singletonList(occurrenceToStudentDTO), allOccurrences);
    }

    @Test
    void whenFindAllFromAStudentIsCalledWithFilterThenReturnEmptyList() {
        Long id = 1L;
        Student expectedStudent = StudentUtils.createStudentWithOccurrences();
        expectedStudent.getOccurrences().get(0).setDate(LocalDate.of(2020, 01, 15));

        when(studentRepository.findById(id)).thenReturn(Optional.of(expectedStudent));

        List<OccurrenceToStudentDTO> allOccurrences = studentService.findAllOccurrences(
                id,
                LocalDate.of(2021, 1, 1),
                LocalDate.of(2021, 12, 31));

        assertEquals(Collections.emptyList(), allOccurrences);
    }
}