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
import uol.compass.school.Utils.UserUtils;
import uol.compass.school.dto.request.UserRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.service.UserService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uol.compass.school.Utils.JsonUtils.asJsonString;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenReturnCreatedStatus() throws Exception {
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();
        MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO.builder()
                .message("User jenniferuser with id 1 successfully created")
                .build();

        when(userService.create(userRequestDTO)).thenReturn(expectedMessageResponseDTO);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedMessageResponseDTO.getMessage())));
    }

    @Test
    void whenPOSTIsCalledWithIncorrectFieldsThenReturnBadRequestStatus() throws Exception {
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();
        userRequestDTO.setName(null);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenPUTIsCalledThenReturnOKStatus() throws Exception {
        Long id = 1L;
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();
        MessageResponseDTO expectedMessageResponseDTO = MessageResponseDTO.builder()
                .message("User jenniferuser with id 1 successfully updated")
                .build();

        when(userService.update(id, userRequestDTO)).thenReturn(expectedMessageResponseDTO);

        mockMvc.perform(put("/api/v1/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedMessageResponseDTO.getMessage())));
    }

    @Test
    void whenDELETEsCalledThenReturnNoContentStatus() throws Exception {
        Long id = 1L;

        doNothing().when(userService).delete(id);

        mockMvc.perform(delete("/api/v1/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}