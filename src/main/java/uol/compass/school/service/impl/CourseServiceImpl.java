package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.CourseRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.Course;
import uol.compass.school.repository.CourseRepository;
import uol.compass.school.service.CourseService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(CourseRequestDTO courseRequestDTO) {
        Course courseToSave = modelMapper.map(courseRequestDTO, Course.class);
        Course savedCourse = this.courseRepository.save(courseToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Course %s with id %s was successfully created", savedCourse.getName(), savedCourse.getId()))
                .build();
    }

    @Override
    public List<CourseDTO> findAll(String name) {
        List<Course> courses;

        if (name == null) {
            courses = this.courseRepository.findAll();
        } else {
            courses = this.courseRepository.findByNameStartingWith(name);
        }

        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CourseDTO findById(Long id) {
        Course course = this.courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find course with id %s", id)));

        return modelMapper.map(course, CourseDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, CourseRequestDTO courseRequestDTO) {
        Course course = this.courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find course with id %s", id)));

        Course courseToSave = modelMapper.map(courseRequestDTO, Course.class);
        courseToSave.setId(id);

        this.courseRepository.save(courseToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Course with id %s was successfully updated", course.getId()))
                .build();
    }

    @Override
    public MessageResponseDTO deleteById(Long id) {
        this.courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find course with id %s", id)));

        this.courseRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Course with id %s was successfully deleted", id))
                .build();
    }

    @Override
    public Set<CourseDTO> findAllCourses(Long id) {
        return null;
    }



}
