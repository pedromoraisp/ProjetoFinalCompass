package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.Course;
import uol.compass.school.entity.Educator;
import uol.compass.school.repository.EducatorRepository;
import uol.compass.school.service.EducatorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducatorServiceImpl implements EducatorService {

    private EducatorRepository educatorRepository;

    private ModelMapper modelMapper;

    @Autowired
    public EducatorServiceImpl(EducatorRepository educatorRepository, ModelMapper modelMapper) {
        this.educatorRepository = educatorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(EducatorRequestDTO educatorRequestDTO) {
        Educator educatorToSave = modelMapper.map(educatorRequestDTO, Educator.class);
        Educator savedEducator = educatorRepository.save(educatorToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Educator %s with id %s was successfully created", savedEducator.getName(), savedEducator.getId()))
                .build();
    }

    @Override
    public List<EducatorDTO> findAll(String name) {
        List<Educator> educators;

        if (name == null) {
            educators = educatorRepository.findAll();
        } else {
            educators = educatorRepository.findByNameStartingWith(name);
        }

        return educators.stream()
                .map(educator -> modelMapper.map(educator, EducatorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public EducatorDTO findById(Long id) {
        CourseDTO courseDTO;
        Educator educator = checkIfEducatorExists(id);

        EducatorDTO educatorDTO = modelMapper.map(educator, EducatorDTO.class);

        if(!(educator.getCourse() == null)) {
            courseDTO = modelMapper.map(educator.getCourse(), CourseDTO.class);
            educatorDTO.setCourse(courseDTO);
        }

        return educatorDTO;
    }

    @Override
    public MessageResponseDTO update(Long id, EducatorRequestDTO educatorRequestDTO) {
        Educator educator = checkIfEducatorExists(id);

        Educator educatorToSave = modelMapper.map(educatorRequestDTO, Educator.class);
        educatorToSave.setId(id);

        educatorRepository.save(educatorToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Educator with id %s was successfully updated", educator.getId()))
                .build();
    }

    @Transactional
    @Override
    public MessageResponseDTO deleteById(Long id) {
        checkIfEducatorExists(id);

        educatorRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Educator with id %s was successfully deleted", id))
                .build();
    }

    private Educator checkIfEducatorExists(Long id) {
        return educatorRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find educator with id %s", id)));
    }
}