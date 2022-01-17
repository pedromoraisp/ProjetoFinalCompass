package uol.compass.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import uol.compass.school.enums.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoordinatorRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    @CPF
    private String cpf;

    @NotNull
    private Gender gender;
}
