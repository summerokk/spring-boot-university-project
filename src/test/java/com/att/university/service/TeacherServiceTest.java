package com.att.university.service;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.service.impl.TeacherServiceImpl;
import com.att.university.validator.person.TeacherRegisterValidator;
import com.att.university.validator.person.TeacherUpdateValidator;
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
class TeacherServiceTest {
    @Mock
    private TeacherDao teacherDao;

    @Mock
    private AcademicRankDao academicRankDao;

    @Mock
    private ScienceDegreeDao scienceDegreeDao;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TeacherUpdateValidator teacherUpdateValidator;

    @Mock
    private TeacherRegisterValidator teacherRegisterValidator;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void registerShouldNotThrowExceptionWhenEmailDoesNotExist() {
        final TeacherRegisterRequest teacherRegisterRequest = generateRegisterRequest();

        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        when(teacherDao.findByEmail(teacherRegisterRequest.getEmail())).thenReturn(Optional.empty());
        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.of(generateDegree()));
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));

        teacherService.register(teacherRegisterRequest);

        verify(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        verify(teacherDao).findByEmail(anyString());
        verify(scienceDegreeDao).findById(anyInt());
        verify(academicRankDao).findById(anyInt());
        verify(teacherDao).save(any(Teacher.class));
    }

    @Test
    void registerShouldThrowExceptionIfEmailExists() {
        final TeacherRegisterRequest registerRequest = generateRegisterRequest();
        final Teacher teacher = generateTeacher();

        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        when(teacherDao.findByEmail(teacher.getEmail())).thenReturn(Optional.of(teacher));

        assertThrows(RuntimeException.class, () -> teacherService.register(registerRequest));

        verify(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        verify(teacherDao).findByEmail(anyString());
        verifyNoMoreInteractions(teacherDao, teacherRegisterValidator);
    }

    @Test
    void registerShouldThrowExceptionIfAcademicRankDoesNotExist() {
        final TeacherRegisterRequest registerRequest = generateRegisterRequest();

        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        when(teacherDao.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.register(registerRequest));

        verify(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        verify(teacherDao).findByEmail(anyString());
        verify(academicRankDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao, teacherRegisterValidator, academicRankDao, scienceDegreeDao);
    }

    @Test
    void registerShouldThrowExceptionIfScienceDegreeDoesNotExist() {
        final TeacherRegisterRequest registerRequest = generateRegisterRequest();

        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        when(teacherDao.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));
        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.register(registerRequest));

        verify(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        verify(teacherDao).findByEmail(anyString());
        verify(academicRankDao).findById(anyInt());
        verify(scienceDegreeDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao, teacherRegisterValidator, academicRankDao, scienceDegreeDao);
    }

    @Test
    void updateShouldNotThrowExceptionWhenUserExist() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
        final Teacher teacher = generateTeacher();

        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        when(teacherDao.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.of(generateDegree()));
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));

        teacherService.update(teacherUpdateRequest);

        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        verify(teacherDao).findById(anyInt());
        verify(scienceDegreeDao).findById(anyInt());
        verify(academicRankDao).findById(anyInt());
        verify(teacherDao).update(any(Teacher.class));
    }

    @Test
    void updateShouldThrowExceptionIfScienceDegreeDoesNotExist() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
        final Teacher teacher = generateTeacher();

        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        when(teacherDao.findById(teacherUpdateRequest.getId())).thenReturn(Optional.of(teacher));
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));
        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.update(teacherUpdateRequest));

        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        verify(teacherDao).findById(anyInt());
        verify(academicRankDao).findById(anyInt());
        verify(scienceDegreeDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao, teacherUpdateValidator, academicRankDao, scienceDegreeDao);
    }

    @Test
    void updateShouldThrowExceptionIfAcademicRankDoesNotExist() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
        final Teacher teacher = generateTeacher();

        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        when(teacherDao.findById(teacherUpdateRequest.getId())).thenReturn(Optional.of(teacher));
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.update(teacherUpdateRequest));

        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        verify(teacherDao).findById(anyInt());
        verify(academicRankDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao, teacherUpdateValidator, academicRankDao, scienceDegreeDao);
    }

    @Test
    void updateShouldThrowExceptionIfUserExists() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();

        doNothing().when(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        when(teacherDao.findById(teacherUpdateRequest.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.update(teacherUpdateRequest));

        verify(teacherUpdateValidator).validate(any(TeacherUpdateRequest.class));
        verify(teacherDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao, teacherUpdateValidator);
    }

    @Test
    void loginShouldReturnTrueIfEmailAndPasswordAreValid() {
        String email = "test@test.ru";
        String password = "12345678";

        final Teacher teacher = generateTeacher();

        when(teacherDao.findByEmail(email)).thenReturn(Optional.of(teacher));

        teacherService.login(email, password);

        verify(teacherDao).findByEmail(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    void loginShouldThrowExceptionWhenEmailNotFound() {
        String email = "test@test.ru";
        String password = "12345678";

        when(teacherDao.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.login(email, password));

        verify(teacherDao).findByEmail(anyString());
        verifyNoMoreInteractions(passwordEncoder, teacherDao);
    }

    private TeacherRegisterRequest generateRegisterRequest() {
        return TeacherRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567890")
                .withLinkedin("http://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();
    }

    private TeacherUpdateRequest generateUpdateRequest() {
        return TeacherUpdateRequest.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567890")
                .withLinkedin("http://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();
    }

    private Teacher generateTeacher() {
        AcademicRank academicRank = generateRank();
        ScienceDegree scienceDegree = generateDegree();

        return Teacher.builder()
                .withId(1)
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567890")
                .withLinkedin("http://test.ru")
                .withAcademicRank(academicRank)
                .withScienceDegree(scienceDegree)
                .build();
    }

    private AcademicRank generateRank() {
        return new AcademicRank(1, "testRank");
    }

    private ScienceDegree generateDegree() {
        return new ScienceDegree(1, "testDegree");
    }
}
