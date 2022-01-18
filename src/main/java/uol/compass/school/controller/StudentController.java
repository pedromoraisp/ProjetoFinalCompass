package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.StudentRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.dto.response.StudentOccurrenceDTO;
import uol.compass.school.service.StudentService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid StudentRequestDTO studentRequestDTO) {
        return this.studentService.create(studentRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<StudentDTO> findAll(@RequestParam(required = false) String name) {
        return this.studentService.findAll(name);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public StudentDTO findById(@PathVariable Long id) {
        return this.studentService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody StudentRequestDTO studentRequestDTO) {
        return this.studentService.update(id, studentRequestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public MessageResponseDTO deleteById(@PathVariable Long id) {
        return this.studentService.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/occurrences")
    public List<OccurrenceDTO> findAllOccurrences(@PathVariable Long id,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialDate,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finalDate) {
        return studentService.findAllOccurrences(id, initialDate, finalDate);
    }

    @PreAuthorize("hasRole('RESPONSIBLE')")
    @GetMapping("/occurrences")
    public Set<StudentOccurrenceDTO> getOccurrencesFromStudentsLinkedToUser() {
        return studentService.getOccurrencesFromStudentsLinkedToUser();
    }
}