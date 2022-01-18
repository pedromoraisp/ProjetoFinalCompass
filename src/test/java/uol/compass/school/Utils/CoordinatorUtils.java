package uol.compass.school.Utils;

import uol.compass.school.dto.request.CoordinatorRequestDTO;
import uol.compass.school.dto.response.CoordinatorDTO;
import uol.compass.school.entity.Coordinator;
import uol.compass.school.enums.Gender;

public class CoordinatorUtils {

    public static Coordinator createCoordinator() {
        return Coordinator.builder()
                .id(1L)
                .name("Maria das Graças")
                .cpf("30696552620")
                .gender(Gender.FEMALE)
                .build();
    }

    public static CoordinatorDTO createCoordinatorDTO() {
        return CoordinatorDTO.builder()
                .id(1L)
                .name("Maria das Graças")
                .cpf("30696552620")
                .gender(Gender.FEMALE)
                .build();
    }

    public static CoordinatorRequestDTO createCoordinatorRequestDTO() {
        return CoordinatorRequestDTO.builder()
                .name("Maria das Graças")
                .cpf("30696552620")
                .gender(Gender.FEMALE)
                .build();
    }
}