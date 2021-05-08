package com.att.university.service;

import com.att.university.dao.BuildingDao;
import com.att.university.entity.Building;
import com.att.university.exception.dao.BuildingNotFoundException;
import com.att.university.mapper.building.BuildingAddRequestMapper;
import com.att.university.mapper.building.BuildingUpdateRequestMapper;
import com.att.university.request.building.BuildingAddRequest;
import com.att.university.request.building.BuildingUpdateRequest;
import com.att.university.service.impl.BuildingServiceImpl;
import com.att.university.validator.building.BuildingAddValidator;
import com.att.university.validator.building.BuildingUpdateValidator;
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
class BuildingServiceTest {
    @Mock
    private BuildingDao buildingDao;

    @Mock
    private BuildingUpdateValidator updateValidator;

    @Mock
    private BuildingAddValidator addValidator;

    @Mock
    private BuildingAddRequestMapper addRequestMapper;

    @Mock
    private BuildingUpdateRequestMapper updateRequestMapper;

    @InjectMocks
    private BuildingServiceImpl buildingService;

    @Test
    void updateBuildingShouldThrowNotFoundExceptionIfBuildingNotFound() {
        final BuildingUpdateRequest request = new BuildingUpdateRequest(1, "update");
        final Building Building = generateBuildings().get(0);

        doNothing().when(updateValidator).validate(any(BuildingUpdateRequest.class));
        when(buildingDao.findById(Building.getId())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> buildingService.update(request));

        verify(updateValidator).validate(any(BuildingUpdateRequest.class));
        verify(buildingDao).findById(anyInt());
        verifyNoMoreInteractions(buildingDao, updateValidator);
    }

    @Test
    void updateBuildingShouldNotThrowExceptionIfBuildingIsFound() {
        final BuildingUpdateRequest request = new BuildingUpdateRequest(1, "update");
        final Building Building = generateBuildings().get(0);

        doNothing().when(updateValidator).validate(any(BuildingUpdateRequest.class));
        when(buildingDao.findById(Building.getId())).thenReturn(Optional.of(generateBuildings().get(0)));
        when(updateRequestMapper.convertToEntity(any(BuildingUpdateRequest.class))).thenReturn((generateBuildings().get(0)));

        buildingService.update(request);

        verify(updateValidator).validate(any(BuildingUpdateRequest.class));
        verify(buildingDao).findById(anyInt());
        verify(buildingDao).update(any(Building.class));
        verifyNoMoreInteractions(buildingDao, updateValidator);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(buildingDao.findAll(anyInt(), anyInt())).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> buildingService.findAll());

        verify(buildingDao).findAll(anyInt(), anyInt());
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfBuildingNotFound() {
        Integer id = 4;

        when(buildingDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> buildingService.findById(id));

        verify(buildingDao).findById(anyInt());
        verifyNoMoreInteractions(buildingDao);
    }

    @Test
    void findByIdShouldReturnEntityWhenBuildingExists() {
        Integer id = 4;

        when(buildingDao.findById(anyInt())).thenReturn(Optional.of(generateBuildings().get(0)));

        buildingService.findById(id);

        verify(buildingDao).findById(anyInt());
        verifyNoMoreInteractions(buildingDao);
    }

    @Test
    void createBuildingShouldNotThrowException() {
        final BuildingAddRequest request = new BuildingAddRequest("new");
        doNothing().when(addValidator).validate(any(BuildingAddRequest.class));
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateBuildings().get(0));

        buildingService.create(request);

        verify(addValidator).validate(any(BuildingAddRequest.class));
        verify(buildingDao).save(any(Building.class));
        verifyNoMoreInteractions(buildingDao, addValidator);
    }

    @Test
    void deleteBuildingShouldThrowNotFoundExceptionIfBuildingNotFound() {
        when(buildingDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> buildingService.deleteById(1));

        verify(buildingDao).findById(anyInt());
        verifyNoMoreInteractions(buildingDao);
    }

    @Test
    void deleteBuildingShouldNotThrowExceptionIfBuildingIsFound() {
        when(buildingDao.findById(anyInt())).thenReturn(Optional.of(generateBuildings().get(0)));

        buildingService.deleteById(1);

        verify(buildingDao).findById(anyInt());
        verify(buildingDao).deleteById(anyInt());
        verifyNoMoreInteractions(buildingDao);
    }

    private List<Building> generateBuildings() {
        return Arrays.asList(
                new Building(1, "Building1"),
                new Building(2, "Building2"),
                new Building(3, "Building3")
        );
    }
}
