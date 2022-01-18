package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.entity.Occurrence;
import uol.compass.school.entity.Student;
import uol.compass.school.repository.OccurrenceRepository;
import uol.compass.school.repository.StudentRepository;
import uol.compass.school.service.OccurrenceService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OccurrenceServiceImpl implements OccurrenceService {

    private StudentRepository studentRepository;
    private OccurrenceRepository occurrenceRepository;
    private ModelMapper modelMapper;

    @Autowired
    public OccurrenceServiceImpl(StudentRepository studentRepository, OccurrenceRepository occurrenceRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.occurrenceRepository = occurrenceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(OccurrenceRequestDTO occurrenceRequestDTO) {
        Occurrence occurrenceToSave = modelMapper.map(occurrenceRequestDTO, Occurrence.class);
        Occurrence savedOccurrence = occurrenceRepository.save(occurrenceToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Occurrence with id %s was successfully created", savedOccurrence.getId()))
                .build();
    }

    @Override
    public List<OccurrenceDTO> findAll(LocalDate initialDate, LocalDate finalDate) {
        List<Occurrence> occurrences;

        if (initialDate == null && finalDate == null) {
            occurrences = occurrenceRepository.findAll();
        } else {
            if (initialDate == null) {
                initialDate = LocalDate.now().minusYears(5);
            }
            if (finalDate == null) {
                finalDate = LocalDate.now();
            }
            occurrences = occurrenceRepository.findByDateBetween(initialDate, finalDate);
        }

        return occurrences.stream().map(occurrence -> modelMapper.map(occurrence, OccurrenceDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public OccurrenceDTO findById(Long id) {
        Occurrence occurrence = checkIfOccurrenceExists(id);

        return modelMapper.map(occurrence, OccurrenceDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, OccurrenceRequestDTO occurrenceRequestDTO) {
        Occurrence occurrence = checkIfOccurrenceExists(id);

        Occurrence occurrenceToSave = modelMapper.map(occurrenceRequestDTO, Occurrence.class);
        occurrenceToSave.setId(id);

        occurrenceRepository.save(occurrenceToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Occurrence with id %s was successfully updated", occurrence.getId()))
                .build();
    }

    @Transactional
    @Override
    public MessageResponseDTO deleteById(Long id) {
        checkIfOccurrenceExists(id);

        occurrenceRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Occurrence with id %s was successfully deleted", id))
                .build();
    }

    @Override
    public MessageResponseDTO linkOccurrenceToStudent(Long occurrenceId, Long studentId) {
        Occurrence occurrence = checkIfOccurrenceExists(occurrenceId);
        Student student = checkIfStudentExists(studentId);

        occurrence.setStudent(student);

        occurrenceRepository.save(occurrence);

        return MessageResponseDTO.builder()
                .message(String.format("the occurrence with id %s was linked to the student with id %s successfully", occurrenceId, studentId))
                .build();
    }

    private Student checkIfStudentExists(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Unable to find student with id %s", studentId)));
    }

    private Occurrence checkIfOccurrenceExists(Long id) {
        return occurrenceRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Unable to find occurrence with id %s", id)));
    }

}
