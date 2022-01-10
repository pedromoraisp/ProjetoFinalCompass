package uol.compass.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import uol.compass.school.entity.Course;
import uol.compass.school.enums.Gender;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @NotNull
    private Course course;

}
