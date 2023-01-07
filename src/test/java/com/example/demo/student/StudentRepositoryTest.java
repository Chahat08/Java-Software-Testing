package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepositoryUnderTest;

    @AfterEach
    void tearDown() {
        studentRepositoryUnderTest.deleteAll();
    }

    @Test
    void checkEmailExists() {
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

    @Test
    void checkEmailDoesNotExist() {
        // given
        String email = "chahat.ck88@gmail.com";

        // when
        Boolean exists = studentRepositoryUnderTest.selectExistsEmail(email);

        // then
        assertFalse(exists);
    }
}