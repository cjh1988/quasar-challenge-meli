package com.meli.challenge.service;

import com.meli.challenge.dto.ParamsDTO;
import com.meli.challenge.dto.SatelliteDTO;
import com.meli.challenge.dto.request.TopSecretRequestDTO;
import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.model.Point;
import com.meli.challenge.service.impl.CommunicationServiceImpl;
import com.meli.challenge.service.impl.LocationServiceImpl;
import com.meli.challenge.service.impl.ValidatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;

class CommunicationServiceImplTest {

    @Mock
    private LocationServiceImpl locationService;

    @Mock
    private MessageService messageService;

    @Mock
    private ValidatorServiceImpl validatorService;

    @Mock
    private SatelliteService satelliteService;

    @InjectMocks
    private CommunicationServiceImpl communicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessMessage() {

        var request = TopSecretRequestDTO.builder()
                .satellites(List.of(SatelliteDTO.builder()
                        .name("kenobi")
                        .distance(100.0)
                        .message(List.of("este", "", "un", "mensaje"))
                        .build(), SatelliteDTO.builder()
                        .name("skywalker")
                        .distance(115.5)
                        .message(List.of("", "es", "", ""))
                        .build(), SatelliteDTO.builder()
                        .name("sato")
                        .distance(142.7)
                        .message(List.of("este", "", "", "mensaje"))
                        .build()))
                .build();

        var expectedPosition = new Point(-58.32, -69.55);
        var expectedMessage = "este es un mensaje";

        when(locationService.getLocation(anyList())).thenReturn(expectedPosition);
        when(messageService.getMessage(anyList())).thenReturn(expectedMessage);

        var response = communicationService.processMessage(request);

        assertEquals(expectedPosition, response.getPosition());
        assertEquals(expectedMessage, response.getMessage());
        verify(validatorService, times(1)).topSecretRequestValidation(request);
        verify(locationService, times(1)).getLocation(anyList());
        verify(messageService, times(1)).getMessage(anyList());
    }

    @Test
    void testProcessMessage_ErrorLocation() {
        var request = TopSecretRequestDTO.builder()
                .satellites(List.of(SatelliteDTO.builder()
                        .name("kenobi")
                        .distance(100.0)
                        .message(List.of("este", "", "un", "mensaje"))
                        .build(), SatelliteDTO.builder()
                        .name("skywalker")
                        .distance(115.5)
                        .message(List.of("", "es", "", ""))
                        .build(), SatelliteDTO.builder()
                        .name("sato")
                        .distance(142.7)
                        .message(List.of("este", "", "", "mensaje"))
                        .build()))
                .build();

        when(locationService.getLocation(anyList())).thenThrow(new RuntimeException("Error calculating location"));

        var exception = assertThrows(FieldValidatorException.class, () -> communicationService.processMessage(request));

        assertEquals("Error calculating the location", exception.getMessage());
    }

    @Test
    void testProcessMessage_ErrorMessage() {
        var request = TopSecretRequestDTO.builder()
                .satellites(List.of(SatelliteDTO.builder()
                        .name("kenobi")
                        .distance(100.0)
                        .message(List.of("este", "", "un", "mensaje"))
                        .build(), SatelliteDTO.builder()
                        .name("skywalker")
                        .distance(115.5)
                        .message(List.of("", "es", "", ""))
                        .build(), SatelliteDTO.builder()
                        .name("sato")
                        .distance(142.7)
                        .message(List.of("este", "", "", "mensaje"))
                        .build()))
                .build();

        when(messageService.getMessage(anyList())).thenThrow(new RuntimeException("Error building message"));

        var exception = assertThrows(FieldValidatorException.class, () -> communicationService.processMessage(request));

        assertEquals("Error trying to build the message", exception.getMessage());
    }

    @Test
    void testProcessMessageSplit() {

        var satelliteMap = new HashMap<String, SatelliteDTO>();

        satelliteMap.put("kenobi", SatelliteDTO.builder()
                .name("kenobi")
                .distance(100.0)
                .message(List.of("este", "", "un", "mensaje"))
                .build());
        satelliteMap.put("skywalker", SatelliteDTO.builder()
                .name("skywalker")
                .distance(115.5)
                .message(List.of("", "es", "", ""))
                .build());
        satelliteMap.put("sato", SatelliteDTO.builder()
                .name("sato")
                .distance(142.7)
                .message(List.of("este", "", "", "mensaje"))
                .build());


        var satelliteNames = List.of("kenobi", "skywalker", "sato");
        var params = ParamsDTO.builder()
                .satelliteHashMap(satelliteMap)
                .satellitesNames(satelliteNames)
                .build();

        when(validatorService.requestMessageSplit(any())).thenReturn(params);

        var expectedPosition = new Point(-58.32, -69.55);
        var expectedMessage = "este es un mensaje";

        when(locationService.getLocation(anyList())).thenReturn(expectedPosition);
        when(messageService.getMessage(anyList())).thenReturn(expectedMessage);

        var response = communicationService.processMessageSplit("kenobi");

        assertEquals(expectedPosition, response.getPosition());
        assertEquals(expectedMessage, response.getMessage());
        verify(validatorService, times(1)).requestMessageSplit(any());
        verify(locationService, times(1)).getLocation(anyList());
        verify(messageService, times(1)).getMessage(anyList());
    }
}
