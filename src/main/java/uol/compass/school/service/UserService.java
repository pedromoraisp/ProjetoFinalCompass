package uol.compass.school.service;

import uol.compass.school.dto.request.UserRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;

public interface UserService {

    MessageResponseDTO create(UserRequestDTO userRequestDTO);

    MessageResponseDTO update(Long id, UserRequestDTO userRequestDTO);

    void delete(Long id);
}
