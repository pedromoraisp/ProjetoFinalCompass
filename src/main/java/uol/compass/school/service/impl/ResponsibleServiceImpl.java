package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.ResponsibleRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.ResponsibleDTO;
import uol.compass.school.dto.response.StudentNameDTO;
import uol.compass.school.entity.Responsible;
import uol.compass.school.entity.Student;
import uol.compass.school.entity.User;
import uol.compass.school.repository.ResponsibleRepository;
import uol.compass.school.repository.StudentRepository;
import uol.compass.school.repository.UserRepository;
import uol.compass.school.service.ResponsibleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResponsibleServiceImpl implements ResponsibleService {

    private ResponsibleRepository responsibleRepository;

    private StudentRepository studentRepository;

    private UserRepository userRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ResponsibleServiceImpl(ResponsibleRepository responsibleRepository, StudentRepository studentRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.responsibleRepository = responsibleRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(ResponsibleRequestDTO responsibleRequestDTO) {
        Responsible responsibleToSave = modelMapper.map(responsibleRequestDTO, Responsible.class);
        Responsible savedResponsible = responsibleRepository.save(responsibleToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible %s with id %s was successfully created", savedResponsible.getName(), savedResponsible.getId()))
                .build();
    }

    @Override
    public List<ResponsibleDTO> findAll(String name) {
        List<Responsible> responsibles;

        if (name == null) {
            responsibles = responsibleRepository.findAll();
        } else {
            responsibles = responsibleRepository.findByNameStartingWith(name);
        }

        return responsibles.stream()
                .map(responsible -> modelMapper.map(responsible, ResponsibleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ResponsibleDTO findById(Long id) {
        Responsible responsible = checkIfResponsibleExists(id);
        return modelMapper.map(responsible, ResponsibleDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, ResponsibleRequestDTO responsibleRequestDTO) {
        Responsible responsible = checkIfResponsibleExists(id);

        Responsible responsibleToSave = modelMapper.map(responsibleRequestDTO, Responsible.class);
        responsibleToSave.setId(id);

        responsibleRepository.save(responsibleToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible with id %s was successfully updated", responsible.getId()))
                .build();
    }

    @Transactional
    @Override
    public MessageResponseDTO deleteById(Long id) {
        checkIfResponsibleExists(id);

        responsibleRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible with id %s was successfully deleted", id))
                .build();
    }

    @Override
    public MessageResponseDTO linkResponsibleToStudent(Long responsibleId, Long studentId) {
        Responsible responsible = checkIfResponsibleExists(responsibleId);
        Student student = checkIfStudentExists(studentId);

        if (!(responsible.getStudents() == null)) {
            HashSet<Student> students = new HashSet<>(responsible.getStudents());
            students.add(student);
            responsible.setStudents(students);
        }

        responsibleRepository.save(responsible);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible with id %s was linked to the student with id %s successfully", responsible.getId(), student.getId()))
                .build();
    }

    @Override
    public Set<StudentNameDTO> findAllStudents(Long id) {
        Set<Student> students;

        Responsible responsible = checkIfResponsibleExists(id);

        students = (responsible.getStudents() == null) ? new HashSet<>() : responsible.getStudents();


        return students.stream().map(student -> modelMapper.map(student, StudentNameDTO.class))
                .collect(Collectors.toSet());
    }

    @Override
    public MessageResponseDTO linkResponsibleToUser(Long responsibleId, Long userId) {
        Responsible responsible = checkIfResponsibleExists(responsibleId);
        User user = verifyIfUserExists(userId);

        responsible.setUser(user);

        responsibleRepository.save(responsible);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible with id %s was linked to the user with id %s successfully", responsible.getId(), user.getId()))
                .build();
    }

    private Responsible checkIfResponsibleExists(Long id) {
        return responsibleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find responsible with id %s", id)));
    }

    private Student checkIfStudentExists(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", id)));
    }

    private User verifyIfUserExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find user with id %s", id)));
    }
}