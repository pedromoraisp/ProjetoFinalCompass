package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.StudentRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.StudentRepository;
import uol.compass.school.service.StudentService;

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
        Student savedStudent = this.studentRepository.save(studentToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Student %s with id %s was successfully created", savedStudent.getName(), savedStudent.getId()))
                .build();
    }

    @Override
    public List<StudentDTO> findAll(String name) {
        List<Student> students;

        if (name == null) {
            students = this.studentRepository.findAll();
        } else {
            students = this.studentRepository.findByNameStartingWith(name);
        }

        return students.stream()
                .map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public StudentDTO findById(Long id) {
        Student student = this.studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", id)));

        return modelMapper.map(student, StudentDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, StudentRequestDTO studentRequestDTO) {
        Student student = this.studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", id)));

        Student studentToSave = modelMapper.map(studentRequestDTO, Student.class);
        studentToSave.setId(id);

        this.studentRepository.save(studentToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Student with id %s was successfully updated", student.getId()))
                .build();
    }

    @Transactional
    @Override
    public MessageResponseDTO deleteById(Long id) {
        this.studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", id)));

        this.studentRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Student with id %s was successfully deleted", id))
                .build();
    }
}
