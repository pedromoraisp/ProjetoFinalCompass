package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;
import uol.compass.school.enums.RelationshipType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponsibleDTO {

    private Long id;

    private String name;

    private Gender gender;

    private String cpf;

    private String identityCard;

    private String phone;

    private RelationshipType relationshipType;

    private LocalDate birthdate;

    private AddressDTO address;

    private EducationalLevel educationalLevel;

    private String profession;

    }
