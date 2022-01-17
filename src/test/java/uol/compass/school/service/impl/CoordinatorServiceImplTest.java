package uol.compass.school.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import uol.compass.school.Utils.CoordinatorUtils;
import uol.compass.school.dto.request.CoordinatorRequestDTO;
import uol.compass.school.dto.response.CoordinatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.Coordinator;
import uol.compass.school.repository.CoordinatorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoordinatorServiceImplTest {

    @Mock
    private CoordinatorRepository coordinatorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CoordinatorServiceImpl coordinatorService;

    @Test
    void whenCoordinatorIsInformedThenShouldBeCreated() {
        CoordinatorRequestDTO coordinatorRequestDTO = CoordinatorUtils.createCoordinatorRequestDTO();
        Coordinator coordinator = CoordinatorUtils.createCoordinator();

        when(modelMapper.map(coordinatorRequestDTO, Coordinator.class)).thenReturn(coordinator);
        when(coordinatorRepository.save(coordinator)).thenReturn(coordinator);

        MessageResponseDTO messageResponseDTO = coordinatorService.create(coordinatorRequestDTO);

        assertEquals("Coordinator Maria das Graças with id 1 was successfully created", messageResponseDTO.getMessage());
    }

    @Test
    void WhenFindAllIsCalledThenReturnCoordinatorList() {
        Coordinator coordinator = CoordinatorUtils.createCoordinator();
        CoordinatorDTO coordinatorDTO = CoordinatorUtils.createCoordinatorDTO();

        when(modelMapper.map(coordinator, CoordinatorDTO.class)).thenReturn(coordinatorDTO);
        when(coordinatorRepository.findAll()).thenReturn(Collections.singletonList(coordinator));

        List<CoordinatorDTO> all = coordinatorService.findAll();

        assertEquals(Collections.singletonList(coordinatorDTO), all);
    }

    @Test
    void whenCoordinatorIsInformedThenItShouldBeReturned() {
        Coordinator coordinator = CoordinatorUtils.createCoordinator();
        CoordinatorDTO coordinatorDTO = CoordinatorUtils.createCoordinatorDTO();

        when(modelMapper.map(coordinator, CoordinatorDTO.class)).thenReturn(coordinatorDTO);
        when(coordinatorRepository.findById(coordinator.getId())).thenReturn(Optional.of(coordinator));

        CoordinatorDTO byId = coordinatorService.findById(coordinator.getId());

        assertEquals(coordinatorDTO, byId);
    }

    @Test
    void whenCoordinatorIsInformedThenShouldBeUpdated() {
        CoordinatorRequestDTO coordinatorRequestDTO = CoordinatorUtils.createCoordinatorRequestDTO();
        Coordinator coordinator = CoordinatorUtils.createCoordinator();

        when(coordinatorRepository.findById(coordinator.getId())).thenReturn(Optional.of(coordinator));
        when(modelMapper.map(coordinatorRequestDTO, Coordinator.class)).thenReturn(coordinator);
        when(coordinatorRepository.save(coordinator)).thenReturn(coordinator);

        MessageResponseDTO messageResponseDTO = coordinatorService.update(coordinator.getId(), coordinatorRequestDTO);

        assertEquals("Coordinator with id 1 was successfully updated", messageResponseDTO.getMessage());
    }

    @Test
    void whenCoordinatorIsInformedThenShouldBeDeleted() {
        Coordinator coordinator = CoordinatorUtils.createCoordinator();
        when(coordinatorRepository.findById(coordinator.getId())).thenReturn(Optional.of(coordinator));

        MessageResponseDTO messageResponseDTO = coordinatorService.delete(coordinator.getId());

        assertEquals("Coordinator with id 1 was successfully deleted", messageResponseDTO.getMessage());
    }
}