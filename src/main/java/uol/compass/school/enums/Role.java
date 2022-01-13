package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("Admin"),
    EDUCATOR("Educator"),
    RESPONSIBLE("Responsible");

    private final String description;
}
