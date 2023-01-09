package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    // student repo has been tested, we don't need to autowire it (get a real instance of it), instead we will mock it
    @Mock
    private StudentRepository studentRepository;
    private StudentService studentServiceUnderTest;

    @BeforeEach
    void setUp(){
        // mockito extension can handle the aftereach method to close the mocks

        // get a fresh instance for student service before each test
        studentServiceUnderTest = new StudentService(studentRepository);
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
    void CanAddStudent() {
        // given
        Student student = new Student("ck",
                "chahat.ck88@gmail.com",
                Gender.FEMALE);

        // when
        studentServiceUnderTest.addStudent(student);

        // then
        // checking if student repository was invoked with the same student that we created
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        Mockito.verify(studentRepository).save(studentArgumentCaptor.capture());
        // above: verify that studentrepository.save was called. capture the arguemtn that it was called with.

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowExceptionWhenEmailIsTaken(){
        // given

        Student student = new Student(
                "ck",
                "chahat.ck88@gmail.com",
                Gender.FEMALE
        );

        // we need to mock the behaviour of the student repository selectExistsEmail to return true
        // so that the exception is actually thrown
        given(studentRepository.selectExistsEmail(anyString())).willReturn(true);
        // can pass student.getEmail() instead of actually passing the email

        // when
        // then
        assertThatThrownBy(()->studentServiceUnderTest.addStudent(student))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email "+student.getEmail()+" taken");


        // checking that since exception is thrown, studentRepository.save is never called
        verify(studentRepository, never()).save(any());
    }

    @Test
    void CanDeleteStudent() {
        // given
        long id = 8L;
        given(studentRepository.existsById(anyLong())).willReturn(true);

        // when
        studentServiceUnderTest.deleteStudent(id);

        // then
        verify(studentRepository).deleteById(id);
    }

    @Test
    void willThrowExceptionWhenDeleteStudentIdNotFound(){
        // given
        Long id = 8L;
        given(studentRepository.existsById(anyLong())).willReturn(false);

        // when
        // then
        assertThatThrownBy(()->studentServiceUnderTest.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " does not exists");

        verify(studentRepository, never()).deleteById(anyLong());
    }
}