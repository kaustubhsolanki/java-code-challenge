package com.javatest.skilltrackerapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatest.skilltrackerapi.entity.Skill;
import com.javatest.skilltrackerapi.services.SkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SkillControllerTest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private SkillService skillService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new SkillController(skillService)).build();
    }

    @Test
    public void testGetAllSkills() throws Exception {
        when(skillService.getAllSkills()).thenReturn(Collections.singletonList(
                Skill.builder().id(1L).name("Java").build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/skills"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Java"));

        verify(skillService).getAllSkills();
    }

    @Test
    public void testGetSkillById() throws Exception {
        when(skillService.getSkillById(1L)).thenReturn(
                Skill.builder().id(1L).name("Java").build());

        mockMvc.perform(MockMvcRequestBuilders.get("/skills/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Java"));

        verify(skillService).getSkillById(1L);
    }

    @Test
    public void testCreateSkill() throws Exception {
        Skill skill = Skill.builder().id(1L).name("Java").build();
        when(skillService.createSkill(any(Skill.class))).thenReturn(skill);

        mockMvc.perform(MockMvcRequestBuilders.post("/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skill)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Java"));

        verify(skillService).createSkill(any(Skill.class));
    }

    @Test
    public void testDeleteSkill() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/skills/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(skillService).deleteSkill(1L);
    }
}
