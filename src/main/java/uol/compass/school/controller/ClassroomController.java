package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.*;
import uol.compass.school.service.ClassroomService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/classrooms")
public class ClassroomController {

    private ClassroomService classroomService;

    @Autowired
    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) ClassroomRequestDTO classroomRequestDTO) {
        return classroomService.create(classroomRequestDTO);
    }

    @GetMapping
    public List<ClassroomDTO> findAll(@RequestParam(required = false) Boolean status) {
        return classroomService.findAll(status);
    }

    @GetMapping("/{id}")
    public ClassroomDTO findById(@PathVariable Long id) {
        return classroomService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid ClassroomRequestDTO classroomRequestDTO) {
        return classroomService.update(id, classroomRequestDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO delete(@PathVariable Long id) {
        return classroomService.delete(id);
    }

    @PostMapping("/{classroomId}/courses/{courseId}")
    public MessageResponseDTO linkACourse(@PathVariable Long classroomId, @PathVariable Long courseId) {
        return classroomService.linkACourse(classroomId, courseId);
    }

    @DeleteMapping("/{classroomId}/courses/{courseId}")
    public MessageResponseDTO unlinkACourse(@PathVariable Long classroomId, @PathVariable Long courseId) {
        return classroomService.unlinkACourse(classroomId, courseId);
    }

    @PostMapping("/{classroomId}/students/{studentId}")
    public MessageResponseDTO linkAStudent(@PathVariable Long classroomId, @PathVariable Long studentId) {
        return classroomService.linkAStudent(classroomId, studentId);
    }

    @DeleteMapping("/{classroomId}/students/{studentId}")
    public MessageResponseDTO unlinkAStudent(@PathVariable Long classroomId, @PathVariable Long studentId) {
        return classroomService.unlinkAStudent(classroomId, studentId);
    }
}
