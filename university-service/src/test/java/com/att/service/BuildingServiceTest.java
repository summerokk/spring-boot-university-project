package com.att.service;

import com.att.dao.BuildingRepository;
import com.att.entity.Building;
import com.att.exception.dao.BuildingNotFoundException;
import com.att.mapper.building.BuildingAddRequestMapper;
import com.att.mapper.building.BuildingUpdateRequestMapper;
import com.att.request.building.BuildingAddRequest;
import com.att.request.building.BuildingUpdateRequest;
import com.att.service.impl.BuildingServiceImpl;
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
class BuildingServiceTest {
    @Mock
    private BuildingRepository buildingRepository;

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

        when(buildingRepository.findById(Building.getId())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> buildingService.update(request));

        verify(buildingRepository).findById(anyInt());
        verifyNoMoreInteractions(buildingRepository);
    }

    @Test
    void updateBuildingShouldNotThrowExceptionIfBuildingIsFound() {
        final BuildingUpdateRequest request = new BuildingUpdateRequest(1, "update");
        final Building Building = generateBuildings().get(0);

        when(buildingRepository.findById(Building.getId())).thenReturn(Optional.of(generateBuildings().get(0)));
        when(updateRequestMapper.convertToEntity(any(BuildingUpdateRequest.class))).thenReturn((generateBuildings().get(0)));

        buildingService.update(request);

        verify(buildingRepository).findById(anyInt());
        verify(buildingRepository).save(any(Building.class));
        verifyNoMoreInteractions(buildingRepository);
    }

    @Test
    void findAllShouldNotThrowException() {
        when(buildingRepository.findAll()).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> buildingService.findAll());

        verify(buildingRepository).findAll();
    }


    @Test
    void findByIdSShouldThrowNotFoundExceptionIfBuildingNotFound() {
        Integer id = 4;

        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> buildingService.findById(id));

        verify(buildingRepository).findById(anyInt());
        verifyNoMoreInteractions(buildingRepository);
    }

    @Test
    void findByIdShouldReturnEntityWhenBuildingExists() {
        Integer id = 4;

        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(generateBuildings().get(0)));

        buildingService.findById(id);

        verify(buildingRepository).findById(anyInt());
        verifyNoMoreInteractions(buildingRepository);
    }

    @Test
    void createBuildingShouldNotThrowException() {
        final BuildingAddRequest request = new BuildingAddRequest("new");
        when(addRequestMapper.convertToEntity(request)).thenReturn(generateBuildings().get(0));

        buildingService.create(request);

        verify(buildingRepository).save(any(Building.class));
        verifyNoMoreInteractions(buildingRepository);
    }

    @Test
    void deleteBuildingShouldThrowNotFoundExceptionIfBuildingNotFound() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(BuildingNotFoundException.class, () -> buildingService.deleteById(1));

        verify(buildingRepository).findById(anyInt());
        verifyNoMoreInteractions(buildingRepository);
    }

    @Test
    void deleteBuildingShouldNotThrowExceptionIfBuildingIsFound() {
        when(buildingRepository.findById(anyInt())).thenReturn(Optional.of(generateBuildings().get(0)));

        buildingService.deleteById(1);

        verify(buildingRepository).findById(anyInt());
        verify(buildingRepository).deleteById(anyInt());
        verifyNoMoreInteractions(buildingRepository);
    }

    private List<Building> generateBuildings() {
        return Arrays.asList(
                new Building(1, "Building1"),
                new Building(2, "Building2"),
                new Building(3, "Building3")
        );
    }
}
