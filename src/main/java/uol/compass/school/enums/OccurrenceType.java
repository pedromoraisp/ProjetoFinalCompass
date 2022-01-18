package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OccurrenceType {

	PHYSICAL_AGGRESSION("PHYSICAL AGGRESSION"),
	VERBAL_AGGRESSION("VERBAL AGGRESSION"),
	DISOBEDIENCE("DISOBEDIENCE"),
	OTHERS("OTHERS");

	private String description;
}