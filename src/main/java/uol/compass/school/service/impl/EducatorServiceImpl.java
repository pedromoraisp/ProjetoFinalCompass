package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.entity.Educator;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.EducatorRepository;
import uol.compass.school.service.EducatorService;

import java.time.LocalDate;
import java.util.ArrayList;
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
    	Educator savedEducator = this.educatorRepository.save(educatorToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Educator %s with id %s was successfully created", savedEducator.getName(), savedEducator.getId()))
                .build();
    }

    @Override
    public List<EducatorDTO> findAll(String name) {
        List<Educator> educators;

        if (name == null) {
        	educators = this.educatorRepository.findAll();
        } else {
        	educators = this.educatorRepository.findByNameStartingWith(name);
        }

        return educators.stream()
                .map(educator -> modelMapper.map(educator, EducatorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public EducatorDTO findById(Long id) {
    	Educator educator = this.educatorRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find educator with id %s", id)));

        return modelMapper.map(educator, EducatorDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, EducatorRequestDTO educatorRequestDTO) {
    	Educator educator = this.educatorRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find educator with id %s", id)));

    	Educator educatorToSave = modelMapper.map(educatorRequestDTO, Educator.class);
    	educatorToSave.setId(id);

        this.educatorRepository.save(educatorToSave);

        return MessageResponseDTO.builder()
                .message(String.format("educator with id %s was successfully updated", educator.getId()))
                .build();
    }

    @Transactional
    @Override
    public MessageResponseDTO deleteById(Long id) {
        this.educatorRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find educator with id %s", id)));

        this.educatorRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Educator with id %s was successfully deleted", id))
                .build();
    }

	@Override
	public List<EducatorDTO> findAllOccurrences(Long id, LocalDate initialDate, LocalDate finalDate) {
		// TODO Auto-generated method stub
		return null;
	}

    //@Override
    /*public List<DTO> findAllOccurrences(Long id, LocalDate initialDate, LocalDate finalDate) {
        List<Occurrence> occurrences;

        Student student = this.studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find student with id %s", id)));

        occurrences = (student.getOccurrences() == null) ? new ArrayList<>() : student.getOccurrences();

        if (initialDate == null && finalDate == null) {
            return occurrences.stream().map(occurrence -> modelMapper.map(occurrence, OccurrenceDTO.class))
                    .collect(Collectors.toList());
        } else {
            if(initialDate == null) {
                initialDate = LocalDate.now().minusYears(5);
            }*/
       /*     if(finalDate == null) {
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
                    .collect(Collectors.toList());*/
        //}/*
    }
//}
