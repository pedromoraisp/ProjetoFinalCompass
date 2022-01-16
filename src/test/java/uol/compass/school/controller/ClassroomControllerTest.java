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
import uol.compass.school.Utils.ClassroomUtils;
import uol.compass.school.Utils.JsonUtils;
import uol.compass.school.Utils.StudentUtils;
import uol.compass.school.dto.request.ClassroomRequestDTO;
import uol.compass.school.dto.response.ClassroomDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.StudentNameDTO;
import uol.compass.school.service.ClassroomService;

import java.util.Collections;
import java.util.Set;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ClassroomControllerTest {

    @Mock
    private ClassroomService classroomService;

    @InjectMocks
    private ClassroomController classroomController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(classroomController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenShouldReturnCreatedStatus() throws Exception {
        ClassroomRequestDTO classroomRequestDTO = ClassroomUtils.createClassroomRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Class with id 1 was successfully created")
                .build();

        when(classroomService.create(classroomRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(classroomRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTWithIncorrectFieldsIsCalledThenShouldReturnBadRequestStatus() throws Exception {
        ClassroomRequestDTO classroomRequestDTO = ClassroomUtils.createClassroomRequestDTO();
        classroomRequestDTO.setStatus(null);

        mockMvc.perform(post("/api/v1/classrooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(classroomRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETAllIsCalledThenShouldReturnOKStatus() throws Exception {
        ClassroomDTO classroomDTO = ClassroomUtils.createClassroomDTO();

        when(classroomService.findAll(null)).thenReturn(Collections.singletonList(classroomDTO));

        mockMvc.perform(get("/api/v1/classrooms")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(classroomDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].status", is(classroomDTO.getStatus())))
                .andExpect(jsonPath("$[0].initialDate", is(classroomDTO.getInitialDate())));
    }

    @Test
    void whenGETByIdIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        ClassroomDTO classroomDTO = ClassroomUtils.createClassroomDTO();

        when(classroomService.findById(id)).thenReturn(classroomDTO);

        mockMvc.perform(get("/api/v1/classrooms/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(classroomDTO.getId().intValue())))
                .andExpect(jsonPath("$.status", is(classroomDTO.getStatus())))
                .andExpect(jsonPath("$.initialDate", is(classroomDTO.getInitialDate())));
    }

    @Test
    void whenPUTIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        ClassroomRequestDTO classroomRequestDTO = ClassroomUtils.createClassroomRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Class with id 1 was successfully updated")
                .build();

        when(classroomService.update(id, classroomRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(put("/api/v1/classrooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(classroomRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenDELETETIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Class with id 1 was successfully deleted")
                .build();

        when(classroomService.delete(id)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/classrooms/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTToLinkAStudentIsCalledThenReturnOKStatus() throws Exception {
        Long studentId = 1L;
        Long classroomId = 1L;
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Classroom with id 1 was linked to the student with id 1 successfully")
                .build();

        when(classroomService.linkAStudent(classroomId, studentId)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/classrooms/"+ classroomId +"/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTToUnlinkAStudentIsCalledThenReturnOKStatus() throws Exception {
        Long studentId = 1L;
        Long classroomId = 1L;
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Classroom with id 1 was unlinked to the student with id 1 successfully")
                .build();

        when(classroomService.unlinkAStudent(classroomId, studentId)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/classrooms/"+ classroomId +"/students/" + studentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenGETToGetAllStudentsIsCalledThenReturnOKStatus() throws Exception {
        Long id = 1L;
        StudentNameDTO studentNameDTO = StudentUtils.createStudentNameDTO();

        when(classroomService.getAllStudents(id)).thenReturn(Collections.singleton(studentNameDTO));

        mockMvc.perform(get("/api/v1/classrooms/"+ id +"/students")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]name", is(studentNameDTO.getName())));
    }
}