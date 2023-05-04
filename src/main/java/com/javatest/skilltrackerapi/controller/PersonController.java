package com.javatest.skilltrackerapi.controller;

import com.javatest.skilltrackerapi.entity.Person;
import com.javatest.skilltrackerapi.model.AddSkillRequest;
import com.javatest.skilltrackerapi.model.UpdateSkillRequest;
import com.javatest.skilltrackerapi.services.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    @Autowired
    PersonService personService;




    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        return personService.updatePerson(id, personDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @PostMapping("/{personId}/skills")
    public void addSkillToPerson(@PathVariable Long personId, @RequestBody AddSkillRequest request) {
        personService.addSkillToPerson(personId, request.getSkillId(), request.getLevel());
    }

    @PutMapping("/{personId}/skills/{skillId}")
    public void updateSkillForPerson(@PathVariable Long personId, @PathVariable Long skillId,
                                     @RequestBody UpdateSkillRequest request) {
        personService.updateSkillForPerson(personId, skillId, request.getLevel());
    }

    @DeleteMapping("/{personId}/skills/{skillId}")
    public void removeSkillFromPerson(@PathVariable Long personId, @PathVariable Long skillId) {
        personService.removeSkillFromPerson(personId, skillId);
    }
}

