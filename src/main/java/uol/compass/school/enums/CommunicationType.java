package uol.compass.school.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommunicationType {

	VERBAL("VERBAL"),
	WRITTEN("WRITTEN"),
	TO_PARENTS("TO PARENTS");

	private String description;
}