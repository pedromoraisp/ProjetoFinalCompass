package uol.compass.school.Utils;

import uol.compass.school.dto.request.ResponsibleRequestDTO;
import uol.compass.school.dto.response.ResponsibleDTO;
import uol.compass.school.entity.Responsible;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;
import uol.compass.school.enums.RelationshipType;

import java.time.LocalDate;
import java.util.Collections;

public class ResponsibleUtils {

    public static Responsible createResponsible() {
        return Responsible.builder()
                .id(1L)
                .name("Edivan Alves")
                .gender(Gender.MALE)
                .cpf("240001000899")
                .identityCard("1234567")
                .phone("03490000000")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddress())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .relationshipType(RelationshipType.FATHER)
                .profession("autonomo")
                .build();
    }

    public static ResponsibleDTO createResponsibleDTO(){
        return ResponsibleDTO.builder()
                .id(1L)
                .name("Edivan Alves")
                .gender(Gender.MALE)
                .cpf("240001000899")
                .identityCard("1234567")
                .phone("03490000000")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddressDTO())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .relationshipType(RelationshipType.FATHER)
                .profession("autonomo")
                .build();
    }

    public static ResponsibleRequestDTO createResponsibleRequestDTO() {
        return ResponsibleRequestDTO.builder()
                .name("Edivan Alves")
                .gender(Gender.MALE)
                .cpf("240001000899")
                .identityCard("1234567")
                .phone("03490000000")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddressRequestDTO())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .relationshipType(RelationshipType.FATHER)
                .profession("autonomo")
                .build();
    }

    public static Responsible createResponsibleWithStudents() {
        return Responsible.builder()
                .id(1L)
                .name("Pedro Henrique")
                .gender(Gender.MALE)
                .cpf("43236854677")
                .identityCard("17333222")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddress())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .students(Collections.singleton(StudentUtils.createStudent()))
                .build();
    }
}
