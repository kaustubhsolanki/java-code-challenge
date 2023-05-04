package com.javatest.skilltrackerapi.repository;

import com.javatest.skilltrackerapi.entity.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSaveAndRetrievePerson() {
        // create a person
        Person person = Person.builder().firstName("Issac").lastName("Parle").build();

        // save the person
        personRepository.save(person);

        // retrieve the person by id
        Person retrievedPerson = personRepository.findById(person.getId()).orElse(null);

        // verify the retrieved person is not null
        Assertions.assertNotNull(retrievedPerson);

        // verify the retrieved person matches the saved person
        Assertions.assertEquals(person.getFirstName(), retrievedPerson.getFirstName());
        Assertions.assertEquals(person.getLastName(), retrievedPerson.getLastName());
    }

    @Test
    public void testRetrieveAllPeople() {
        // create some people
        Person person1 = Person.builder().firstName("Issac").lastName("Parle").build();
        Person person2 = Person.builder().firstName("Jane").lastName("Parle").build();
        personRepository.saveAll(List.of(person1, person2));

        // retrieve all people
        List<Person> people = personRepository.findAll();

        // verify the number of people retrieved is correct
        Assertions.assertEquals(2, people.size());

        // verify the retrieved people match the saved people
        Assertions.assertEquals(person1.getFirstName(), people.get(0).getFirstName());
        Assertions.assertEquals(person1.getLastName(), people.get(0).getLastName());
        Assertions.assertEquals(person2.getFirstName(), people.get(1).getFirstName());
        Assertions.assertEquals(person2.getLastName(), people.get(1).getLastName());
    }

}
