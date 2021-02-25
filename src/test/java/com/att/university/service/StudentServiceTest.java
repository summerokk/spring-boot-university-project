package com.att.university.service;

import com.att.university.dao.GroupDao;
import com.att.university.dao.StudentDao;
import com.att.university.entity.Student;
import com.att.university.service.impl.StudentServiceImpl;
import com.att.university.validator.StudentValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentDao studentDao;

    @Mock
    private GroupDao groupDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StudentValidator studentValidator;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void registerShouldNotThrowExceptionWhenEmailDoesNotExist() {
        String email = "test@test.ru";

        final Student student = Student.builder()
                .withId(1)
                .withFirstName("name")
                .withLastName("last")
                .withEmail(email)
                .withPassword("123456789")
                .build();

        doNothing().when(studentValidator).validate(any(Student.class));
        when(studentDao.findByEmail(email)).thenReturn(Optional.empty());

        studentService.register(student);

        verify(studentValidator).validate(any(Student.class));
        verify(studentDao).findByEmail(anyString());
        verify(studentDao).save(any(Student.class));
    }

    @Test
    void registerShouldThrowExceptionIfEmailExists() {
        String email = "test@test.ru";

        final Student student = Student.builder()
                .withId(1)
                .withFirstName("name")
                .withLastName("last")
                .withEmail(email)
                .withPassword("123456789")
                .build();

        doNothing().when(studentValidator).validate(any(Student.class));
        when(studentDao.findByEmail(email)).thenReturn(Optional.of(student));

        assertThrows(RuntimeException.class, () -> studentService.register(student));

        verify(studentValidator).validate(any(Student.class));
        verify(studentDao).findByEmail(anyString());
        verifyNoMoreInteractions(studentDao, studentValidator);
    }

    @Test
    void updateShouldNotThrowExceptionWhenUserExist() {
        Integer id = 1;

        final Student student = Student.builder()
                .withId(id)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withPassword("123456789")
                .build();

        doNothing().when(studentValidator).validate(any(Student.class));
        when(studentDao.findById(id)).thenReturn(Optional.of(student));

        studentService.update(student);

        verify(studentValidator).validate(any(Student.class));
        verify(studentDao).findById(anyInt());
        verify(studentDao).update(any(Student.class));
    }

    @Test
    void updateShouldThrowExceptionIfUserExists() {
        Integer id = 1;

        final Student student = Student.builder()
                .withId(id)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withPassword("123456789")
                .build();

        doNothing().when(studentValidator).validate(any(Student.class));
        when(studentDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.update(student));

        verify(studentValidator).validate(any(Student.class));
        verify(studentDao).findById(anyInt());
        verifyNoMoreInteractions(studentDao, studentValidator);
    }

    @Test
    void loginShouldReturnTrueIfEmailAndPasswordAreValid() {
        String email = "test@test.ru";
        String password = "12345678";

        final Student student = Student.builder()
                .withEmail(email)
                .withPassword(password)
                .withFirstName("Name")
                .withLastName("Last")
                .withId(1)
                .build();

        when(studentDao.findByEmail(email)).thenReturn(Optional.of(student));

        studentService.login(email, password);

        verify(studentDao).findByEmail(anyString());
        verify(passwordEncoder).encode(password);
    }

    @Test
    void loginShouldThrowExceptionWhenEmailNotFound() {
        String email = "test@test.ru";
        String password = "12345678";

        when(studentDao.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.login(email, password));

        verify(studentDao).findByEmail(anyString());
        verifyNoMoreInteractions(passwordEncoder, studentDao);
    }
}
