package uol.compass.school.dto.request;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uol.compass.school.entity.Student;
import uol.compass.school.enums.CommunicationType;
import uol.compass.school.enums.OccurrenceType;

public class OccurrenceRequestDTO {
	
	    @NotNull
	    private LocalDate date;
	    
        @NotBlank
	    private String description;
        
        @NotNull
	    private CommunicationType communicationType;
        
        @NotNull
	    private OccurrenceType occurrenceType;
        
        @NotNull
	    private Student student;
}
