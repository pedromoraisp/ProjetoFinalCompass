package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EducationalLevel {
    INCOMPLETE_FUNDAMENTAL("Incomplete Fundamental"),
    COMPLETE_FUNDAMENTAL("Complete Fundamental"),
    INCOMPLETE_HIGH_SCHOOL("Incomplete High School"),
    COMPLETE_HIGH_SCHOOL("Complete High School"),
    INCOMPLETE_UNIVERSITY_EDUCATION("Incomplete University Education"),
    COMPLETE_UNIVERSITY_EDUCATION("Complete University Education");

    private String description;
}
