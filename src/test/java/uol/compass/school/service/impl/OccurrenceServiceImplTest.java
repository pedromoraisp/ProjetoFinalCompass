package uol.compass.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.Utils.OccurrenceUtils;
import uol.compass.school.dto.request.OccurrenceRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.OccurrenceDTO;
import uol.compass.school.entity.Occurrence;
import uol.compass.school.repository.OccurrenceRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OccurrenceServiceImplTest {

    @Mock
    private OccurrenceRepository occurrenceRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private OccurrenceServiceImpl occurrenceService;

    @Test
    void whenOccurrenceIsInformedThenShouldBeCreated() {
    	OccurrenceRequestDTO occurrenceRequestDTO = OccurrenceUtils.createOccurrenceRequestDTO();
    	Occurrence expectedOccurrence = OccurrenceUtils.createOccurrence();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Occurrence with id 1 was successfully created")
                .build();

        when(modelMapper.map(occurrenceRequestDTO, Occurrence.class)).thenReturn(expectedOccurrence);
        when(occurrenceRepository.save(expectedOccurrence)).thenReturn(expectedOccurrence);

        MessageResponseDTO messageResponseDTO = occurrenceService.create(occurrenceRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenFindAllWithoutNameIsCalledThenReturnAllOccurrences() {
    	Occurrence expectedOccurrence = OccurrenceUtils.createOccurrence();
    	OccurrenceDTO expectedOccurrenceDTO = OccurrenceUtils.createOccurrenceDTO();
        List<OccurrenceDTO> expectedStudentsDTO = Collections.singletonList(expectedOccurrenceDTO);

        when(modelMapper.map(expectedOccurrence, OccurrenceDTO.class)).thenReturn(expectedOccurrenceDTO);
        when(occurrenceRepository.findAll()).thenReturn(Collections.singletonList(expectedOccurrence));

        List<OccurrenceDTO> occurrencesDTO = occurrenceService.findAll(null);

        assertEquals(occurrencesDTO, expectedOccurrencesDTO);
    }

    @Test
    void whenFindAllWithNameIsCalledThenReturnAllOccurrencesWithThisName() {
        String name = "Pedro";

        Occurrence expectedOccurrence = OccurrenceUtils.createOccurrence();
        OccurrenceDTO expectedOccurrenceDTO = OccurrenceUtils.createOccurrenceDTO();
        List<OccurrenceDTO> expectedOccurrencesDTO = Collections.singletonList(expectedOccurrenceDTO);

        when(modelMapper.map(expectedOccurrence, OccurrenceDTO.class)).thenReturn(expectedOccurrenceDTO);
        when(occurrenceRepository.findByNameStartingWith(id)).thenReturn(Collections.singletonList(expectedOccurrence));

        List<OccurrenceDTO> studentsDTO = occurrenceService.findAll(id);

        assertEquals(occurrencesDTO, expectedOccurrencesDTO);
    }

    @Test
    void whenFindAllWithAnNonexistentNameIsCalledThenReturnAnEmptyList() {
        String name = "Pedro";

        when(occurrenceRepository.findByNameStartingWith(name)).thenReturn(Collections.EMPTY_LIST);

        List<OccurrenceDTO> studentsDTO = occurrenceService.findAll(name);

        assertEquals(occurrencesDTO.size(), 0);
    }

    @Test
    void whenGivenAnIdThenShouldReturnAOccurrenceWithThisId() {
        Long id = 1L;
        Occurrence expectedOccurrence = OccurrenceUtils.createOccurrence();
        OccurrenceDTO expectedOccurrenceDTO = OccurrenceUtils.createOccurrenceDTO();

        when(occurrencetRepository.findById(id)).thenReturn(Optional.of(expectedOccurrence));
        when(modelMapper.map(expectedOccurrence, OccurrenceDTO.class)).thenReturn(expectedOccurrenceDTO);

        OccurrenceDTO occurrenceDTO = occurrenceService.findById(id);

        assertEquals(expectedOccurrenceDTO, occurrenceDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToFindAOccurrenceThenShouldReturnResponseStatusException() {
        Long id = 1L;

        when(occurrenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> occurrenceService.findById(id));
    }

    @Test
    void whenGivenAnIdThenShouldUpdateAOccurrence() {
        Long id = 1L;
        Occurrence expectedOccurrence = OccurrenceUtils.createOccurrence();
        OccurrenceRequestDTO occurrenceRequestDTO = OccurrenceUtils.createOccurrenceRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Occurrence with id 1 was successfully updated")
                .build();

        when(occurrenceRepository.findById(id)).thenReturn(Optional.of(expectedOccurrence));
        when(modelMapper.map(occurrenceRequestDTO, Occurrence.class)).thenReturn(expectedOccurrence);

        MessageResponseDTO messageResponseDTO = occurrenceService.update(id, occurrenceRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToUpdateAOccurrenceThenShouldReturnResponseStatusException() {
        Long id = 1L;
        OccurrenceRequestDTO occurrenceRequestDTO = OccurrenceUtils.createOccurrenceRequestDTO();

        when(occurrenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> occurrenceService.update(id, occurrenceRequestDTO));
    }

    @Test
    void whenGivenAnIdThenShouldDeleteAOccurrence() {
        Long id = 1L;
        Occurrence expectedOccurrence = OccurrenceUtils.createOccurrence();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Occurrence with id 1 was successfully deleted")
                .build();

        when(occurrenceRepository.findById(id)).thenReturn(Optional.of(expectedOccurrence));

        MessageResponseDTO messageResponseDTO = occurrenceService.deleteById(id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenAnNonexistentIdToDeleteAOccurrenceThenShouldReturnResponseStatusException() {
        Long id = 1L;

        when(occurrenceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> occurrenceService.deleteById(id));
    }
}