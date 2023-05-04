package com.javatest.skilltrackerapi.services;

import com.javatest.skilltrackerapi.entity.Skill;
import com.javatest.skilltrackerapi.exception.SkillNotFoundException;
import com.javatest.skilltrackerapi.repository.SkillRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class SkillServiceTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillService skillService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllSkills() {
        // Arrange
        Skill skill1 = new Skill(1L,"Java");
        Skill skill2 = new Skill(2L,"Python");
        List<Skill> skills = Arrays.asList(skill1, skill2);
        when(skillRepository.findAll()).thenReturn(skills);

        // Act
        List<Skill> result = skillService.getAllSkills();

        // Assert
        assertEquals(skills.size(), result.size());
        assertTrue(result.contains(skill1));
        assertTrue(result.contains(skill2));
    }

    @Test
    public void testGetSkillById() {
        // Arrange
        Skill skill = new Skill(1L,"Java");
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));

        // Act
        Skill result = skillService.getSkillById(1L);

        // Assert
        assertEquals(skill, result);
    }

    @Test
    public void testGetSkillByIdNotFound() {
        // Arrange
        when(skillRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and assert
        assertThrows(SkillNotFoundException.class, () -> skillService.getSkillById(1L));
    }

    @Test
    public void testCreateSkill() {
        // Arrange
        Skill skill = new Skill(1L,"Java");
        when(skillRepository.save(skill)).thenReturn(skill);

        // Act
        Skill result = skillService.createSkill(skill);

        // Assert
        assertEquals(skill, result);
    }

    @Test
    public void testUpdateSkill() {
        // Arrange
        Skill skill = new Skill(1L,"Java");
        Skill updatedSkill = new Skill(2L,"Python");
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));
        when(skillRepository.save(skill)).thenReturn(updatedSkill);

        // Act
        Skill result = skillService.updateSkill(1L, updatedSkill);

        // Assert
        assertEquals(updatedSkill, result);
    }

    @Test
    public void testUpdateSkillNotFound() {
        // Arrange
        Skill updatedSkill = new Skill(1L,"Python");
        when(skillRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and assert
        assertThrows(SkillNotFoundException.class, () -> skillService.updateSkill(1L, updatedSkill));
    }

    @Test
    public void testDeleteSkill() {
        // Arrange
        Skill skill = new Skill(1L,"Java");
        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));

        // Act
        skillService.deleteSkill(1L);

        // Assert
        verify(skillRepository, times(1)).delete(skill);
    }

    @Test
    public void testDeleteSkillNotFound() {
        // Arrange
        when(skillRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and assert
        assertThrows(SkillNotFoundException.class, () -> skillService.deleteSkill(1L));
    }
}
