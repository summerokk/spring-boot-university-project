package com.att.university.service;

import com.att.university.dao.AcademicRankDao;
import com.att.university.entity.AcademicRank;
import com.att.university.exception.dao.AcademicRankNotFoundException;
import com.att.university.mapper.academic_rank.AcademicRankAddRequestMapper;
import com.att.university.mapper.academic_rank.AcademicRankUpdateRequestMapper;
import com.att.university.request.academic_rank.AcademicRankAddRequest;
import com.att.university.request.academic_rank.AcademicRankUpdateRequest;
import com.att.university.service.impl.AcademicRankServiceImpl;
import com.att.university.validator.academic_rank.AcademicRankAddValidator;
import com.att.university.validator.academic_rank.AcademicRankUpdateValidator;
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
class AcademicRankServiceTest {
    @Mock
    private AcademicRankDao academicRankDao;

    @Mock
    private AcademicRankUpdateValidator updateValidator;

    @Mock
    private AcademicRankAddValidator addValidator;

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

        doNothing().when(updateValidator).validate(any(AcademicRankUpdateRequest.class));
        when(academicRankDao.findById(AcademicRank.getId())).thenReturn(Optional.empty());

        assertThrows(AcademicRankNotFoundException.class, () -> academicRankService.update(request));

        verify(updateValidator).validate(any(AcademicRankUpdateRequest.class));
        verify(academicRankDao).findById(anyInt());
        verifyNoMoreInteractions(academicRankDao, updateValidator);
    }

    @Test
    void updateAcademicRankShouldNotThrowExceptionIfAcademicRankIsFound() {
        final AcademicRankUpdateRequest request = new AcademicRankUpdateRequest(1, "update");
        final AcademicRank AcademicRank = generateAcademicRanks().get(0);

        doNothing().when(updateValidator).validate(any(AcademicRankUpdateRequest.class));
        when(academicRankDao.findById(AcademicRank.getId())).thenReturn(Optional.of(generateAcademicRanks().get(0)));
        when(updateRequestMapper.convertToEntity(any(AcademicRankUpdateRequest.class))).thenReturn((generateAcademicRanks().get(0)));

        academicRankService.update(request);

        verify(updateValidator).validate(any(AcademicRankUpdateRequest.class));
        verify(academicRankDao).findById(anyInt());
        verify(academicRankDao).update(any(AcademicRank.class));
        verifyNoMoreInteractions(academicRankDao, updateValidator);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(academicRankDao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> academicRankService.findAll());

        verify(academicRankDao).findAll(anyInt(), anyInt());
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfAcademicRankNotFound() {
        Integer id = 4;

        when(academicRankDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(AcademicRankNotFoundException.class, () -> academicRankService.findById(id));

        verify(academicRankDao).findById(anyInt());
        verifyNoMoreInteractions(academicRankDao);
    }

    @Test
    void findByIdShouldReturnEntityWhenAcademicRankExists() {
        Integer id = 4;

        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateAcademicRanks().get(0)));

        academicRankService.findById(id);

        verify(academicRankDao).findById(anyInt());
        verifyNoMoreInteractions(academicRankDao);
    }

    @Test
    void createAcademicRankShouldNotThrowException() {
        final AcademicRankAddRequest request = new AcademicRankAddRequest("new");
        doNothing().when(addValidator).validate(any(AcademicRankAddRequest.class));
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateAcademicRanks().get(0));

        academicRankService.create(request);

        verify(addValidator).validate(any(AcademicRankAddRequest.class));
        verify(academicRankDao).save(any(AcademicRank.class));
        verifyNoMoreInteractions(academicRankDao, addValidator);
    }

    @Test
    void deleteAcademicRankShouldThrowNotFoundExceptionIfAcademicRankNotFound() {
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(AcademicRankNotFoundException.class, () -> academicRankService.deleteById(1));

        verify(academicRankDao).findById(anyInt());
        verifyNoMoreInteractions(academicRankDao);
    }

    @Test
    void deleteAcademicRankShouldNotThrowExceptionIfAcademicRankIsFound() {
        when(academicRankDao.findById(anyInt())).thenReturn(Optional.of(generateAcademicRanks().get(0)));

        academicRankService.deleteById(1);

        verify(academicRankDao).findById(anyInt());
        verify(academicRankDao).deleteById(anyInt());
        verifyNoMoreInteractions(academicRankDao);
    }

    private List<AcademicRank> generateAcademicRanks() {
        return Arrays.asList(
                new AcademicRank(1, "rank"),
                new AcademicRank(2, "rank2"),
                new AcademicRank(3, "rank3")
        );
    }
}
