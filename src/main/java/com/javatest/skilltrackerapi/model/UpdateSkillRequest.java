package com.javatest.skilltrackerapi.model;

import com.javatest.skilltrackerapi.enums.Level;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSkillRequest {
    private Level level;
}
