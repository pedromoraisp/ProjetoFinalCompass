package uol.compass.school.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;
import uol.compass.school.Utils.ResponsibleUtils;
import uol.compass.school.dto.request.ResponsibleRequestDTO;
import uol.compass.school.dto.response.MessageResponseDTO;
import uol.compass.school.dto.response.ResponsibleDTO;
import uol.compass.school.entity.Responsible;
import uol.compass.school.repository.ResponsibleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResponsibleServiceImplTest {

    @Mock
    private ResponsibleRepository responsibleRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResponsibleServiceImpl responsibleService;

    @Test
    void whenResponsibleIsInformedThenShouldBeCreated() {
        ResponsibleRequestDTO responsibleRequestDTO = ResponsibleUtils.createResponsibleRequestDTO();
        Responsible expectedResponsible = ResponsibleUtils.createResponsible();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Responsible Edivan Alves with id 1 was successfully created")
                .build();

        when(modelMapper.map(responsibleRequestDTO, Responsible.class)).thenReturn(expectedResponsible);
        when(responsibleRepository.save(expectedResponsible)).thenReturn(expectedResponsible);

        MessageResponseDTO messageResponseDTO = responsibleService.create(responsibleRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenFindAllWithoutNameIsCalledThenReturnListStudents() {
        Responsible expectedResponsible = ResponsibleUtils.createResponsible();
        ResponsibleDTO expectedResponsibleDTO = ResponsibleUtils.createResponsibleDTO();
        List<ResponsibleDTO> expectedResponsibleListDTO = Collections.singletonList(expectedResponsibleDTO);

        when(modelMapper.map(expectedResponsible, ResponsibleDTO.class)).thenReturn(expectedResponsibleDTO);
        when(responsibleRepository.findAll()).thenReturn(Collections.singletonList(expectedResponsible));

        List<ResponsibleDTO> responsibleListDTO = responsibleService.findAll(null);

        assertEquals(responsibleListDTO, expectedResponsibleListDTO);
    }

    @Test
    void FindsEveryoneWithTheNamedAndReturnsEveryoneResponsibleWithThatName() {
        String name = "Ediv";

        Responsible expectedResponsible = ResponsibleUtils.createResponsible();
        ResponsibleDTO expectedResponsibleDTO = ResponsibleUtils.createResponsibleDTO();
        List<ResponsibleDTO> expectedResponsibleListDTO = Collections.singletonList(expectedResponsibleDTO);

        when(modelMapper.map(expectedResponsible, ResponsibleDTO.class)).thenReturn(expectedResponsibleDTO);
        when(responsibleRepository.findByNameStartingWith(name)).thenReturn(Collections.singletonList(expectedResponsible));

        List<ResponsibleDTO> responsibleListDTO = responsibleService.findAll(name);

        assertEquals(responsibleListDTO, expectedResponsibleListDTO);
    }

    @Test
    void whenFindAllWithAnNonexistentNameIsCalledThenReturnAnEmptyList() {
        String name = "Edivan";

        when(responsibleRepository.findByNameStartingWith(name)).thenReturn(Collections.EMPTY_LIST);

        List<ResponsibleDTO> responsibleListDTO = responsibleService.findAll(name);

        assertEquals(responsibleListDTO.size(), 0);
    }

    @Test
    void whenGivenAnIdThenShouldReturnResponsibleWithThisId() {
        Long id = 1L;
        Responsible expectedResponsible = ResponsibleUtils.createResponsible();
        ResponsibleDTO expectedResponsibleDTO = ResponsibleUtils.createResponsibleDTO();

        when(responsibleRepository.findById(id)).thenReturn(Optional.of(expectedResponsible));
        when(modelMapper.map(expectedResponsible, ResponsibleDTO.class)).thenReturn(expectedResponsibleDTO);

        ResponsibleDTO responsibleDTO = responsibleService.findById(id);

        assertEquals(expectedResponsibleDTO, responsibleDTO);
    }

    @Test
    void whenIdDoesNotExistForParentReturnResponseStatusException() {
        Long id = 1L;

        when(responsibleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> responsibleService.findById(id));
    }

    @Test
    void whenGivenIdThenShouldUpdateResponsible() {
        Long id = 1L;
        Responsible expectedResponsible = ResponsibleUtils.createResponsible();
        ResponsibleRequestDTO responsibleRequestDTO = ResponsibleUtils.createResponsibleRequestDTO();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Responsible with id 1 was successfully updated")
                .build();

        when(responsibleRepository.findById(id)).thenReturn(Optional.of(expectedResponsible));
        when(modelMapper.map(responsibleRequestDTO, Responsible.class)).thenReturn(expectedResponsible);

        MessageResponseDTO messageResponseDTO = responsibleService.update(id, responsibleRequestDTO);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenNonexistentIdToUpdateResponsibleReturnResponseStatusException() {
        Long id = 1L;
        ResponsibleRequestDTO responsibleRequestDTO = ResponsibleUtils.createResponsibleRequestDTO();

        when(responsibleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> responsibleService.update(id, responsibleRequestDTO));
    }

    @Test
    void whenGivenIdExistentDeleteResponsible() {
        Long id = 1L;
        Responsible expectedResponsible = ResponsibleUtils.createResponsible();
        MessageResponseDTO expectedMessageResponse = MessageResponseDTO.builder()
                .message("Responsible with id 1 was successfully deleted")
                .build();

        when(responsibleRepository.findById(id)).thenReturn(Optional.of(expectedResponsible));

        MessageResponseDTO messageResponseDTO = responsibleService.deleteById(id);

        assertEquals(expectedMessageResponse, messageResponseDTO);
    }

    @Test
    void whenGivenNoExistentIdToDeleteResponsibleReturnResponseStatusException() {
        Long id = 1L;

        when(responsibleRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> responsibleService.deleteById(id));
    }

//    @Test
//    void FindAllFromResponsibleAndThenReturnAllStudents() {
//        Long id = 1L;
//        Responsible expectedResponsible = ResponsibleUtils.createResponsibleWithStudents();
//        StudentDTO studentDTO = StudentUtils.createStudentDTO();
//
//        when(responsibleRepository.findById(id)).thenReturn(Optional.of(expectedResponsible));
//        when(modelMapper.map(expectedResponsible.getStudents().get(0), OccurrenceDTO.class))
//                .thenReturn(studentDTO);
//
//        Set<StudentDTO> allStudents = responsibleService.findAllStudents(id);
//
//        assertEquals(Collections.singleton(studentDTO), allStudents);;;
//    }


}