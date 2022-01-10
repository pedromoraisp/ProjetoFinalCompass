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
import uol.compass.school.Utils.ResponsibleUtils;
import uol.compass.school.dto.request.ResponsibleRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.ResponsibleDTO;
import uol.compass.school.service.ResponsibleService;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ResponsibleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ResponsibleService responsibleService;

    @InjectMocks
    private ResponsibleController responsibleController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(responsibleController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledReturnCreatedStatus() throws Exception {
        ResponsibleRequestDTO expectedResponsibleRequestDTO = ResponsibleUtils.createResponsibleRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Responsible Edivan Peixoto with id 1 was successfully created")
                .build();

        when(responsibleService.create(expectedResponsibleRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/responsible")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedResponsibleRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTIsCalledWithIncorrectFieldThenShouldReturnBadRequestStatus() throws Exception {
        ResponsibleRequestDTO expectedResponsibleRequestDTO = ResponsibleUtils.createResponsibleRequestDTO();
        expectedResponsibleRequestDTO.setName(null);

        mockMvc.perform(post("/api/v1/responsible")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedResponsibleRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindAllIsCalledThenShouldReturnOKStatus() throws Exception {
        ResponsibleDTO expectedResponsibleDTO = ResponsibleUtils.createResponsibleDTO();

        when(responsibleService.findAll(null)).thenReturn(Collections.singletonList(expectedResponsibleDTO));

        mockMvc.perform(get("/api/v1/responsible")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedResponsibleDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedResponsibleDTO.getName())))
                .andExpect(jsonPath("$[0].cpf", is(expectedResponsibleDTO.getCpf())));
    }


    @Test
    void whenFindByIdIsCalledReturnOKStatus() throws Exception {
        Long id = 1L;
        ResponsibleDTO expectedResponsibleDTO = ResponsibleUtils.createResponsibleDTO();

        when(responsibleService.findById(id)).thenReturn(expectedResponsibleDTO);

        mockMvc.perform(get("/api/v1/responsible/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedResponsibleDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedResponsibleDTO.getName())))
                .andExpect(jsonPath("$.cpf", is(expectedResponsibleDTO.getCpf())));
    }

    @Test
    void whenPUTIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        ResponsibleRequestDTO expectedResponsibleRequestDTO = ResponsibleUtils.createResponsibleRequestDTO();

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Responsible with id 1 was successfully updated")
                .build();

        when(responsibleService.update(id, expectedResponsibleRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(put("/api/v1/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedResponsibleRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenDELETEIsCalledReturnOKStatus() throws Exception {
        Long id = 1L;

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Responsible with id 1 was successfully deleted")
                .build();

        when(responsibleService.deleteById(id)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/responsible/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }
}
