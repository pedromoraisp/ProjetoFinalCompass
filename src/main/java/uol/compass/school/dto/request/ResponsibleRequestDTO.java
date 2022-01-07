package uol.compass.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;
import uol.compass.school.enums.RelationshipType;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsibleRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    @CPF
    private String cpf;

    @NotBlank
    private String identityCard;

    @NotNull
    private LocalDate birthdate;

    @NotNull
    @Valid
    private AddressRequestDTO address;

    @NotNull
    private EducationalLevel educationalLevel;

    @NotBlank
    private String phone;

    @NotNull
    private RelationshipType relationshipType;

    @NotBlank
    private String profession;


}
