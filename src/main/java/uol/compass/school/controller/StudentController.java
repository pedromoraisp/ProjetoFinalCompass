package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.StudentFormDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.service.StudentService;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid StudentFormDTO studentFormDTO) {
        return this.studentService.create(studentFormDTO);
    }

    @GetMapping
    public List<StudentDTO> findAll(@RequestParam(required = false) String name) {
        return this.studentService.findAll(name);
    }

    @GetMapping("/{id}")
    public StudentDTO findById(@PathVariable Long id) {
        return this.studentService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody StudentFormDTO studentFormDTO) {
        return this.studentService.update(id, studentFormDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO deleteById(@PathVariable Long id) {
        return this.studentService.deleteById(id);
    }
}
