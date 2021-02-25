package com.att.university.service;

import com.att.university.H2Config;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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