package com.att.service;

import com.att.exception.dao.ScienceDegreeNotFoundException;
import com.att.mapper.science_degree.ScienceDegreeAddRequestMapper;
import com.att.mapper.science_degree.ScienceDegreeUpdateRequestMapper;
import com.att.request.science_degree.ScienceDegreeAddRequest;
import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.validator.science_degree.ScienceDegreeAddValidator;
import com.att.validator.science_degree.ScienceDegreeUpdateValidator;
import com.att.dao.ScienceDegreeRepository;
import com.att.entity.ScienceDegree;
import com.att.service.impl.ScienceDegreeServiceImpl;
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
    private ScienceDegreeRepository scienceDegreeRepository;

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
        when(scienceDegreeRepository.findById(ScienceDegree.getId())).thenReturn(Optional.empty());

        assertThrows(ScienceDegreeNotFoundException.class, () -> scienceDegreeService.update(request));

        verify(updateValidator).validate(any(ScienceDegreeUpdateRequest.class));
        verify(scienceDegreeRepository).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeRepository, updateValidator);
    }

    @Test
    void updateScienceDegreeShouldNotThrowExceptionIfScienceDegreeIsFound() {
        final ScienceDegreeUpdateRequest request = new ScienceDegreeUpdateRequest(1, "update");
        final ScienceDegree ScienceDegree = generateScienceDegrees().get(0);

        doNothing().when(updateValidator).validate(any(ScienceDegreeUpdateRequest.class));
        when(scienceDegreeRepository.findById(ScienceDegree.getId())).thenReturn(Optional.of(generateScienceDegrees().get(0)));
        when(updateRequestMapper.convertToEntity(any(ScienceDegreeUpdateRequest.class))).thenReturn((generateScienceDegrees().get(0)));

        scienceDegreeService.update(request);

        verify(updateValidator).validate(any(ScienceDegreeUpdateRequest.class));
        verify(scienceDegreeRepository).findById(anyInt());
        verify(scienceDegreeRepository).save(any(ScienceDegree.class));
        verifyNoMoreInteractions(scienceDegreeRepository, updateValidator);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(scienceDegreeRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> scienceDegreeService.findAll());

        verify(scienceDegreeRepository).findAll();
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfScienceDegreeNotFound() {
        Integer id = 4;

        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ScienceDegreeNotFoundException.class, () -> scienceDegreeService.findById(id));

        verify(scienceDegreeRepository).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeRepository);
    }

    @Test
    void findByIdShouldReturnEntityWhenScienceDegreeExists() {
        Integer id = 4;

        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.of(generateScienceDegrees().get(0)));

        scienceDegreeService.findById(id);

        verify(scienceDegreeRepository).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeRepository);
    }

    @Test
    void createScienceDegreeShouldNotThrowException() {
        final ScienceDegreeAddRequest request = new ScienceDegreeAddRequest("new");
        doNothing().when(addValidator).validate(any(ScienceDegreeAddRequest.class));
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateScienceDegrees().get(0));

        scienceDegreeService.create(request);

        verify(addValidator).validate(any(ScienceDegreeAddRequest.class));
        verify(scienceDegreeRepository).save(any(ScienceDegree.class));
        verifyNoMoreInteractions(scienceDegreeRepository, addValidator);
    }

    @Test
    void deleteScienceDegreeShouldThrowNotFoundExceptionIfScienceDegreeNotFound() {
        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ScienceDegreeNotFoundException.class, () -> scienceDegreeService.deleteById(1));

        verify(scienceDegreeRepository).findById(anyInt());
        verifyNoMoreInteractions(scienceDegreeRepository);
    }

    @Test
    void deleteScienceDegreeShouldNotThrowExceptionIfScienceDegreeIsFound() {
        when(scienceDegreeRepository.findById(anyInt())).thenReturn(Optional.of(generateScienceDegrees().get(0)));

        scienceDegreeService.deleteById(1);

        verify(scienceDegreeRepository).findById(anyInt());
        verify(scienceDegreeRepository).deleteById(anyInt());
        verifyNoMoreInteractions(scienceDegreeRepository);
    }

    private List<ScienceDegree> generateScienceDegrees() {
        return Arrays.asList(
                new ScienceDegree(1, "degree1"),
                new ScienceDegree(2, "degree2"),
                new ScienceDegree(3, "degree3")
        );
    }
}
