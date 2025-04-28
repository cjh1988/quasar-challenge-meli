package com.meli.challenge.service;

import com.meli.challenge.dto.SatelliteDTO;
import com.meli.challenge.dto.request.TopSecretSplitRequestDTO;
import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.repository.SatelliteRepository;
import com.meli.challenge.service.impl.SatelliteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;

class SatelliteServiceImplTest {

    @Mock
    private SatelliteRepository repository;

    @InjectMocks
    private SatelliteServiceImpl satelliteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateSatellite_Success() {
        String name = "kenobi";
        TopSecretSplitRequestDTO requestDTO = new TopSecretSplitRequestDTO();
        requestDTO.setDistance(100.0);
        requestDTO.setMessage(List.of("this", "is", "a", "test"));

        when(repository.isSatelliteExist(List.of(name))).thenReturn(true);

        satelliteService.updateSatellite(name, requestDTO);

        verify(repository, times(1)).updateSatellite(any(SatelliteDTO.class));
    }

    @Test
    void testUpdateSatellite_NotFound() {
        String name = "unknown";
        TopSecretSplitRequestDTO requestDTO = new TopSecretSplitRequestDTO();

        when(repository.isSatelliteExist(List.of(name))).thenReturn(false);

        FieldValidatorException exception = assertThrows(FieldValidatorException.class, () -> {
            satelliteService.updateSatellite(name, requestDTO);
        });

        assertEquals("Satellite with name unknown does not exist", exception.getMessage());
        verify(repository, never()).updateSatellite(any(SatelliteDTO.class));
    }

    @Test
    void testGetSatellitesMap() {
        HashMap<String, SatelliteDTO> mockMap = new HashMap<>();
        when(repository.getSatellitesMap()).thenReturn(mockMap);

        HashMap<String, SatelliteDTO> result = satelliteService.getSatellitesMap();

        assertEquals(mockMap, result);
        verify(repository, times(1)).getSatellitesMap();
    }

    @Test
    void testIsSatelliteExist() {
        String name = "kenobi";
        when(repository.isSatelliteExist(List.of(name))).thenReturn(true);

        Boolean result = satelliteService.isSatelliteExist(name);

        assertTrue(result);
        verify(repository, times(1)).isSatelliteExist(List.of(name));
    }

    @Test
    void testIsSatellitesExists() {
        List<String> names = List.of("kenobi", "skywalker");
        when(repository.isSatelliteExist(names)).thenReturn(true);

        Boolean result = satelliteService.isSatellitesExists(names);

        assertTrue(result);
        verify(repository, times(1)).isSatelliteExist(names);
    }

    @Test
    void testGetSatelliteNames() {
        List<String> mockNames = List.of("kenobi", "skywalker", "sato");
        when(repository.getSatelliteNames()).thenReturn(mockNames);

        List<String> result = satelliteService.getSatelliteNames();

        assertEquals(mockNames, result);
        verify(repository, times(1)).getSatelliteNames();
    }
}
