package uol.compass.school.service;

import uol.compass.school.dto.request.ResponsibleRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.ResponsibleDTO;


import java.util.List;

public interface ResponsibleService {

    MessageResponseDTO create(ResponsibleRequestDTO responsibleRequestDTO);

    List<ResponsibleDTO> findAll(String name);

    ResponsibleDTO findById(Long id);

    MessageResponseDTO update(Long id, ResponsibleRequestDTO responsibleRequestDTO);

    MessageResponseDTO deleteById(Long id);
}
