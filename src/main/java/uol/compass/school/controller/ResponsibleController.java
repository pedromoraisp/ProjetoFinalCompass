package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.ResponsibleRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.ResponsibleDTO;
import uol.compass.school.dto.response.StudentNameDTO;
import uol.compass.school.service.ResponsibleService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/v1/responsible")
public class ResponsibleController {

    private ResponsibleService responsibleService;

    @Autowired
    public ResponsibleController(ResponsibleService responsibleService) {
        this.responsibleService = responsibleService;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid ResponsibleRequestDTO responsibleRequestDTO) {
        return this.responsibleService.create(responsibleRequestDTO);
    }

    @GetMapping
    public List<ResponsibleDTO> findAll(@RequestParam(required = false) String name) {
        return this.responsibleService.findAll(name);
    }

    @GetMapping("/{id}")
    public ResponsibleDTO findById(@PathVariable Long id) {
        return this.responsibleService.findById(id);
    }

    @GetMapping("/{id}/students")
    public Set<StudentNameDTO> findAllStudents(@PathVariable Long id) {
        return responsibleService.findAllStudents(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody ResponsibleRequestDTO
            responsibleRequestDTO) {
        return this.responsibleService.update(id, responsibleRequestDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO deleteById(@PathVariable Long id) {
        return this.responsibleService.deleteById(id);
    }

    @PostMapping("{responsibleId}/students/{studentId}")
    public MessageResponseDTO linkResponsibleToStudent(@PathVariable Long responsibleId, @PathVariable Long studentId) {
        return responsibleService.linkResponsibleToStudent(responsibleId, studentId);
    }

    @PostMapping("{responsibleId}/users/{userId}")
    public MessageResponseDTO linkResponsibleToUser(@PathVariable Long responsibleId, @PathVariable Long userId) {
        return responsibleService.linkResponsibleToUser(responsibleId, userId);
    }
}