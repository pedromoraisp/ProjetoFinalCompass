package uol.compass.school.entity;

import lombok.*;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column
    private String cpf;

    @Column
    private String identityCard;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationalLevel educationalLevel;

    @Column(nullable = false)
    private String placeOfBirth;

    @Column(nullable = false)
    private String school;

    @ManyToMany(mappedBy = "students")
    private Set<Responsible> responsible;

    @ManyToMany(mappedBy = "students")
    private Set<Classroom> classrooms = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private List<Occurrence> occurrences;
}
