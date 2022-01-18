package uol.compass.school.service;

import uol.compass.school.dto.request.CoordinatorRequestDTO;
import uol.compass.school.dto.response.CoordinatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;

import java.util.List;

public interface CoordinatorService {

    MessageResponseDTO create(CoordinatorRequestDTO coordinatorRequestDTO);

    List<CoordinatorDTO> findAll();

    CoordinatorDTO findById(Long id);

    MessageResponseDTO update(Long id, CoordinatorRequestDTO coordinatorRequestDTO);

    MessageResponseDTO delete(Long id);

    MessageResponseDTO linkCoordinatorToUser(Long coordinatorId, Long userId);
}