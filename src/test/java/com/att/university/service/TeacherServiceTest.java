package com.att.university.service;

import com.att.university.dao.AcademicRankDao;
import com.att.university.dao.ScienceDegreeDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.exception.dao.PersonNotFoundException;
import com.att.university.mapper.teacher.TeacherRegisterRequestMapper;
import com.att.university.mapper.teacher.TeacherUpdateRequestMapper;
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

import java.util.ArrayList;
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

    @Mock
    private TeacherRegisterRequestMapper registerRequestMapper;

    @Mock
    private TeacherUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void findAllShouldNotThrowException() {
        when(teacherDao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> teacherService.findAll(1, 3));

        verify(teacherDao).findAll(anyInt(), anyInt());
    }

    @Test
    void findByIdShouldThrowExceptionWhenTeacherDoesNotExist() {
        Integer id = 4;

        when(teacherDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> teacherService.findById(id));

        verify(teacherDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao);
    }

    @Test
    void findByIdShouldReturnTeacherWhenTeacherExists() {
        Integer id = 4;

        when(teacherDao.findById(anyInt())).thenReturn(Optional.of(generateTeacher()));

        teacherService.findById(id);

        verify(teacherDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao);
    }

    @Test
    void registerShouldNotThrowExceptionWhenEmailDoesNotExist() {
        final TeacherRegisterRequest teacherRegisterRequest = generateRegisterRequest();
        final Teacher teacher = generateTeacher();

        doNothing().when(teacherRegisterValidator).validate(any(TeacherRegisterRequest.class));
        when(teacherDao.findByEmail(teacherRegisterRequest.getEmail())).thenReturn(Optional.empty());
        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.of(generateDegree()));
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateRank()));
        when(registerRequestMapper.convertToEntity(
                any(TeacherRegisterRequest.class), anyString(), any(AcademicRank.class), any(ScienceDegree.class))
        ).thenReturn(teacher);
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());

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
        when(updateRequestMapper.convertToEntity(
                any(TeacherUpdateRequest.class), any(AcademicRank.class), any(ScienceDegree.class))
        ).thenReturn(teacher);

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

    @Test
    void findAllWithoutPaginationShouldNotThrowException() {
        when(teacherDao.count()).thenReturn(2);
        when(teacherDao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> teacherService.findAllWithoutPagination());

        verify(teacherDao).findAll(anyInt(), anyInt());
        verify(teacherDao).count();
    }
    
    @Test
    void findByEmailShouldThrowExceptionWhenStudentDoesNotExist() {
        String email = "test@test.ru";

        when(teacherDao.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> teacherService.findByEmail(email));

        verify(teacherDao).findByEmail(anyString());
        verifyNoMoreInteractions(teacherDao);
    }

    @Test
    void findByEmailShouldReturnStudentWhenStudentExists() {
        String email = "test@test.ru";

        when(teacherDao.findByEmail(anyString())).thenReturn(Optional.of(generateTeacher()));

        assertDoesNotThrow(() -> teacherService.findByEmail(email));

        verify(teacherDao).findByEmail(anyString());
        verifyNoMoreInteractions(teacherDao);
    }

    @Test
    void countShouldNotThrowException() {
        when(teacherDao.count()).thenReturn(1);

        assertDoesNotThrow(() -> teacherService.count());

        verify(teacherDao).count();
    }

    @Test
    void deleteTeacherShouldThrowNotFoundExceptionIfTeacherNotFound() {
        when(teacherDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> teacherService.deleteById(1));

        verify(teacherDao).findById(anyInt());
        verifyNoMoreInteractions(teacherDao);
    }

    @Test
    void deleteTeacherShouldNotThrowExceptionIfTeacherIsFound() {
        when(teacherDao.findById(anyInt())).thenReturn(Optional.of(generateTeacher()));

        teacherService.deleteById(1);

        verify(teacherDao).findById(anyInt());
        verify(teacherDao).deleteById(anyInt());
        verifyNoMoreInteractions(teacherDao);
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
