package com.javatest.skilltrackerapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Range(min=0, max=2000,message = "Cannot add more than 2000 employees")
    private Long id;

    private String firstName;

    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "person_skillLevels",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<SkillLevels> skillLevels;

    public Person(String fName, String lName) {
        this.firstName = fName;
        this.lastName = lName;
    }
}
