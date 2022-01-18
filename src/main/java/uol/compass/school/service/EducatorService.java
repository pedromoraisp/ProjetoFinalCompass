package uol.compass.school.service;

import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;

import java.util.List;

public interface EducatorService {

    MessageResponseDTO create(EducatorRequestDTO educatorRequestDTO);

    List<EducatorDTO> findAll(String name);

    EducatorDTO findById(Long id);

    MessageResponseDTO update(Long id, EducatorRequestDTO educatorRequestDTO);

    MessageResponseDTO deleteById(Long id);
}