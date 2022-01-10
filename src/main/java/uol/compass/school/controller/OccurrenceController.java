package uol.compass.school.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.service.impl.OccurrenceServiceImpl;

@RestController
@RequestMapping("/api/v1/occurrences")
public class OccurrenceController {

	private OccurrenceServiceImpl occurrenceService;

	@Autowired
	public OccurrenceController(OccurrenceServiceImpl occurrenceService) {
		this.occurrenceService = occurrenceService;
	}
//CRIAR UMA OCORRENCIA
	@PostMapping
	public MessageResponseDTO create(@RequestBody @Valid OccurrenceRequestDTO occurrenceRequestDTO) {
		return occurrenceService.create(occurrenceRequestDTO);

	}
//LER
	@GetMapping
	public List<OccurrenceDTO> findAll(@RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate initialDate,
			@RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)LocalDate finalDate) {
		return this.occurrenceService.findAll(initialDate, finalDate);
	}

	@GetMapping("/{id}")
	public OccurrenceDTO findById(@PathVariable Long id) {
		return this.occurrenceService.findById(id);
	}
//ATUALIZA
	@PutMapping("/{id}")
	public MessageResponseDTO update(@PathVariable Long id, @RequestBody OccurrenceRequestDTO occurrenceRequestDTO) {
		return this.occurrenceService.update(id, occurrenceRequestDTO);
	}
//DELETAR OCORRENCIA
	@DeleteMapping("/{id}")
	public MessageResponseDTO deleteById(@PathVariable Long id) {
		return this.occurrenceService.deleteById(id);
	}
//CRIAR
	@PostMapping("/{occurrenceId}/students/{studentId}")
	public MessageResponseDTO linkOccurrenceToStudent(@PathVariable Long occurrenceId, @PathVariable Long studentId) {
		return occurrenceService.linkOccurrenceToStudent(occurrenceId, studentId);
	}

}
