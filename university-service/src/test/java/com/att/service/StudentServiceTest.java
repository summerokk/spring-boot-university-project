package com.att.service;

import com.att.exception.dao.GroupNotFoundException;
import com.att.exception.dao.PersonNotFoundException;
import com.att.mapper.student.StudentRegisterRequestMapper;
import com.att.mapper.student.StudentUpdateRequestMapper;
import com.att.request.person.student.StudentRegisterRequest;
import com.att.request.person.student.StudentUpdateRequest;
import com.att.validator.person.StudentRegisterValidator;
import com.att.validator.person.StudentUpdateValidator;
import com.att.dao.GroupRepository;
import com.att.dao.StudentRepository;
import com.att.entity.Faculty;
import com.att.entity.Group;
import com.att.entity.Student;
import com.att.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StudentUpdateValidator studentUpdateValidator;

    @Mock
    private StudentRegisterValidator studentRegisterValidator;

    @Mock
    private StudentRegisterRequestMapper registerRequestMapper;

    @Mock
    private StudentUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void registerShouldNotThrowExceptionWhenEmailDoesNotExist() {
        String email = "test@test.ru";

        final StudentRegisterRequest registerRequest = StudentRegisterRequest.builder()
                .withFirstName("name")
                .withLastName("last")
                .withEmail(email)
                .withPassword("123456789")
                .build();

        final Student student = generateEntity();

        doNothing().when(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(registerRequestMapper.convertToEntity(any(StudentRegisterRequest.class), anyString())).thenReturn(student);
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());

        studentService.register(registerRequest);

        verify(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        verify(registerRequestMapper).convertToEntity(any(StudentRegisterRequest.class), anyString());
        verify(passwordEncoder).encode(anyString());
        verify(studentRepository).findByEmail(anyString());
        verify(studentRepository).save(any(Student.class));
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

        final Student student = generateEntity();

        doNothing().when(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        assertThrows(RuntimeException.class, () -> studentService.register(registerRequest));

        verify(studentRegisterValidator).validate(any(StudentRegisterRequest.class));
        verify(studentRepository).findByEmail(anyString());
        verifyNoMoreInteractions(studentRepository, studentRegisterValidator);
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
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(group));
        when(updateRequestMapper.convertToEntity(studentRequest, group, student.getPassword())).thenReturn(student);

        studentService.update(studentRequest);

        verify(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        verify(updateRequestMapper).convertToEntity(any(StudentUpdateRequest.class), any(Group.class), anyString());
        verify(studentRepository).findById(anyInt());
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void updateShouldThrowExceptionIfUserExists() {
        Integer studentId = 1;

        final StudentUpdateRequest student = StudentUpdateRequest.builder()
                .withId(studentId)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .build();

        doNothing().when(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> studentService.update(student));

        verify(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        verify(studentRepository).findById(anyInt());
        verifyNoMoreInteractions(studentRepository, studentUpdateValidator);
    }

    @Test
    void updateShouldThrowExceptionIfGroupIsNotFound() {
        Integer studentId = 1;

        final StudentUpdateRequest updateRequest = StudentUpdateRequest.builder()
                .withId(studentId)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withGroupId(1)
                .build();

        final Student student = Student.builder()
                .withId(studentId)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("email")
                .withPassword("123456789")
                .build();

        doNothing().when(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(groupRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(GroupNotFoundException.class, () -> studentService.update(updateRequest));

        verify(studentUpdateValidator).validate(any(StudentUpdateRequest.class));
        verify(studentRepository).findById(anyInt());
        verifyNoMoreInteractions(studentRepository, studentUpdateValidator);
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

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        studentService.login(email, password);

        verify(studentRepository).findByEmail(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    void loginShouldThrowExceptionWhenEmailNotFound() {
        String email = "test@test.ru";
        String password = "12345678";

        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.login(email, password));

        verify(studentRepository).findByEmail(anyString());
        verifyNoMoreInteractions(passwordEncoder, studentRepository);
    }

    @Test
    void findByIdShouldThrowExceptionWhenStudentDoesNotExist() {
        Integer id = 4;

        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> studentService.findById(id));

        verify(studentRepository).findById(anyInt());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void findByIdShouldReturnStudentWhenStudentExists() {
        Integer id = 4;

        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(generateEntity()));

        studentService.findById(id);

        verify(studentRepository).findById(anyInt());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void findByEmailShouldThrowExceptionWhenStudentDoesNotExist() {
        String email = "test@test.ru";

        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> studentService.findByEmail(email));

        verify(studentRepository).findByEmail(anyString());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void findByEmailShouldReturnStudentWhenStudentExists() {
        String email = "test@test.ru";

        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(generateEntity()));

        assertDoesNotThrow(() -> studentService.findByEmail(email));

        verify(studentRepository).findByEmail(anyString());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void findAllShouldNotThrowException() {
        Page<Student> students = new PageImpl<>(Collections.singletonList(generateEntity()));

        when(studentRepository.findAll(any(PageRequest.class))).thenReturn(students);

        assertDoesNotThrow(() -> studentService.findAll(PageRequest.of(0, 12)));

        verify(studentRepository).findAll(any(PageRequest.class));
    }

    @Test
    void deleteStudentShouldThrowNotFoundExceptionIfStudentNotFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> studentService.delete(1));

        verify(studentRepository).findById(anyInt());
        verifyNoMoreInteractions(studentRepository);
    }

    @Test
    void deleteStudentShouldNotThrowExceptionIfStudentIsFound() {
        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(generateEntity()));

        studentService.delete(1);

        verify(studentRepository).findById(anyInt());
        verify(studentRepository).deleteById(anyInt());
        verifyNoMoreInteractions(studentRepository);
    }

    private Student generateEntity() {
        return Student.builder()
                .withId(1)
                .withFirstName("name")
                .withLastName("last")
                .withEmail("test@test.ru")
                .withPassword("123456789")
                .build();
    }
}
