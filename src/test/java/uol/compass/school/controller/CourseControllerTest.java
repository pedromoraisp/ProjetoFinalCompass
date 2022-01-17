package uol.compass.school.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import uol.compass.school.Utils.CourseUtils;
import uol.compass.school.Utils.JsonUtils;
import uol.compass.school.dto.request.CourseRequestDTO;
import uol.compass.school.dto.response.CourseDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.service.CourseService;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(courseController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledReturnCreatedStatus() throws Exception {
        CourseRequestDTO courseRequestDTO = CourseUtils.createCourseRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course with id 1 was successfully created")
                .build();

        when(courseService.create(courseRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(courseRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTWithIncorrectFieldsIsCalledReturnBadRequestStatus() throws Exception {
        CourseRequestDTO courseRequestDTO = CourseUtils.createCourseRequestDTO();
        courseRequestDTO.setName(null);

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(courseRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindAllIsCalledReturnOKStatus() throws Exception {
        CourseDTO expectedCourseDTO = CourseUtils.createCourseDTO();

        when(courseService.findAll(null)).thenReturn(Collections.singletonList(expectedCourseDTO));

        mockMvc.perform(get("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedCourseDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedCourseDTO.getName())));

    }


    @Test
    void whenFindByIdIsCalledReturnOKStatus() throws Exception {
        Long id = 1L;
        CourseDTO expectedCourseDTO = CourseUtils.createCourseDTO();

        when(courseService.findById(id)).thenReturn(expectedCourseDTO);

        mockMvc.perform(get("/api/v1/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedCourseDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedCourseDTO.getName())));
    }

    @Test
    void whenPUTIsCalledReturnOKStatus() throws Exception {
        Long id = 1L;
        CourseRequestDTO courseRequestDTO = CourseUtils.createCourseRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course with id 1 was successfully updated")
                .build();

        when(courseService.update(id, courseRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(put("/api/v1/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(courseRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenDELETETIsCalledReturnOKStatus() throws Exception {
        Long id = 1L;
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Course with id 1 was successfully deleted")
                .build();

        when(courseService.deleteById(id)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/courses/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }
}
