package com.javatest.skilltrackerapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.javatest.skilltrackerapi.entity.Person;
import com.javatest.skilltrackerapi.exception.PersonNotFoundException;
import com.javatest.skilltrackerapi.repository.PersonRepository;
import com.javatest.skilltrackerapi.repository.SkillRepository;

public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private SkillRepository skillRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        personService = new PersonService(personRepository, skillRepository);
    }

    @Test
    public void testGetAllPeople() {
        List<Person> people = new ArrayList<>();
        people.add(new Person(1L, "Alan", "Lynch", new ArrayList<>()));
        people.add(new Person(2L, "Jane", "Lynch", new ArrayList<>()));

        Mockito.when(personRepository.findAll()).thenReturn(people);

        List<Person> result = personService.getAllPeople();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Alan", result.get(0).getFirstName());
        assertEquals("Lynch", result.get(0).getLastName());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Jane", result.get(1).getFirstName());
        assertEquals("Lynch", result.get(1).getLastName());
    }

    @Test
    public void testGetPersonById() {
        Long id = 1L;
        Person person = new Person(id, "Alan", "Lynch", new ArrayList<>());

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Person result = personService.getPersonById(id);

        assertEquals(id, result.getId());
        assertEquals("Alan", result.getFirstName());
        assertEquals("Lynch", result.getLastName());
    }

    @Test
    public void testGetPersonByIdWithInvalidId() {
        Long id = 1L;

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(id));
    }

    @Test
    public void testCreatePerson() {
        Person person = new Person(null, "Alan", "Lynch", null);
        Person savedPerson = new Person(1L, "Alan", "Lynch", null);

        Mockito.when(personRepository.save(person)).thenReturn(savedPerson);

        Person result = personService.createPerson(person);

        assertEquals(1L, result.getId());
        assertEquals("Alan", result.getFirstName());
        assertEquals("Lynch", result.getLastName());
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person(1L, "Alan", "Lynch", new ArrayList<>());
        Mockito.when(personRepository.findById(1L)).thenReturn(Optional.of(person));

        personService.deletePerson(1L);
        Mockito.verify(personRepository).delete(person);
    }

}