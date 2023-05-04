package com.javatest.skilltrackerapi.repository;

import com.javatest.skilltrackerapi.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
}
