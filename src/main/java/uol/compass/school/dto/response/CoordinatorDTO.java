package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinatorDTO {

    private Long id;

    private String name;

    private String cpf;

    private Gender gender;
}
