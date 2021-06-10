package com.att.service;

import com.att.dao.AcademicRankRepository;
import com.att.entity.AcademicRank;
import com.att.exception.dao.AcademicRankNotFoundException;
import com.att.mapper.academicrank.AcademicRankAddRequestMapper;
import com.att.mapper.academicrank.AcademicRankUpdateRequestMapper;
import com.att.request.academic_rank.AcademicRankAddRequest;
import com.att.request.academic_rank.AcademicRankUpdateRequest;
import com.att.service.impl.AcademicRankServiceImpl;
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
class AcademicRankServiceTest {
    @Mock
    private AcademicRankRepository academicRankRepository;

    @Mock
    private AcademicRankAddRequestMapper addRequestMapper;

    @Mock
    private AcademicRankUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private AcademicRankServiceImpl academicRankService;

    @Test
    void updateAcademicRankShouldThrowNotFoundExceptionIfAcademicRankNotFound() {
        final AcademicRankUpdateRequest request = new AcademicRankUpdateRequest(1, "update");
        final AcademicRank AcademicRank = generateAcademicRanks().get(0);

        when(academicRankRepository.findById(AcademicRank.getId())).thenReturn(Optional.empty());

        assertThrows(AcademicRankNotFoundException.class, () -> academicRankService.update(request));

        verify(academicRankRepository).findById(anyInt());
        verifyNoMoreInteractions(academicRankRepository);
    }

    @Test
    void updateAcademicRankShouldNotThrowExceptionIfAcademicRankIsFound() {
        final AcademicRankUpdateRequest request = new AcademicRankUpdateRequest(1, "update");
        final AcademicRank AcademicRank = generateAcademicRanks().get(0);

        when(academicRankRepository.findById(AcademicRank.getId())).thenReturn(Optional.of(generateAcademicRanks().get(0)));
        when(updateRequestMapper.convertToEntity(any(AcademicRankUpdateRequest.class))).thenReturn((generateAcademicRanks().get(0)));

        academicRankService.update(request);

        verify(academicRankRepository).findById(anyInt());
        verify(academicRankRepository).save(any(AcademicRank.class));
        verifyNoMoreInteractions(academicRankRepository);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(academicRankRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> academicRankService.findAll());

        verify(academicRankRepository).findAll();
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfAcademicRankNotFound() {
        Integer id = 4;

        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(AcademicRankNotFoundException.class, () -> academicRankService.findById(id));

        verify(academicRankRepository).findById(anyInt());
        verifyNoMoreInteractions(academicRankRepository);
    }

    @Test
    void findByIdShouldReturnEntityWhenAcademicRankExists() {
        Integer id = 4;

        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.of(generateAcademicRanks().get(0)));

        academicRankService.findById(id);

        verify(academicRankRepository).findById(anyInt());
        verifyNoMoreInteractions(academicRankRepository);
    }

    @Test
    void createAcademicRankShouldNotThrowException() {
        final AcademicRankAddRequest request = new AcademicRankAddRequest("new");
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateAcademicRanks().get(0));

        academicRankService.create(request);

        verify(academicRankRepository).save(any(AcademicRank.class));
        verifyNoMoreInteractions(academicRankRepository);
    }

    @Test
    void deleteAcademicRankShouldThrowNotFoundExceptionIfAcademicRankNotFound() {
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(AcademicRankNotFoundException.class, () -> academicRankService.deleteById(1));

        verify(academicRankRepository).findById(anyInt());
        verifyNoMoreInteractions(academicRankRepository);
    }

    @Test
    void deleteAcademicRankShouldNotThrowExceptionIfAcademicRankIsFound() {
        when(academicRankRepository.findById(anyInt())).thenReturn(Optional.of(generateAcademicRanks().get(0)));

        academicRankService.deleteById(1);

        verify(academicRankRepository).findById(anyInt());
        verify(academicRankRepository).deleteById(anyInt());
        verifyNoMoreInteractions(academicRankRepository);
    }

    private List<AcademicRank> generateAcademicRanks() {
        return Arrays.asList(
                new AcademicRank(1, "rank"),
                new AcademicRank(2, "rank2"),
                new AcademicRank(3, "rank3")
        );
    }
}
