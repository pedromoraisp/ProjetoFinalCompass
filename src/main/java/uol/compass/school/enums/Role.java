package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    RESPONSIBLE("RESPONSIBLE");

    private final String description;
}
