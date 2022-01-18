package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {

    MALE("MALE"),
    FEMALE("FEMALE");

    private String description;
}