package com.javatest.skilltrackerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public
class SkillNotFoundException extends RuntimeException {
    public SkillNotFoundException(Long id) {
        super("Could not find skill " + id);
    }
}
