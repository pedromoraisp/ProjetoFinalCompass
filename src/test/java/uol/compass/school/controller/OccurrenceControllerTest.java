package uol.compass.school.controller;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import uol.compass.school.Utils.JsonUtils;
import uol.compass.school.Utils.OccurrenceUtils;
import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.service.OccurrenceService;
import uol.compass.school.enums.OccurrenceType;

import java.util.Collections;

import static org.hamcrest.core.Is.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OccurrenceControllerTest {

    @Mock
    private OccurrenceService occurrenceService;

    @InjectMocks
    private OccurrenceController occurrenceController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(occurrenceController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenShouldReturnCreatedStatus() throws Exception {
    	OccurrenceRequestDTO expectedOccurrenceRequestDTO = OccurrenceUtils.createOccurrenceRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Occurrence with id 1 was successfully created")
                .build();

        when(occurrenceService.create(expectedOccurrenceRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(post("/api/v1/occurrences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.asJsonString(expectedOccurrenceRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

    @Test
    void whenPOSTIsCalledWithIncorrectFieldThenShouldReturnBadRequestStatus() throws Exception {
    	OccurrenceRequestDTO expectedOccurrenceRequestDTO = OccurrenceUtils.createOccurrenceRequestDTO();
        expectedOccurrenceRequestDTO.setId(null);

        mockMvc.perform(post("/api/v1/occurrence")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedOccurrenceRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenFindAllIsCalledThenShouldReturnOKStatus() throws Exception {
    	OccurrenceDTO expectedOccurrenceDTO = OccurrenceUtils.createOccurrenceDTO();

        when(occurrenceService.findAll(null)).thenReturn(Collections.singletonList(expectedOccurrenceDTO));

        mockMvc.perform(get("/api/v1/occurrences")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedOccurrenceDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].date", is(expectedOccurrenceDTO.getDate())))
                .andExpect(jsonPath("$[0].description", is(expectedOccurrenceDTO.getDescription())))
                .andExpect(jsonPath("$[0].communicationType", is(expectedOccurrenceDTO.getCommunicationType())))
                .andExpect(jsonPath("$[0].occurrenceType", is(expectedOccurrenceDTO.getOccurrenceType())))
                .andExpect(jsonPath("$[0].student", is(expectedOccurrenceDTO.getStudent())));
    }

    @Test
    void whenFindByIdIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        OccurrenceDTO expectedOccurrenceDTO = OccurrenceUtils.createOccurrenceDTO();

        when(occurrenceService.findById(id)).thenReturn(expectedOccurrenceDTO);

        mockMvc.perform(get("/api/v1/occurrences/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedOccurrenceDTO.getId().intValue())))
                .andExpect(jsonPath("$.date", is(expectedOccurrenceDTO.getDate())))
                .andExpect(jsonPath("$.description", is(expectedOccurrenceDTO.getDescription())))
                .andExpect(jsonPath("$.communicationType", is(expectedOccurrenceDTO.getCommunicationType())))
                .andExpect(jsonPath("$.occurrenceType", is(expectedOccurrenceDTO.getOccurrenceType())))
                .andExpect(jsonPath("$.student", is(expectedOccurrenceDTO.getStudent())));
    }

    @Test
    void whenPUTIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;
        OccurrenceRequestDTO expectedOccurrenceRequestDTO = OccurrenceUtils.createOccurrenceRequestDTO();

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Occurrence with id 1 was successfully updated")
                .build();

        when(occurrenceService.update(id, expectedOccurrenceRequestDTO)).thenReturn(expectedMessageResponse);

        mockMvc.perform(put("/api/v1/occurrences/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtils.asJsonString(expectedOccurrenceRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }
    @Test
    void whenDELETEIsCalledThenShouldReturnOKStatus() throws Exception {
        Long id = 1L;

        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Occurrence with id 1 was successfully deleted")
                .build();

        when(occurrenceService.deleteById(id)).thenReturn(expectedMessageResponse);

        mockMvc.perform(delete("/api/v1/occurrences/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponse.getMessage())));
    }

}