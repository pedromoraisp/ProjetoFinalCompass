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
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.entity.Responsible;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.ResponsibleRepository;
import uol.compass.school.service.ResponsibleService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResponsibleServiceImpl implements ResponsibleService {

    private ResponsibleRepository responsibleRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ResponsibleServiceImpl(ResponsibleRepository responsibleRepository, ModelMapper modelMapper) {
        this.responsibleRepository = responsibleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(ResponsibleRequestDTO responsibleRequestDTO) {
        Responsible responsibleToSave = modelMapper.map(responsibleRequestDTO, Responsible.class);
        Responsible savedResponsible = this.responsibleRepository.save(responsibleToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible %s with id %s was successfully created", savedResponsible.getName(), savedResponsible.getId()))
                .build();
    }

    @Override
    public List<ResponsibleDTO> findAll(String name) {

        List<Responsible> responsibles;

        if (name == null) {
            responsibles = this.responsibleRepository.findAll();
        } else {
            responsibles = this.responsibleRepository.findByNameStartingWith(name);
        }

        return responsibles.stream()
                .map(responsible -> modelMapper.map(responsible, ResponsibleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ResponsibleDTO findById(Long id) {
        Responsible responsible = this.responsibleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find responsible " +
                                "with id %s", id)));

        return modelMapper.map(responsible, ResponsibleDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, ResponsibleRequestDTO responsibleRequestDTO) {

        Responsible responsible = this.responsibleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find responsible" +
                                " with id %s", id)));

        Responsible responsibleToSave = modelMapper.map(responsibleRequestDTO, Responsible.class);
        responsibleToSave.setId(id);

        this.responsibleRepository.save(responsibleToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible with id %s was successfully updated", responsible.getId()))
                .build();

    }
    @Transactional
    @Override
    public MessageResponseDTO deleteById(Long id) {

        Responsible responsible = this.responsibleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find responsible with id %s", id)));

        this.responsibleRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Responsible with id %s was successfully deleted", id))
                .build();
    }

    @Override
    public Set<StudentDTO> findAllStudents(Long id) {
        Set<Student> students;

        Responsible responsible = this.responsibleRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find responsible with id %s", id)));

        students = (responsible.getStudents() == null) ? new HashSet<>() : responsible.getStudents();


        return students.stream().map(student -> modelMapper.map(student, StudentDTO.class))
                    .collect(Collectors.toSet());

    }
}
