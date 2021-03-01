package com.att.university.service;

import com.att.university.dao.GroupDao;
import com.att.university.dao.StudentDao;
import com.att.university.entity.Faculty;
import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.request.person.student.StudentUpdateRequest;
import com.att.university.service.impl.StudentServiceImpl;
import com.att.university.validator.person.StudentRegisterValidator;
import com.att.university.validator.person.StudentUpdateValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentDao studentDao;

    @Mock
    private GroupDao groupDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StudentUpdateValidator studentUpdateValidator;

    @Mock
    private StudentRegisterValidator studentRegisterValidator;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void registerShouldNotThrowExceptionWhenEmailDoesNotExist() {
        String email = "test@test.ru";

        final StudentRegisterRequest student = StudentRegisterRequest.builder()
                .withFirstName("name")
                .withLastName("last")
                .withEmail(email)
                .withPassword("123456789")
                .build();

        doNothing().when(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        when(studentDao.findByEmail(email)).thenReturn(Optional.empty());

        studentService.register(student);

        verify(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        verify(studentDao).findByEmail(anyString());
        verify(studentDao).save(any(Student.class));
    }

    @Test
    void registerShouldThrowExceptionIfEmailExists() {
        String email = "test@test.ru";

        final StudentRegisterRequest registerRequest = StudentRegisterRequest.builder()
                .withFirstName("name")
                .withLastName("last")
                .withEmail(email)
                .withPassword("123456789")
                .build();

        final Student student = Student.builder()
                .withId(1)
                .withFirstName("name")
                .withLastName("last")
                .withEmail(email)
                .withPassword("123456789")
                .build();

        doNothing().when(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        when(studentDao.findByEmail(email)).thenReturn(Optional.of(student));

        assertThrows(RuntimeException.class, () -> studentService.register(registerRequest));

        verify(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        verify(studentDao).findByEmail(anyString());
        verifyNoMoreInteractions(studentDao, studentRegisterValidator);
    }

    @Test
    void updateShouldNotThrowExceptionWhenUserExist() {
        Integer studentId = 1;
        Integer groupId = 1;

        final StudentUpdateRequest studentRequest = StudentUpdateRequest.builder()
                .withId(studentId)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withPassword("123456789")
                .withGroupId(1)
                .build();

        final Student student = Student.builder()
                .withId(studentId)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withPassword("123456789")
                .build();

        final Group group = new Group(groupId, "Test", new Faculty(1, "FacultyTest"));

        doNothing().when(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        when(studentDao.findById(studentId)).thenReturn(Optional.of(student));
        when(groupDao.findById(groupId)).thenReturn(Optional.of(group));

        studentService.update(studentRequest);

        verify(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        verify(studentDao).findById(anyInt());
        verify(studentDao).update(any(Student.class));
    }

    @Test
    void updateShouldThrowExceptionIfUserExists() {
        Integer studentId = 1;

        final StudentUpdateRequest student = StudentUpdateRequest.builder()
                .withId(studentId)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withPassword("123456789")
                .build();

        doNothing().when(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        when(studentDao.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.update(student));

        verify(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        verify(studentDao).findById(anyInt());
        verifyNoMoreInteractions(studentDao, studentUpdateValidator);
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
