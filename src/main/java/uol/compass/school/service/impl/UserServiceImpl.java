package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.UserRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.User;
import uol.compass.school.exception.UserAlreadyExistsException;
import uol.compass.school.repository.UserRepository;
import uol.compass.school.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(UserRequestDTO userRequestDTO) {
        verifyIfUsernameExists(userRequestDTO.getUsername());

        User userToSave = modelMapper.map(userRequestDTO, User.class);
        userToSave.setPassword(passwordEncoder.encode(userToSave.getPassword()));

        User savedUser = userRepository.save(userToSave);

        return MessageResponseDTO.builder()
                .message(String.format("User %s with id %s successfully created", savedUser.getUsername(), savedUser.getId()))
                .build();
    }

    @Override
    public MessageResponseDTO update(Long id, UserRequestDTO userRequestDTO) {
        User user = verifyIfUserExists(id);
        userRequestDTO.setId(user.getId());

        User userToUpdate = modelMapper.map(userRequestDTO, User.class);
        userToUpdate.setPassword(passwordEncoder.encode(userToUpdate.getPassword()));

        User updatedUser = userRepository.save(userToUpdate);

        return MessageResponseDTO.builder()
                .message(String.format("User %s with id %s successfully updated", updatedUser.getUsername(), updatedUser.getId()))
                .build();
    }

    @Override
    public void delete(Long id) {
        verifyIfUserExists(id);
        userRepository.deleteById(id);
    }

    private void verifyIfUsernameExists(String username) {
        Optional<User> userFound = userRepository.findByUsername(username);
        userFound.ifPresent(user -> {
            throw new UserAlreadyExistsException(username);
        });
    }

    private User verifyIfUserExists(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find user with id %s", id)));
    }
}
