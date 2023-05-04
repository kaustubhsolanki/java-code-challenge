package com.javatest.skilltrackerapi.controller;

import com.javatest.skilltrackerapi.entity.Skill;
import com.javatest.skilltrackerapi.services.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@AllArgsConstructor
public class SkillController {
    @Autowired
    SkillService skillService;

    @GetMapping
    public ResponseEntity<List<Skill>> getAllSkills() {
        List<Skill> skills = skillService.getAllSkills();
        return new ResponseEntity<>(skills, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long id) {
        Skill skill = skillService.getSkillById(id);
        return new ResponseEntity<>(skill, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Skill> createSkill(@RequestBody Skill skill) {
        Skill createdSkill = skillService.createSkill(skill);
        return new ResponseEntity<>(createdSkill, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long id, @RequestBody Skill skill) {
        Skill updatedSkill = skillService.updateSkill(id, skill);
        return new ResponseEntity<>(updatedSkill, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

