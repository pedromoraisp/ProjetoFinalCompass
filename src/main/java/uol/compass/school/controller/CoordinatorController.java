package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.CoordinatorRequestDTO;
import uol.compass.school.dto.response.CoordinatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.service.CoordinatorService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/coordinators")
public class CoordinatorController {

    private CoordinatorService coordinatorService;

    @Autowired
    public CoordinatorController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid CoordinatorRequestDTO coordinatorRequestDTO) {
        return coordinatorService.create(coordinatorRequestDTO);
    }

    @GetMapping
    public List<CoordinatorDTO> findAll() {
        return coordinatorService.findAll();
    }

    @GetMapping("/{id}")
    public CoordinatorDTO findById(@PathVariable Long id) {
        return coordinatorService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid CoordinatorRequestDTO coordinatorRequestDTO) {
        return coordinatorService.update(id, coordinatorRequestDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO delete(@PathVariable Long id) {
        return coordinatorService.delete(id);
    }
}
