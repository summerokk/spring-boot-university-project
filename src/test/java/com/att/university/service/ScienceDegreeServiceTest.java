package com.att.university.service;

import com.att.university.dao.ScienceDegreeDao;
import com.att.university.entity.ScienceDegree;
import com.att.university.exception.dao.ScienceDegreeNotFoundException;
import com.att.university.mapper.science_degree.ScienceDegreeAddRequestMapper;
import com.att.university.mapper.science_degree.ScienceDegreeUpdateRequestMapper;
import com.att.university.request.science_degree.ScienceDegreeAddRequest;
import com.att.university.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.university.service.impl.ScienceDegreeServiceImpl;
import com.att.university.validator.science_degree.ScienceDegreeAddValidator;
import com.att.university.validator.science_degree.ScienceDegreeUpdateValidator;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScienceDegreeServiceTest {
    @Mock
    private ScienceDegreeDao scienceDegreeDao;

    @Mock
    private ScienceDegreeUpdateValidator updateValidator;

    @Mock
    private ScienceDegreeAddValidator addValidator;

    @Mock
    private ScienceDegreeAddRequestMapper addRequestMapper;

    @Mock
    private ScienceDegreeUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private ScienceDegreeServiceImpl scienceDegreeService;

    @Test
    void updateScienceDegreeShouldThrowNotFoundExceptionIfScienceDegreeNotFound() {
        final ScienceDegreeUpdateRequest request = new ScienceDegreeUpdateRequest(1, "update");
        final ScienceDegree ScienceDegree = generateScienceDegrees().get(0);

        doNothing().when(updateValidator).validate(any(ScienceDegreeUpdateRequest.class));
        when(scienceDegreeDao.findById(ScienceDegree.getId())).thenReturn(Optional.empty());

        assertThrows(ScienceDegreeNotFoundException.class, () -> scienceDegreeService.update(request));

        verify(updateValidator).validate(any(ScienceDegreeUpdateRequest.class));
        verify(scienceDegreeDao).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeDao, updateValidator);
    }

    @Test
    void updateScienceDegreeShouldNotThrowExceptionIfScienceDegreeIsFound() {
        final ScienceDegreeUpdateRequest request = new ScienceDegreeUpdateRequest(1, "update");
        final ScienceDegree ScienceDegree = generateScienceDegrees().get(0);

        doNothing().when(updateValidator).validate(any(ScienceDegreeUpdateRequest.class));
        when(scienceDegreeDao.findById(ScienceDegree.getId())).thenReturn(Optional.of(generateScienceDegrees().get(0)));
        when(updateRequestMapper.convertToEntity(any(ScienceDegreeUpdateRequest.class))).thenReturn((generateScienceDegrees().get(0)));

        scienceDegreeService.update(request);

        verify(updateValidator).validate(any(ScienceDegreeUpdateRequest.class));
        verify(scienceDegreeDao).findById(anyInt());
        verify(scienceDegreeDao).update(any(ScienceDegree.class));
        verifyNoMoreInteractions(scienceDegreeDao, updateValidator);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(scienceDegreeDao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> scienceDegreeService.findAll());

        verify(scienceDegreeDao).findAll(anyInt(), anyInt());
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfScienceDegreeNotFound() {
        Integer id = 4;

        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ScienceDegreeNotFoundException.class, () -> scienceDegreeService.findById(id));

        verify(scienceDegreeDao).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeDao);
    }

    @Test
    void findByIdShouldReturnEntityWhenScienceDegreeExists() {
        Integer id = 4;

        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.of(generateScienceDegrees().get(0)));

        scienceDegreeService.findById(id);

        verify(scienceDegreeDao).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeDao);
    }

    @Test
    void createScienceDegreeShouldNotThrowException() {
        final ScienceDegreeAddRequest request = new ScienceDegreeAddRequest("new");
        doNothing().when(addValidator).validate(any(ScienceDegreeAddRequest.class));
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateScienceDegrees().get(0));

        scienceDegreeService.create(request);

        verify(addValidator).validate(any(ScienceDegreeAddRequest.class));
        verify(scienceDegreeDao).save(any(ScienceDegree.class));
        verifyNoMoreInteractions(scienceDegreeDao, addValidator);
    }

    @Test
    void deleteScienceDegreeShouldThrowNotFoundExceptionIfScienceDegreeNotFound() {
        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ScienceDegreeNotFoundException.class, () -> scienceDegreeService.deleteById(1));

        verify(scienceDegreeDao).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeDao);
    }

    @Test
    void deleteScienceDegreeShouldNotThrowExceptionIfScienceDegreeIsFound() {
        when(scienceDegreeDao.findById(anyInt())).thenReturn(Optional.of(generateScienceDegrees().get(0)));

        scienceDegreeService.deleteById(1);

        verify(scienceDegreeDao).findById(anyInt());
        verify(scienceDegreeDao).deleteById(anyInt());
        verifyNoMoreInteractions(scienceDegreeDao);
    }

    private List<ScienceDegree> generateScienceDegrees() {
        return Arrays.asList(
                new ScienceDegree(1, "degree1"),
                new ScienceDegree(2, "degree2"),
                new ScienceDegree(3, "degree3")
        );
    }
}
