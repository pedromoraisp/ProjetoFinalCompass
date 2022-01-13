package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RelationshipType {
    FATHER("Father"),
    MOTHER("Mother"),
    LEGAL_RESPONSIBLE("Legal Responsible");

    private String description;
}

