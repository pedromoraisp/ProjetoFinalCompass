package uol.compass.school.service;

import java.time.LocalDate;
import java.util.List;

import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;

public interface OccurrenceService {
	
	MessageResponseDTO create(OccurrenceRequestDTO occurrenceRequestDTO);
	List<OccurrenceDTO> findAll(LocalDate initialDate, LocalDate finalDate);
	OccurrenceDTO findById(Long id);
	MessageResponseDTO deleteById(Long id);
	MessageResponseDTO update(Long id, OccurrenceRequestDTO occurrenceRequestDTO);
	MessageResponseDTO linkOccurrenceToStudent(Long occurrenceId, Long studentId);
}
