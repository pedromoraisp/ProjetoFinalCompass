package uol.compass.school.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import uol.compass.school.Utils.EducatorUtils;
import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.entity.Educator;
import uol.compass.school.repository.EducatorRepository;

@ExtendWith(MockitoExtension.class)
class EducatorServiceImplTest {

    @Mock
    private EducatorRepository educatorRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EducatorServiceImpl educatorService;

    @Test
    void whenEducatorIsInformedThenShouldBeCreated() {
    	EducatorRequestDTO educatorRequestDTO = EducatorUtils.createEducatorRequestDTO();
    	Educator expectedEducator = EducatorUtils.createEducator();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Educator Mariana Moreira with id 1 was successfully created")
                .build();

        when(modelMapper.map(educatorRequestDTO, Educator.class)).thenReturn(expectedEducator);
        when(educatorRepository.save(expectedEducator)).thenReturn(expectedEducator);

        MessageResponseDTO messageResponseDTO = educatorService.create(educatorRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenFindAllWithoutNameIsCalledThenReturnAllEducators() {
    	Educator expectedEducator = EducatorUtils.createEducator();
    	EducatorDTO expectedEducatorDTO = EducatorUtils.createEducatorDTO();
        List<EducatorDTO> expectedEducatorsDTO = Collections.singletonList(expectedEducatorDTO);

        when(educatorRepository.findAll()).thenReturn(Collections.singletonList(expectedEducator));
        when(modelMapper.map(expectedEducator, EducatorDTO.class)).thenReturn(expectedEducatorDTO);

        List<EducatorDTO> educatorsDTO = educatorService.findAll(null);

        assertEquals(educatorsDTO, expectedEducatorsDTO);
    }

    @Test
    void whenFindAllWithNameIsCalledThenReturnAllEducatorsWithThisName() {
        String name = "Mariana";

        Educator expectedEducator = EducatorUtils.createEducator();
        EducatorDTO expectedEducatorDTO = EducatorUtils.createEducatorDTO();
        List<EducatorDTO> expectedEducatorsDTO = Collections.singletonList(expectedEducatorDTO);

        when(educatorRepository.findByNameStartingWith(name)).thenReturn(Collections.singletonList(expectedEducator));
        when(modelMapper.map(expectedEducator, EducatorDTO.class)).thenReturn(expectedEducatorDTO);

        List<EducatorDTO> educatorsDTO = educatorService.findAll(name);

        assertEquals(educatorsDTO, expectedEducatorsDTO);
    }

    @Test
    void whenFindAllWithAnNonexistentNameIsCalledThenReturnAnEmptyList() {
        String name = "Mariana";

        when(educatorRepository.findByNameStartingWith(name)).thenReturn(Collections.EMPTY_LIST);

        List<EducatorDTO> educatorsDTO = educatorService.findAll(name);

        assertEquals(educatorsDTO.size(), 0);
    }

    @Test
    void whenGivenAnIdThenShouldReturnAEducatorWithThisId() {
        Long id = 1L;
        Educator expectedEducator = EducatorUtils.createEducator();
        EducatorDTO expectedEducatorDTO = EducatorUtils.createEducatorDTO();

        when(educatorRepository.findById(id)).thenReturn(Optional.of(expectedEducator));
        when(modelMapper.map(expectedEducator, EducatorDTO.class)).thenReturn(expectedEducatorDTO);

        EducatorDTO educatorDTO = educatorService.findById(id);

        assertEquals(expectedEducatorDTO, educatorDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToFindAEducatorThenShouldReturnResponseStatusException() {
        Long id = 1L;

        when(educatorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> educatorService.findById(id));
    }

    @Test
    void whenGivenAnIdThenShouldUpdateAEducator() {
        Long id = 1L;
        Educator expectedEducator = EducatorUtils.createEducator();
        EducatorRequestDTO educatorRequestDTO = EducatorUtils.createEducatorRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Educator with id 1 was successfully updated")
                .build();

        when(educatorRepository.findById(id)).thenReturn(Optional.of(expectedEducator));
        when(modelMapper.map(educatorRequestDTO, Educator.class)).thenReturn(expectedEducator);

        MessageResponseDTO messageResponseDTO = educatorService.update(id, educatorRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToUpdateAEducatorThenShouldReturnResponseStatusException() {
        Long id = 1L;
        EducatorRequestDTO educatorRequestDTO = EducatorUtils.createEducatorRequestDTO();

        when(educatorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> educatorService.update(id, educatorRequestDTO));
    }

    @Test
    void whenGivenAnIdThenShouldDeleteAEducator() {
        Long id = 1L;
        Educator expectedEducator = EducatorUtils.createEducator();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Educator with id 1 was successfully deleted")
                .build();

        when(educatorRepository.findById(id)).thenReturn(Optional.of(expectedEducator));

        MessageResponseDTO messageResponseDTO = educatorService.deleteById(id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToDeleteAEducatorThenShouldReturnResponseStatusException() {
        Long id = 1L;

        when(educatorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> educatorService.deleteById(id));
    }

}