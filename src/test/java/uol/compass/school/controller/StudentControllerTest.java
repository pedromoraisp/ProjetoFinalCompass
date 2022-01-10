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
import uol.compass.school.Utils.JsonUtils;
import uol.compass.school.Utils.OccurrenceUtils;
import uol.compass.school.Utils.StudentUtils;
import uol.compass.school.dto.request.StudentRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.service.StudentService;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenShouldReturnCreatedStatus() throws Exception {
        StudentRequestDTO expectedStudentRequestDTO = StudentUtils.createStudentRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Student Pedro Henrique with id 1 was successfully created")
                .build();

        when(studentService.create(expectedStudentRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedStudentRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTIsCalledWithIncorrectFieldThenShouldReturnBadRequestStatus() throws Exception {
        StudentRequestDTO expectedStudentRequestDTO = StudentUtils.createStudentRequestDTO();
        expectedStudentRequestDTO.setName(null);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedStudentRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindAllIsCalledThenShouldReturnOKStatus() throws Exception {
        StudentDTO expectedStudentDTO = StudentUtils.createStudentDTO();

        when(studentService.findAll(null)).thenReturn(Collections.singletonList(expectedStudentDTO));

        mockMvc.perform(get("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedStudentDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedStudentDTO.getName())))
                .andExpect(jsonPath("$[0].cpf", is(expectedStudentDTO.getCpf())));
    }

    @Test
    void whenFindByIdIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        StudentDTO expectedStudentDTO = StudentUtils.createStudentDTO();

        when(studentService.findById(id)).thenReturn(expectedStudentDTO);

        mockMvc.perform(get("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedStudentDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedStudentDTO.getName())))
                .andExpect(jsonPath("$.cpf", is(expectedStudentDTO.getCpf())));
    }

    @Test
    void whenPUTIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        StudentRequestDTO expectedStudentRequestDTO = StudentUtils.createStudentRequestDTO();

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Student with id 1 was successfully updated")
                .build();

        when(studentService.update(id, expectedStudentRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedStudentRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenDELETEIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Student with id 1 was successfully deleted")
                .build();

        when(studentService.deleteById(id)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenFindAllOccurrencesIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        OccurrenceDTO expectedOccurrencesDTO = OccurrenceUtils.createOccurrenceDTO();

        when(studentService.findAllOccurrences(id, null, null)).thenReturn(Collections.singletonList(expectedOccurrencesDTO));

        mockMvc.perform(get("/api/v1/students/1/occurrences")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedOccurrencesDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].description", is(expectedOccurrencesDTO.getDescription())));
    }
}