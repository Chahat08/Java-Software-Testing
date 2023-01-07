package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    // student repo has been tested, we don't need to autowire it (get a real instance of it), instead we will mock it
    @Mock
    private StudentRepository studentRepository;
    private AutoCloseable autoCloseable;
    private StudentService studentServiceUnderTest;

    @BeforeEach
    void setUp(){
        // start up all the mocks in this test class
        autoCloseable = MockitoAnnotations.openMocks(this);
        // get a fresh instance for student service before each test
        studentServiceUnderTest = new StudentService(studentRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllStudents() {
        // when
        studentServiceUnderTest.getAllStudents();
        // then
        Mockito.verify(studentRepository).findAll();
        /*
        we don't want to test the real student repository when testing the student service.
        because it has already been tested.
        we simply just verify that the method in it has been called, using the mocked repository.
         */
    }

    @Test
    void addStudent() {

    }

    @Test
    void deleteStudent() {
    }
}