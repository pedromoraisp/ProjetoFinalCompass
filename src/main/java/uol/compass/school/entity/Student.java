package uol.compass.school.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Gender gender;

    @Column
    private String cpf;

    @Column
    private String identityCard;

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationalLevel educationalLevel;

    @Column(nullable = false)
    private String placeOfBirth;

    @Column(nullable = false)
    private String school;

    @ManyToMany(mappedBy = "students")
    private Set<Responsible> responsible;

    @ManyToOne(fetch = FetchType.LAZY)
    private Classroom classroom;

    @OneToMany(mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Occurrence> occurrences;
}
