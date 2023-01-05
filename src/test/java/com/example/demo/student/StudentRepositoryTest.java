package com.example.demo.student;

import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepositoryUnderTest;
    @Test
    void itShouldSelectExistsEmail() {
        // given
        String email = "chahat.ck88@gmail.com";
        Student student = new Student(
                "ck",
                email,
                Gender.FEMALE
        );
        studentRepositoryUnderTest.save(student);

        // when
        Boolean exists = studentRepositoryUnderTest.selectExistsEmail(email);

        // then
        assertTrue(exists);
    }
}