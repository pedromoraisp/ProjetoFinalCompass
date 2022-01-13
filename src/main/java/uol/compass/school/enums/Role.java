package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    EDUCATOR("EDUCATOR"),
    RESPONSIBLE("RESPONSIBLE");

    private final String description;
}
