package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.StudentRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.entity.Occurrence;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.StudentRepository;
import uol.compass.school.service.StudentService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    private ModelMapper modelMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(StudentRequestDTO studentRequestDTO) {
        Student studentToSave = modelMapper.map(studentRequestDTO, Student.class);
        Student savedStudent = studentRepository.save(studentToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Student %s with id %s was successfully created", savedStudent.getName(), savedStudent.getId()))
                .build();
    }

    @Override
    public List<StudentDTO> findAll(String name) {
        List<Student> students =
                (name == null) ? studentRepository.findAll() : studentRepository.findByNameStartingWith(name);
        System.out.println(students.get(0).getClassrooms());

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDTO findById(Long id) {
        Student student = checkIfStudentExists(id);

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, StudentRequestDTO studentRequestDTO) {
        checkIfStudentExists(id);

        Student studentToUpdate = modelMapper.map(studentRequestDTO, Student.class);
        studentToUpdate.setId(id);

        Student updatedStudent = studentRepository.save(studentToUpdate);

        return MessageResponseDTO.builder()
                .message(String.format("Student with id %s was successfully updated", id))
                .build();
    }

    @Transactional
    @Override
    public MessageResponseDTO deleteById(Long id) {
        checkIfStudentExists(id);

        studentRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Student with id %s was successfully deleted", id))
                .build();
    }

    @Override
    public List<OccurrenceDTO> findAllOccurrences(Long id, LocalDate initialDate, LocalDate finalDate) {
        List<Occurrence> occurrences;

        Student student = checkIfStudentExists(id);

        occurrences = (student.getOccurrences() == null) ? new ArrayList<>() : student.getOccurrences();

        if (initialDate == null && finalDate == null) {
            return occurrences.stream().map(occurrence -> modelMapper.map(occurrence, OccurrenceDTO.class))
                    .collect(Collectors.toList());
        } else {
            if(initialDate == null) {
                initialDate = LocalDate.now().minusYears(5);
            }
            if(finalDate == null) {
                finalDate = LocalDate.now();
            }

            LocalDate finalInitialDate = initialDate;
            LocalDate finalFinalDate = finalDate;

            List<Occurrence> filteredOccurrences = occurrences.stream().
                    filter(occurrence ->
                            (occurrence.getDate().isEqual(finalInitialDate) || occurrence.getDate().isAfter(finalInitialDate)) &&
                                    (occurrence.getDate().isEqual(finalFinalDate) || occurrence.getDate().isBefore(finalFinalDate)))
                    .collect(Collectors.toList());

            return filteredOccurrences.stream().map(occurrence -> modelMapper.map(occurrence, OccurrenceDTO.class))
                    .collect(Collectors.toList());
        }
    }

    private Student checkIfStudentExists(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", id)));
    }}