package com.javatest.skilltrackerapi.model;

import com.javatest.skilltrackerapi.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddSkillRequest {
    private Long skillId;
    private Level level;

}

