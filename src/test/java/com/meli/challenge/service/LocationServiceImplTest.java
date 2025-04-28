package com.meli.challenge.service;

import com.meli.challenge.repository.SatelliteRepository;
import com.meli.challenge.service.impl.LocationServiceImpl;
import com.meli.challenge.utils.DoubleUtil;
import com.meli.challenge.utils.QuasarUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LocationServiceImplTest {


    @Mock
    private SatelliteRepository satelliteRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLocation_Success() {

        List<Double> distances = List.of(100.0, 115.5, 142.7);

        double[] expectedPosition = {-58.32, -69.55};

        var satelliteReferenceMap = new HashMap<String, String>();
        satelliteReferenceMap.put("kenobi", "-500,-200");
        satelliteReferenceMap.put("skywalker", "100,-100");
        satelliteReferenceMap.put("sato", "500,100");


        when(satelliteRepository.getSatelliteReferenceMap()).thenReturn(satelliteReferenceMap);
        when(satelliteRepository.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));
        mockStatic(QuasarUtil.class);
        when(QuasarUtil.trilateration(any(), any()))
                .thenReturn(expectedPosition);


        var result = locationService.getLocation(distances);

        assertEquals(DoubleUtil.formatTwoDecimals(expectedPosition[0]), result.getX());
        assertEquals(DoubleUtil.formatTwoDecimals(expectedPosition[1]), result.getY());
        verify(satelliteRepository, times(1)).getSatelliteReferenceMap();
        verify(satelliteRepository, times(1)).getSatelliteNames();
    }

}
