package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelationshipType {
    FATHER("FATHER"),
    MOTHER("MOTHER"),
    LEGAL_RESPONSIBLE("LEGAL RESPONSIBLE");

    private String description;
}

