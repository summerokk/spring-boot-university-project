package com.att.service;

import com.att.dao.AcademicRankRepository;
import com.att.dao.ScienceDegreeRepository;
import com.att.dao.TeacherRepository;
import com.att.entity.AcademicRank;
import com.att.entity.ScienceDegree;
import com.att.entity.Teacher;
import com.att.exception.dao.PersonNotFoundException;
import com.att.mapper.teacher.TeacherRegisterRequestMapper;
import com.att.mapper.teacher.TeacherUpdateRequestMapper;
import com.att.request.person.teacher.TeacherRegisterRequest;
import com.att.request.person.teacher.TeacherUpdateRequest;
import com.att.service.impl.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private AcademicRankRepository academicRankRepository;

    @Mock
    private ScienceDegreeRepository scienceDegreeRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TeacherRegisterRequestMapper registerRequestMapper;

    @Mock
    private TeacherUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Test
    void findAllPaginationShouldNotThrowException() {
        Page<Teacher> teachers = new PageImpl<>(Collections.singletonList(generateTeacher()));

        when(teacherRepository.findAll(any(PageRequest.class))).thenReturn(teachers);

        assertDoesNotThrow(() -> teacherService.findAll(PageRequest.of(1, 2)));

        verify(teacherRepository).findAll(any(PageRequest.class));
    }

    @Test
    void findByIdShouldThrowExceptionWhenTeacherDoesNotExist() {
        Integer id = 4;

        when(teacherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> teacherService.findById(id));

        verify(teacherRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void findByIdShouldReturnTeacherWhenTeacherExists() {
        Integer id = 4;

        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(generateTeacher()));

        teacherService.findById(id);

        verify(teacherRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void registerShouldNotThrowExceptionWhenEmailDoesNotExist() {
        final TeacherRegisterRequest teacherRegisterRequest = generateRegisterRequest();
        final Teacher teacher = generateTeacher();

        when(teacherRepository.findByEmail(teacherRegisterRequest.getEmail())).thenReturn(Optional.empty());
        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.of(generateDegree()));
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.of(generateRank()));
        when(registerRequestMapper.convertToEntity(
                any(TeacherRegisterRequest.class), anyString(), any(AcademicRank.class), any(ScienceDegree.class))
        ).thenReturn(teacher);
        when(passwordEncoder.encode(anyString())).thenReturn(anyString());

        teacherService.register(teacherRegisterRequest);

        verify(teacherRepository).findByEmail(anyString());
        verify(scienceDegreeRepository).findById(anyInt());
        verify(academicRankRepository).findById(anyInt());
        verify(teacherRepository).save(any(Teacher.class));
    }

    @Test
    void registerShouldThrowExceptionIfEmailExists() {
        final TeacherRegisterRequest registerRequest = generateRegisterRequest();
        final Teacher teacher = generateTeacher();

        when(teacherRepository.findByEmail(teacher.getEmail())).thenReturn(Optional.of(teacher));

        assertThrows(RuntimeException.class, () -> teacherService.register(registerRequest));

        verify(teacherRepository).findByEmail(anyString());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void registerShouldThrowExceptionIfAcademicRankDoesNotExist() {
        final TeacherRegisterRequest registerRequest = generateRegisterRequest();

        when(teacherRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.register(registerRequest));

        verify(teacherRepository).findByEmail(anyString());
        verify(academicRankRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository, academicRankRepository, scienceDegreeRepository);
    }

    @Test
    void registerShouldThrowExceptionIfScienceDegreeDoesNotExist() {
        final TeacherRegisterRequest registerRequest = generateRegisterRequest();

        when(teacherRepository.findByEmail(registerRequest.getEmail())).thenReturn(Optional.empty());
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.of(generateRank()));
        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.register(registerRequest));

        verify(teacherRepository).findByEmail(anyString());
        verify(academicRankRepository).findById(anyInt());
        verify(scienceDegreeRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository, academicRankRepository, scienceDegreeRepository);
    }

    @Test
    void updateShouldNotThrowExceptionWhenUserExist() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
        final Teacher teacher = generateTeacher();

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.of(generateDegree()));
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.of(generateRank()));
        when(updateRequestMapper.convertToEntity(
                any(TeacherUpdateRequest.class), any(AcademicRank.class), any(ScienceDegree.class), anyString())
        ).thenReturn(teacher);

        teacherService.update(teacherUpdateRequest);

        verify(teacherRepository).findById(anyInt());
        verify(scienceDegreeRepository).findById(anyInt());
        verify(academicRankRepository).findById(anyInt());
        verify(teacherRepository).save(any(Teacher.class));
    }

    @Test
    void updateShouldThrowExceptionIfScienceDegreeDoesNotExist() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
        final Teacher teacher = generateTeacher();

        when(teacherRepository.findById(teacherUpdateRequest.getId())).thenReturn(Optional.of(teacher));
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.of(generateRank()));
        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.update(teacherUpdateRequest));

        verify(teacherRepository).findById(anyInt());
        verify(academicRankRepository).findById(anyInt());
        verify(scienceDegreeRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository, academicRankRepository, scienceDegreeRepository);
    }

    @Test
    void updateShouldThrowExceptionIfAcademicRankDoesNotExist() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();
        final Teacher teacher = generateTeacher();

        when(teacherRepository.findById(teacherUpdateRequest.getId())).thenReturn(Optional.of(teacher));
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.update(teacherUpdateRequest));

        verify(teacherRepository).findById(anyInt());
        verify(academicRankRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository, academicRankRepository, scienceDegreeRepository);
    }

    @Test
    void updateShouldThrowExceptionIfUserExists() {
        final TeacherUpdateRequest teacherUpdateRequest = generateUpdateRequest();

        when(teacherRepository.findById(teacherUpdateRequest.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.update(teacherUpdateRequest));

        verify(teacherRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void loginShouldReturnTrueIfEmailAndPasswordAreValid() {
        String email = "test@test.ru";
        String password = "12345678";

        final Teacher teacher = generateTeacher();

        when(teacherRepository.findByEmail(email)).thenReturn(Optional.of(teacher));

        teacherService.login(email, password);

        verify(teacherRepository).findByEmail(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
    }

    @Test
    void loginShouldThrowExceptionWhenEmailNotFound() {
        String email = "test@test.ru";
        String password = "12345678";

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> teacherService.login(email, password));

        verify(teacherRepository).findByEmail(anyString());
        verifyNoMoreInteractions(passwordEncoder, teacherRepository);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(teacherRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> teacherService.findAll());

        verify(teacherRepository).findAll();
    }

    @Test
    void findByEmailShouldThrowExceptionWhenStudentDoesNotExist() {
        String email = "test@test.ru";

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> teacherService.findByEmail(email));

        verify(teacherRepository).findByEmail(anyString());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void findByEmailShouldReturnStudentWhenStudentExists() {
        String email = "test@test.ru";

        when(teacherRepository.findByEmail(anyString())).thenReturn(Optional.of(generateTeacher()));

        assertDoesNotThrow(() -> teacherService.findByEmail(email));

        verify(teacherRepository).findByEmail(anyString());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void deleteTeacherShouldThrowNotFoundExceptionIfTeacherNotFound() {
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> teacherService.deleteById(1));

        verify(teacherRepository).findById(anyInt());
        verifyNoMoreInteractions(teacherRepository);
    }

    @Test
    void deleteTeacherShouldNotThrowExceptionIfTeacherIsFound() {
        when(teacherRepository.findById(anyInt())).thenReturn(Optional.of(generateTeacher()));

        teacherService.deleteById(1);

        verify(teacherRepository).findById(anyInt());
        verify(teacherRepository).deleteById(anyInt());
        verifyNoMoreInteractions(teacherRepository);
    }

    private TeacherRegisterRequest generateRegisterRequest() {
        return TeacherRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("1234567890")
                .withLinkedin("https://test.ru")
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
                .withLinkedin("https://test.ru")
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
                .withLinkedin("https://test.ru")
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
