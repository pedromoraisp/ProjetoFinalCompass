package uol.compass.school.Utils;

import uol.compass.school.dto.request.StudentRequestDTO;
import uol.compass.school.dto.response.StudentDTO;
import uol.compass.school.entity.Student;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;

import java.time.LocalDate;
import java.util.Collections;

public class StudentUtils {

    public static Student createStudent() {
        return Student.builder()
                .id(1L)
                .name("Pedro Henrique")
                .gender(Gender.MALE)
                .cpf("43236854677")
                .identityCard("17333222")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddress())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .placeOfBirth("Bela Uberlândia")
                .school("Escola Municipal de Bela Uberlândia")
                .build();
    }
    public static Student createStudentWithOccurrences() {
        return Student.builder()
                .id(1L)
                .name("Pedro Henrique")
                .gender(Gender.MALE)
                .cpf("43236854677")
                .identityCard("17333222")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddress())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .placeOfBirth("Bela Uberlândia")
                .school("Escola Municipal de Bela Uberlândia")
                .occurrences(Collections.singletonList(OccurrenceUtils.createOccurrence()))
                .build();
    }

    public static StudentDTO createStudentDTO() {
        return StudentDTO.builder()
                .id(1L)
                .name("Pedro Henrique")
                .gender(Gender.MALE)
                .cpf("43236854677")
                .identityCard("17333222")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddressDTO())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .placeOfBirth("Bela Uberlândia")
                .school("Escola Municipal de Bela Uberlândia")
                .build();
    }

    public static StudentRequestDTO createStudentRequestDTO() {
        return StudentRequestDTO.builder()
                .name("Pedro Henrique")
                .gender(Gender.MALE)
                .cpf("43236854677")
                .identityCard("17333222")
                .birthdate(LocalDate.now())
                .address(AddressUtils.createAddressRequestDTO())
                .educationalLevel(EducationalLevel.INCOMPLETE_FUNDAMENTAL)
                .placeOfBirth("Bela Uberlândia")
                .school("Escola Municipal de Bela Uberlândia")
                .build();
    }
}
