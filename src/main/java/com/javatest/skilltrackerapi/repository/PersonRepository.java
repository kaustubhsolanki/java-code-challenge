package com.javatest.skilltrackerapi.repository;

import com.javatest.skilltrackerapi.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}

