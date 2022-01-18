package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.service.OccurrenceService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1/occurrences")
public class OccurrenceController {

    private OccurrenceService occurrenceService;

    @Autowired
    public OccurrenceController(OccurrenceService occurrenceService) {
        this.occurrenceService = occurrenceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid OccurrenceRequestDTO occurrenceRequestDTO) {
        return this.occurrenceService.create(occurrenceRequestDTO);
    }

    @GetMapping
    public List<OccurrenceDTO> findAll(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialDate,
                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finalDate) {
        return this.occurrenceService.findAll(initialDate, finalDate);
    }

    @GetMapping("/{id}")
    public OccurrenceDTO findById(@PathVariable Long id) {
        return this.occurrenceService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody OccurrenceRequestDTO occurrenceRequestDTO) {
        return this.occurrenceService.update(id, occurrenceRequestDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO deleteById(@PathVariable Long id) {
        return this.occurrenceService.deleteById(id);
    }

    @PostMapping("/{occurrenceId}/students/{studentId}")
    public MessageResponseDTO linkOccurrenceToStudent(@PathVariable Long occurrenceId, @PathVariable Long studentId) {
        return occurrenceService.linkOccurrenceToStudent(occurrenceId, studentId);
    }
}