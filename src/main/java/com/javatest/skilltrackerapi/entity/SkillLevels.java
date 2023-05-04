package com.javatest.skilltrackerapi.entity;

import com.javatest.skilltrackerapi.enums.Level;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "skill_levels")
public class SkillLevels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Range(min=0, max=100,message = "Cannot add more than 100 skills")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

}