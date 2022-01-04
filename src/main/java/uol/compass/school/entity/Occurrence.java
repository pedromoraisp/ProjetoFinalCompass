package uol.compass.school.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.CommunicationType;
import uol.compass.school.enums.OccurrenceType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Occurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommunicationType communicationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OccurrenceType occurrenceType;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
