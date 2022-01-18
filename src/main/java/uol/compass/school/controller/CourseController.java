package uol.compass.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uol.compass.school.dto.request.CourseRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.service.CourseService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {

        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid CourseRequestDTO courseRequestDTO) {
        return this.courseService.create(courseRequestDTO);
    }

    @GetMapping
    public List<CourseDTO> findAll(@RequestParam(required = false) String name) {
        return this.courseService.findAll(name);
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable Long id) {
        return this.courseService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody CourseRequestDTO courseRequestDTO) {
        return this.courseService.update(id, courseRequestDTO);
    }

    @DeleteMapping("/{id}")
    public MessageResponseDTO deleteById(@PathVariable Long id) {
        return this.courseService.deleteById(id);
    }

    @PostMapping("/{courseId}/educators/{educatorId}")
    public MessageResponseDTO linkAEducator(@PathVariable Long courseId, @PathVariable Long educatorId) {
        return courseService.linkAEducator(courseId, educatorId);
    }

    @DeleteMapping("/{courseId}/educators/{educatorId}")
    public MessageResponseDTO unlinkAEducator(@PathVariable Long courseId, @PathVariable Long educatorId) {
        return courseService.unlinkAEducator(courseId, educatorId);
    }

}