package uol.compass.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.Utils.CourseUtils;
import uol.compass.school.Utils.EducatorUtils;
import uol.compass.school.dto.request.CourseRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.Course;
import uol.compass.school.entity.Educator;
import uol.compass.school.repository.CourseRepository;
import uol.compass.school.repository.EducatorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EducatorRepository educatorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    void whenCourseIsInformedBeCreated() {
        CourseRequestDTO courseRequestDTO = CourseUtils.createCourseRequestDTO();
        Course expectedCourse = CourseUtils.createCourse();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course Music with id 1 was successfully created")
                .build();

        when(courseRepository.save(expectedCourse)).thenReturn(expectedCourse);
        when(modelMapper.map(courseRequestDTO, Course.class)).thenReturn(expectedCourse);

        MessageResponseDTO messageResponseDTO = courseService.create(courseRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenFindAllWithoutParameterIsCalledThenReturnAllCourses() {
        Course expectedCourse = CourseUtils.createCourse();
        CourseDTO expectedCourseDTO = CourseUtils.createCourseDTO();
        List<CourseDTO> expectedCoursesDTO = Collections.singletonList(expectedCourseDTO);

        when(courseRepository.findAll()).thenReturn(Collections.singletonList(expectedCourse));
        when(modelMapper.map(expectedCourse, CourseDTO.class)).thenReturn(expectedCourseDTO);

        List<CourseDTO> CoursesDTO = courseService.findAll(null);

        assertEquals(CoursesDTO, expectedCoursesDTO);
    }

    @Test
    void whenCourseIsInformedThenItShouldBeReturned() {
        Long id = 1L;
        Course expectedCourse = CourseUtils.createCourse();
        CourseDTO expectedCourseDTO = CourseUtils.createCourseDTO();

        when(courseRepository.findById(id)).thenReturn(Optional.of(expectedCourse));
        when(modelMapper.map(expectedCourse, CourseDTO.class)).thenReturn(expectedCourseDTO);

        CourseDTO courseFound = courseService.findById(id);

        assertEquals(expectedCourseDTO, courseFound);
    }

    @Test
    void whenNonexistentCourseIdIsInformedReturnResponseStatusException() {
        Long id = 1L;

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> courseService.findById(id));
    }

    @Test
    void whenCourseIsInformedThenShouldBeUpdated() {
        Long id = 1L;
        CourseRequestDTO courseRequestDTO = CourseUtils.createCourseRequestDTO();
        Course expectedCourse = CourseUtils.createCourse();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course with id 1 was successfully updated")
                .build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(expectedCourse));
        when(modelMapper.map(courseRequestDTO, Course.class)).thenReturn(expectedCourse);
        when(courseRepository.save(expectedCourse)).thenReturn(expectedCourse);

        MessageResponseDTO messageResponseDTO = courseService.update(expectedCourse.getId(), courseRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenCourseIsInformedThenItShouldBeDeleted() {
        Long id = 1L;
        Course expectedCourse = CourseUtils.createCourse();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course with id 1 was successfully deleted")
                .build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(expectedCourse));

        MessageResponseDTO messageResponseDTO = courseService.deleteById(id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenCourseIdAndEducatorIdAreInformedThenTheyShouldBeLinked() {
        Long id = 1L;
        Course expectedCourse= CourseUtils.createCourse();
        Educator expectedEducator = EducatorUtils.createEducator();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course with id 1 was linked to the educator with id 1 successfully")
                .build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(expectedCourse));
        when(educatorRepository.findById(id)).thenReturn(Optional.of(expectedEducator));

        MessageResponseDTO messageResponseDTO = courseService.linkAEducator(id, id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenCourseIdAndEducatorIdAreInformedThenTheyShouldBeUnlinked() {
        Long id = 1L;
        Course expectedCourse= CourseUtils.createCourse();
        Educator expectedEducator = EducatorUtils.createEducator();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course with id 1 was unlinked to the educator with id 1 successfully")
                .build();

        when(courseRepository.findById(id)).thenReturn(Optional.of(expectedCourse));
        when(educatorRepository.findById(id)).thenReturn(Optional.of(expectedEducator));

        MessageResponseDTO messageResponseDTO = courseService.unlinkAEducator(id, id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

}
