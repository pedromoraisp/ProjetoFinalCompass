package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.service.EducatorService;
//incluir de oficina e turma???

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/educators")
public class EducatorController {

    private EducatorService educatorService;

    @Autowired
    public EducatorController(EducatorService educatorService) {
        this.educatorService = educatorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid EducatorRequestDTO educatorRequestDTO) {
        return this.educatorService.create(educatorRequestDTO);
    }

    @GetMapping
    public List<EducatorDTO> findAll(@RequestParam(required = false) String name) {
        return this.educatorService.findAll(name);
    }

    @GetMapping("/{id}")
    public EducatorDTO findById(@PathVariable Long id) {
        return this.educatorService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody EducatorRequestDTO educatorRequestDTO) {
        return this.educatorService.update(id, educatorRequestDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO deleteById(@PathVariable Long id) {
        return this.educatorService.deleteById(id);
    }

    
}
