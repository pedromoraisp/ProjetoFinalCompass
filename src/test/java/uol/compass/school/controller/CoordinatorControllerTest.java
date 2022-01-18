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
import uol.compass.school.Utils.CoordinatorUtils;
import uol.compass.school.Utils.JsonUtils;
import uol.compass.school.dto.request.CoordinatorRequestDTO;
import uol.compass.school.dto.response.CoordinatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.service.CoordinatorService;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CoordinatorControllerTest {

    @Mock
    private CoordinatorService coordinatorService;

    @InjectMocks
    private CoordinatorController coordinatorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(coordinatorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenReturnCreatedStatus() throws Exception {
        CoordinatorRequestDTO coordinatorRequestDTO = CoordinatorUtils.createCoordinatorRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Class with id 1 was successfully created")
                .build();

        when(coordinatorService.create(coordinatorRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/coordinators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(coordinatorRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTIsCalledWithInvalidArgumentsThenReturnAnException() throws Exception {
        CoordinatorRequestDTO coordinatorRequestDTO = CoordinatorUtils.createCoordinatorRequestDTO();
        coordinatorRequestDTO.setCpf(null);

        mockMvc.perform(post("/api/v1/coordinators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(coordinatorRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETAllIsCalledThenReturnOkStatus() throws Exception {
        CoordinatorDTO coordinatorDTO = CoordinatorUtils.createCoordinatorDTO();

        when(coordinatorService.findAll()).thenReturn(Collections.singletonList(coordinatorDTO));

        mockMvc.perform(get("/api/v1/coordinators")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(coordinatorDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].cpf", is(coordinatorDTO.getCpf())))
                .andExpect(jsonPath("$[0].name", is(coordinatorDTO.getName())));
    }

    @Test
    void whenGETByIdIsCalledThenReturnOkStatus() throws Exception {
        CoordinatorDTO coordinatorDTO = CoordinatorUtils.createCoordinatorDTO();

        when(coordinatorService.findById(coordinatorDTO.getId())).thenReturn(coordinatorDTO);

        mockMvc.perform(get("/api/v1/coordinators/" + coordinatorDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(coordinatorDTO.getId().intValue())))
                .andExpect(jsonPath("$.cpf", is(coordinatorDTO.getCpf())))
                .andExpect(jsonPath("$.name", is(coordinatorDTO.getName())));
    }

    @Test
    void whenPUTIsCalledThenReturnOkStatus() throws Exception {
        Long id = 1L;
        CoordinatorRequestDTO coordinatorRequestDTO = CoordinatorUtils.createCoordinatorRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Coordinator with id 1 was successfully updated")
                .build();

        when(coordinatorService.update(id, coordinatorRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(put("/api/v1/coordinators/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(coordinatorRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenDELETEIsCalledThenReturnOkStatus() throws Exception {
        Long id = 1L;
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Coordinator with id 1 was successfully deleted")
                .build();

        when(coordinatorService.delete(id)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/coordinators/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }


}