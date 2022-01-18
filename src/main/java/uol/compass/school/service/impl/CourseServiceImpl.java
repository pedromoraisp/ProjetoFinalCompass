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
import uol.compass.school.entity.Educator;
import uol.compass.school.repository.CourseRepository;
import uol.compass.school.repository.EducatorRepository;
import uol.compass.school.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    private EducatorRepository educatorRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper, EducatorRepository educatorRepository) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.educatorRepository = educatorRepository;
    }

    @Override
    public MessageResponseDTO create(CourseRequestDTO courseRequestDTO) {
        Course courseToSave = modelMapper.map(courseRequestDTO, Course.class);
        Course savedCourse = courseRepository.save(courseToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Course %s with id %s was successfully created", savedCourse.getName(), savedCourse.getId()))
                .build();
    }

    @Override
    public List<CourseDTO> findAll(String name) {
        List<Course> courses;

        if (name == null) {
            courses = courseRepository.findAll();
        } else {
            courses = courseRepository.findByNameStartingWith(name);
        }

        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CourseDTO findById(Long id) {
        Course course = checkIfCourseExists(id);

        return modelMapper.map(course, CourseDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, CourseRequestDTO courseRequestDTO) {
        Course course = checkIfCourseExists(id);

        Course courseToSave = modelMapper.map(courseRequestDTO, Course.class);
        courseToSave.setId(id);

        this.courseRepository.save(courseToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Course with id %s was successfully updated", course.getId()))
                .build();
    }

    @Override
    public MessageResponseDTO deleteById(Long id) {
        checkIfCourseExists(id);

        courseRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Course with id %s was successfully deleted", id))
                .build();
    }

    @Override
    public MessageResponseDTO linkAEducator(Long courseId, Long educatorId) {
        Course course = checkIfCourseExists(courseId);
        Educator educator = checkIfEducatorExists(educatorId);

        educator.setCourse(course);

        educatorRepository.save(educator);

        return MessageResponseDTO.builder()
                .message(String.format("Course with id %s was linked to the educator with id %s successfully", course.getId(), educator.getId()))
                .build();
    }

    @Override
    public MessageResponseDTO unlinkAEducator(Long courseId, Long educatorId) {
        Course course = checkIfCourseExists(courseId);
        Educator educator = checkIfEducatorExists(educatorId);

        if(educator.getCourse().getId() == educatorId) {
            educator.setCourse(null);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This educator is not linked to this course");
        }

        educatorRepository.save(educator);

        return MessageResponseDTO.builder()
                .message(String.format("Course with id %s was unlinked to the educator with id %s successfully", course.getId(), educator.getId()))
                .build();
    }

    private Course checkIfCourseExists(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find classroom with id %s", id)));
    }

    private Educator checkIfEducatorExists(Long educatorId) {
        return educatorRepository.findById(educatorId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find educator with id %s", educatorId)));
    }
}