package uol.compass.school.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.dto.request.CoordinatorRequestDTO;
import uol.compass.school.dto.response.CoordinatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.Coordinator;
import uol.compass.school.repository.CoordinatorRepository;
import uol.compass.school.service.CoordinatorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoordinatorServiceImpl implements CoordinatorService {

    private CoordinatorRepository coordinatorRepository;

    private ModelMapper modelMapper;

    @Autowired
    public CoordinatorServiceImpl(CoordinatorRepository coordinatorRepository, ModelMapper modelMapper) {
        this.coordinatorRepository = coordinatorRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public MessageResponseDTO create(CoordinatorRequestDTO coordinatorRequestDTO) {
        Coordinator coordinatorToSave = modelMapper.map(coordinatorRequestDTO, Coordinator.class);
        Coordinator savedCoordinator = coordinatorRepository.save(coordinatorToSave);

        return MessageResponseDTO.builder()
                .message(String.format("Coordinator %s with id %s was successfully created", savedCoordinator.getName(), savedCoordinator.getId()))
                .build();
    }

    @Override
    public List<CoordinatorDTO> findAll() {
        List<Coordinator> coordinators = coordinatorRepository.findAll();
        return coordinators.stream().map(coordinator -> modelMapper.map(coordinator, CoordinatorDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CoordinatorDTO findById(Long id) {
        Coordinator coordinator = checkIfCoordinatorExists(id);
        return modelMapper.map(coordinator, CoordinatorDTO.class);
    }

    @Override
    public MessageResponseDTO update(Long id, CoordinatorRequestDTO coordinatorRequestDTO) {
        checkIfCoordinatorExists(id);

        Coordinator coordinatorToUpdate = modelMapper.map(coordinatorRequestDTO, Coordinator.class);
        coordinatorToUpdate.setId(id);

        coordinatorRepository.save(coordinatorToUpdate);

        return MessageResponseDTO.builder()
                .message(String.format("Coordinator with id %s was successfully updated", id))
                .build();
    }

    @Override
    public MessageResponseDTO delete(Long id) {
        checkIfCoordinatorExists(id);
        coordinatorRepository.deleteById(id);

        return MessageResponseDTO.builder()
                .message(String.format("Coordinator with id %s was successfully deleted", id))
                .build();
    }

    private Coordinator checkIfCoordinatorExists(Long id) {
        return coordinatorRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Unable to find coordinator with id %s", id)));
    }
}
