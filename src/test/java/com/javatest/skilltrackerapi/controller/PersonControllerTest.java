package com.javatest.skilltrackerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatest.skilltrackerapi.entity.Person;
import com.javatest.skilltrackerapi.services.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonControllerTest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PersonService personService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new PersonController(personService)).build();
    }

    @Test
    public void testGetAllPeople() throws Exception {
        when(personService.getAllPeople()).thenReturn(Collections.singletonList(
                Person.builder().id(1L).firstName("Harry").lastName("Master").build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/people"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Harry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Master"));

        verify(personService).getAllPeople();
    }

    @Test
    public void testGetPersonById() throws Exception {
        when(personService.getPersonById(1L)).thenReturn(
                Person.builder().id(1L).firstName("Harry").lastName("Master").build());

        mockMvc.perform(MockMvcRequestBuilders.get("/people/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Harry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Master"));

        verify(personService).getPersonById(1L);
    }

    @Test
    public void testCreatePerson() throws Exception {
        Person person = Person.builder().id(1L).firstName("Harry").lastName("Master").build();
        when(personService.createPerson(any(Person.class))).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders.post("/people")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Harry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Master"));

        verify(personService).createPerson(any(Person.class));
    }

    @Test
    public void testUpdatePerson() throws Exception {
        Person person = Person.builder().id(1L).firstName("Harry").lastName("Master").build();
        when(personService.updatePerson(anyLong(), any(Person.class))).thenReturn(person);

        mockMvc.perform(MockMvcRequestBuilders.put("/people/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Harry"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Master"));

        verify(personService).updatePerson(eq(1L), any(Person.class));
    }

    @Test
    public void testDeletePerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/people/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(personService).deletePerson(1L);
    }

    @Test
    public void testRemoveSkillFromPerson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/people/1/skills/2"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(personService).removeSkillFromPerson(1L, 2L);
    }
}