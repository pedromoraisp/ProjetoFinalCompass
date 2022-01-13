package uol.compass.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.Utils.UserUtils;
import uol.compass.school.dto.request.UserRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.User;
import uol.compass.school.exception.UserAlreadyExistsException;
import uol.compass.school.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void whenUserIsInformedThenItShouldBeCreated() {
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();
        User user = UserUtils.createUser();
        String expectedMessage = "User jenniferuser with id 1 successfully created";

        when(userRepository.findByEmailOrUsername(userRequestDTO.getEmail(), userRequestDTO.getUsername()))
                .thenReturn(Optional.empty());
        when(modelMapper.map(userRequestDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        MessageResponseDTO messageResponseDTO = userService.create(userRequestDTO);

        assertEquals(expectedMessage, messageResponseDTO.getMessage());
    }

    @Test
    void whenExistingUserIsInformedThenThrowAnException() {
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();
        User user = UserUtils.createUser();

        when(userRepository.findByEmailOrUsername(userRequestDTO.getEmail(), userRequestDTO.getUsername()))
                .thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(userRequestDTO));
    }

    @Test
    void whenUserIsInformedThenItShouldBeDeleted() {
        Long id = 1L;
        User user = UserUtils.createUser();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(id);

        userService.delete(id);

        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void whenUserIsInformedThenItShouldReturnAnException() {
        Long id = 1L;
        User user = UserUtils.createUser();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.delete(id));
    }

    @Test
    void whenUserIsInformedThenItShouldBeUpdated() {
        Long id = 1L;
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();
        User user = UserUtils.createUser();
        String expectedMessage = "User jenniferuser with id 1 successfully updated";

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(modelMapper.map(userRequestDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        MessageResponseDTO messageResponseDTO = userService.update(id, userRequestDTO);

        assertEquals(expectedMessage, messageResponseDTO.getMessage());
    }

    @Test
    void whenUserIsInformedToUpdateThenReturnAnException() {
        Long id = 1L;
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.update(id, userRequestDTO));
    }
}