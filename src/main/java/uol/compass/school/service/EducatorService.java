package uol.compass.school.service;

import java.util.List;

import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;

public interface EducatorService {

    MessageResponseDTO create(EducatorRequestDTO educatorRequestDTO);

    List<EducatorDTO> findAll(String name);

    EducatorDTO findById(Long id);

    MessageResponseDTO update(Long id, EducatorRequestDTO educatorRequestDTO);

    MessageResponseDTO deleteById(Long id);

    
}
