package uol.compass.school.Utils;

import uol.compass.school.dto.request.EducatorRequestDTO;
import uol.compass.school.dto.response.EducatorDTO;
import uol.compass.school.entity.Educator;
import uol.compass.school.enums.Gender;

public class EducatorUtils {

    public static Educator createEducator() {
        return Educator.builder()
                .id(1L)
                .name("Mariana Moreira")
                .gender(Gender.FEMALE)
                .cpf("53236854690")
                .build();
    }
   
    public static EducatorDTO createEducatorDTO() {
        return EducatorDTO.builder()
                .id(1L)
                .name("Mariana Moreira")
                .gender(Gender.FEMALE)
                .cpf("53236854690")
                .build();
    }

    public static EducatorRequestDTO createEducatorRequestDTO() {
        return EducatorRequestDTO.builder()
        		.name("Mariana Moreira")
                .gender(Gender.FEMALE)
                .cpf("566.748.876-09")
                .build();
    }
}
