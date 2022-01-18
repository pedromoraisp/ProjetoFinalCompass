package uol.compass.school.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uol.compass.school.enums.Gender;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Educator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
}
