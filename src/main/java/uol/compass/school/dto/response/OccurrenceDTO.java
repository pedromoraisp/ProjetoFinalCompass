package uol.compass.school.dto.response;

import java.time.LocalDate;
@NoArgs...
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uol.compass.school.entity.Occurrence.OccurrenceBuilder;
import uol.compass.school.enums.CommunicationType;
import uol.compass.school.enums.OccurrenceType;

public class OccurrenceDTO {
	
	
	private Long id;
	
    private LocalDate date;
    
    private String description;
    
    private CommunicationType communicationType;
    
    private OccurrenceType occurrenceType;
    
    private StudentDTO student;

	}

	
	