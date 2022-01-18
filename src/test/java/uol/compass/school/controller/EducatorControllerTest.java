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
import uol.compass.school.Utils.EducatorUtils;
import uol.compass.school.Utils.JsonUtils;
import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.service.EducatorService;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EducatorControllerTest {

    @Mock
    private EducatorService educatorService;

    @InjectMocks
    private EducatorController educatorController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(educatorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenShouldReturnCreatedStatus() throws Exception {
        EducatorRequestDTO expectedEducatorRequestDTO = EducatorUtils.createEducatorRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Educator Mariana with id 1 was successfully created")
                .build();

        when(educatorService.create(expectedEducatorRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/educators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedEducatorRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTIsCalledWithIncorrectFieldThenShouldReturnBadRequestStatus() throws Exception {
        EducatorRequestDTO expectedEducatorRequestDTO = EducatorUtils.createEducatorRequestDTO();
        expectedEducatorRequestDTO.setName(null);

        mockMvc.perform(post("/api/v1/educators")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedEducatorRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindAllIsCalledThenShouldReturnOKStatus() throws Exception {
        EducatorDTO expectedEducatorDTO = EducatorUtils.createEducatorDTO();

        when(educatorService.findAll(null)).thenReturn(Collections.singletonList(expectedEducatorDTO));

        mockMvc.perform(get("/api/v1/educators")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedEducatorDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedEducatorDTO.getName())))
                .andExpect(jsonPath("$[0].cpf", is(expectedEducatorDTO.getCpf())));
    }

    @Test
    void whenFindByIdIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        EducatorDTO expectedEducatorDTO = EducatorUtils.createEducatorDTO();

        when(educatorService.findById(id)).thenReturn(expectedEducatorDTO);

        mockMvc.perform(get("/api/v1/educators/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedEducatorDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedEducatorDTO.getName())))
                .andExpect(jsonPath("$.cpf", is(expectedEducatorDTO.getCpf())));
    }

    @Test
    void whenPUTIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        EducatorRequestDTO expectedEducatorRequestDTO = EducatorUtils.createEducatorRequestDTO();

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Educator with id 1 was successfully updated")
                .build();

        when(educatorService.update(id, expectedEducatorRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(put("/api/v1/educators/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedEducatorRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenDELETEIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Educator with id 1 was successfully deleted")
                .build();

        when(educatorService.deleteById(id)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/educators/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

}