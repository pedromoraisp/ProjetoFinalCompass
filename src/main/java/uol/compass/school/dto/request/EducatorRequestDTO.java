package uol.compass.school.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.Gender;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducatorRequestDTO {

	private Long Id;
	
    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    @CPF
    private String cpf;


}
