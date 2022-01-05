package uol.compass.school.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.EducationalLevel;
import uol.compass.school.enums.Gender;
import uol.compass.school.enums.RelationshipType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Responsible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false)
    private RelationshipType relationshipType;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private String identityCard;

    @Column(nullable = false)
    private LocalDate birthdate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EducationalLevel educationalLevel;

    @Column(nullable = false)
    private String profession;

    @Column
    private String email;

    @Column(nullable = false)
    private String phone;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "responsible_student",
            joinColumns = @JoinColumn(name = "responsible_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;
}
