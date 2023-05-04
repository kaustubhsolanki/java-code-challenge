package com.javatest.skilltrackerapi.services;

import com.javatest.skilltrackerapi.entity.Person;
import com.javatest.skilltrackerapi.entity.Skill;
import com.javatest.skilltrackerapi.entity.SkillLevels;
import com.javatest.skilltrackerapi.enums.Level;
import com.javatest.skilltrackerapi.exception.InvalidRequestException;
import com.javatest.skilltrackerapi.exception.PersonNotFoundException;
import com.javatest.skilltrackerapi.exception.SkillNotFoundException;
import com.javatest.skilltrackerapi.repository.PersonRepository;
import com.javatest.skilltrackerapi.repository.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;
    private final SkillRepository skillRepository;

    public PersonService(PersonRepository personRepository, SkillRepository skillRepository) {
        this.personRepository = personRepository;
        this.skillRepository = skillRepository;
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public Person createPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Long id, Person personDetails) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        return personRepository.save(person);
    }

    public void deletePerson(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        personRepository.delete(person);
    }

    public void addSkillToPerson(Long personId, Long skillId, Level level) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new SkillNotFoundException(skillId));

        // Check if person already has the skill
        if (person.getSkillLevels().stream().anyMatch(s -> s.getSkill().getId().equals(skill.getId()))) {
            throw new InvalidRequestException("Person already has the skill");
        }
        SkillLevels skillLevels = new SkillLevels();
        skillLevels.setSkill(skill);
        skillLevels.setLevel(level);
        person.getSkillLevels().add(skillLevels);
        personRepository.save(person);
    }

    public void updateSkillForPerson(Long personId, Long skillId, Level level) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));

        // Find the skill proficiency for the specified skill and update its level
        Optional<SkillLevels> skillProficiency = person.getSkillLevels().stream()
                .filter(sp -> sp.getSkill().getId().equals(skillId))
                .findFirst();
        if (skillProficiency.isPresent()) {
            skillProficiency.get().setLevel(level);
            personRepository.save(person);
        } else {
            throw new InvalidRequestException("Skill proficiency not found");
        }
    }

    public void removeSkillFromPerson(Long personId, Long skillId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException( personId));

        // Remove the skill proficiency for the specified skill
        person.getSkillLevels().removeIf(sp -> sp.getSkill().getId().equals(skillId));
        personRepository.save(person);
    }
}




