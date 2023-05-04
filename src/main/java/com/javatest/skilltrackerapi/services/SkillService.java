package com.javatest.skilltrackerapi.services;

import com.javatest.skilltrackerapi.entity.Skill;
import com.javatest.skilltrackerapi.exception.SkillNotFoundException;
import com.javatest.skilltrackerapi.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SkillService {

    @Autowired
    SkillRepository skillRepository;

    public List<Skill> getAllSkills(){
        return skillRepository.findAll();
    }

    public Skill getSkillById(Long id){
        return skillRepository.findById(id).orElseThrow(() -> new SkillNotFoundException(id));
    }

    public Skill createSkill(Skill skill){
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill skillDetails) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));
        skill.setName(skillDetails.getName());
        return skillRepository.save(skill);
    }

    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(id));
        skillRepository.delete(skill);
    }
}
