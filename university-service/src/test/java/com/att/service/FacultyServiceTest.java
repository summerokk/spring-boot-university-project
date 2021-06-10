package com.att.service;

import com.att.dao.FacultyRepository;
import com.att.entity.Faculty;
import com.att.exception.dao.FacultyNotFoundException;
import com.att.mapper.faculty.FacultyAddRequestMapper;
import com.att.mapper.faculty.FacultyUpdateRequestMapper;
import com.att.request.faculty.FacultyAddRequest;
import com.att.request.faculty.FacultyUpdateRequest;
import com.att.service.impl.FacultyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private FacultyAddRequestMapper addRequestMapper;

    @Mock
    private FacultyUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    void updateFacultyShouldThrowNotFoundExceptionIfFacultyNotFound() {
        final FacultyUpdateRequest request = new FacultyUpdateRequest(1, "update");
        final Faculty Faculty = generateFaculties().get(0);

        when(facultyRepository.findById(Faculty.getId())).thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () -> facultyService.update(request));

        verify(facultyRepository).findById(anyInt());
    }

    @Test
    void updateFacultyShouldNotThrowExceptionIfFacultyIsFound() {
        final FacultyUpdateRequest request = new FacultyUpdateRequest(1, "update");
        final Faculty Faculty = generateFaculties().get(0);

        when(facultyRepository.findById(Faculty.getId())).thenReturn(Optional.of(generateFaculties().get(0)));
        when(updateRequestMapper.convertToEntity(any(FacultyUpdateRequest.class))).thenReturn((generateFaculties().get(0)));

        facultyService.update(request);

        verify(facultyRepository).findById(anyInt());
        verify(facultyRepository).save(any(Faculty.class));
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(facultyRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> facultyService.findAll());

        verify(facultyRepository).findAll();
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfFacultyNotFound() {
        Integer id = 4;

        when(facultyRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () -> facultyService.findById(id));

        verify(facultyRepository).findById(anyInt());
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void findByIdShouldReturnEntityWhenFacultyExists() {
        Integer id = 4;

        when(facultyRepository.findById(anyInt())).thenReturn(Optional.of(generateFaculties().get(0)));

        facultyService.findById(id);

        verify(facultyRepository).findById(anyInt());
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void createFacultyShouldNotThrowException() {
        final FacultyAddRequest request = new FacultyAddRequest("new");
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateFaculties().get(0));

        facultyService.create(request);

        verify(facultyRepository).save(any(Faculty.class));
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void deleteFacultyShouldThrowNotFoundExceptionIfFacultyNotFound() {
        when(facultyRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(FacultyNotFoundException.class, () -> facultyService.deleteById(1));

        verify(facultyRepository).findById(anyInt());
        verifyNoMoreInteractions(facultyRepository);
    }

    @Test
    void deleteFacultyShouldNotThrowExceptionIfFacultyIsFound() {
        when(facultyRepository.findById(anyInt())).thenReturn(Optional.of(generateFaculties().get(0)));

        facultyService.deleteById(1);

        verify(facultyRepository).findById(anyInt());
        verify(facultyRepository).deleteById(anyInt());
        verifyNoMoreInteractions(facultyRepository);
    }

    private List<Faculty> generateFaculties() {
        return Arrays.asList(
                new Faculty(1, "Special Topics in Agronomy"),
                new Faculty(2, "Math"),
                new Faculty(3, "Biology")
        );
    }
}
