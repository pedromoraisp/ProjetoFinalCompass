package uol.compass.school.security.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uol.compass.school.Utils.UserUtils;
import uol.compass.school.dto.request.UserRequestDTO;
import uol.compass.school.entity.User;
import uol.compass.school.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void whenUsernameIsInformedThenItShouldReturnTheUser() {
        UserRequestDTO userRequestDTO = UserUtils.createUserRequestDTO();
        User user = UserUtils.createUser();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_" + userRequestDTO.getRole().getDescription());

        when(userRepository.findByUsername(userRequestDTO.getUsername())).thenReturn(Optional.of(user));

        UserDetails userDetails = authenticationService.loadUserByUsername(userRequestDTO.getUsername());

        assertEquals(userRequestDTO.getUsername(), userDetails.getUsername());
        assertEquals(userRequestDTO.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().contains(simpleGrantedAuthority));
    }
}