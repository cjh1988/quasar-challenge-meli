package com.meli.challenge.service;

import com.meli.challenge.model.Satellite;
import com.meli.challenge.dto.request.TopSecretRequestDTO;
import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.service.impl.ValidatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class ValidatorServiceImplTest {

    @Mock
    private SatelliteService satelliteService;

    @InjectMocks
    private ValidatorServiceImpl validatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTopSecretRequestValidation_ValidRequest() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(Satellite.builder()
                                        .name("kenobi")
                                        .distance(100.0)
                                        .message(List.of("este", "", "un", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("skywalker")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("sato")
                                .distance(300.0)
                                .message(List.of("este", "es", "un", ""))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));
        when(satelliteService.isSatellitesExists(anyList())).thenReturn(true);

        assertDoesNotThrow(() -> validatorService.topSecretRequestValidation(requestDTO));
    }

    @Test
    void testTopSecretRequestValidation_notEnoughSatellites() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(Satellite.builder()
                                        .name("kenobi")
                                        .distance(100.0)
                                        .message(List.of("este", "", "un", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("skywalker")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));

        var exception = assertThrows(FieldValidatorException.class, () -> validatorService.topSecretRequestValidation(requestDTO));

        assertEquals("Not enough information to process the message", exception.getMessage());
    }

    @Test
    void testTopSecretRequestValidation_satelliteNameNull() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(Satellite.builder()
                                        .name(null)
                                        .distance(100.0)
                                        .message(List.of("este", "", "un", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("skywalker")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("sato")
                                .distance(300.0)
                                .message(List.of("este", "es", "un", ""))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));

        var exception = assertThrows(FieldValidatorException.class, () -> validatorService.topSecretRequestValidation(requestDTO));

        assertEquals("The name of the satellites cannot be null or empty", exception.getMessage());
    }

    @Test
    void testTopSecretRequestValidation_SatelliteNotValid() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(Satellite.builder()
                                        .name("kenobi")
                                        .distance(100.0)
                                        .message(List.of("este", "", "un", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("r2-d2")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("sato")
                                .distance(300.0)
                                .message(List.of("este", "es", "un", ""))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));
        when(satelliteService.isSatellitesExists(anyList())).thenReturn(false);

        var exception = assertThrows(FieldValidatorException.class, () -> validatorService.topSecretRequestValidation(requestDTO));

        assertEquals("The name of the satellite/s is not valid", exception.getMessage());
    }

    @Test
    void testTopSecretRequestValidation_SatelliteDistanceZero() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(Satellite.builder()
                                        .name("kenobi")
                                        .distance(0.0)
                                        .message(List.of("este", "", "un", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("skywalker")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("sato")
                                .distance(300.0)
                                .message(List.of("este", "es", "un", ""))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));
        when(satelliteService.isSatellitesExists(anyList())).thenReturn(true);

        var exception = assertThrows(FieldValidatorException.class, () -> validatorService.topSecretRequestValidation(requestDTO));

        assertEquals("The distance of the satellites must be greater than 0", exception.getMessage());
    }

    @Test
    void testTopSecretRequestValidation_SatelliteMessageNull() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(Satellite.builder()
                                        .name("kenobi")
                                        .distance(100.0)
                                        .message(null)
                                .build(),
                        Satellite.builder()
                                .name("skywalker")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("sato")
                                .distance(300.0)
                                .message(List.of("este", "es", "un", ""))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));
        when(satelliteService.isSatellitesExists(anyList())).thenReturn(true);

        var exception = assertThrows(FieldValidatorException.class, () -> validatorService.topSecretRequestValidation(requestDTO));

        assertEquals("The message of the satellites cannot be null or empty", exception.getMessage());
    }

    @Test
    void testTopSecretRequestValidation_SatelliteMessageSizeZero() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(Satellite.builder()
                                        .name("kenobi")
                                        .distance(100.0)
                                        .message(List.of(""))
                                .build(),
                        Satellite.builder()
                                .name("skywalker")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("sato")
                                .distance(300.0)
                                .message(List.of("este", "es", "un", ""))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));
        when(satelliteService.isSatellitesExists(anyList())).thenReturn(true);

        var exception = assertThrows(FieldValidatorException.class, () -> validatorService.topSecretRequestValidation(requestDTO));

        assertEquals("The size of the message must be equal for all satellites", exception.getMessage());
    }

    @Test
    void testTopSecretRequestValidation_InvalidMessages() {
        var requestDTO = TopSecretRequestDTO
                .builder()
                .satellites(List.of(
                        Satellite.builder()
                                .name("kenobi")
                                .distance(100.0)
                                .message(List.of("este", "", "un", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("skywalker")
                                .distance(200.0)
                                .message(List.of("", "es", "", "mensaje"))
                                .build(),
                        Satellite.builder()
                                .name("sato")
                                .distance(300.0)
                                .message(List.of("este", "es", "un", "diferente"))
                                .build()))
                .build();
        when(satelliteService.getSatelliteNames()).thenReturn(List.of("kenobi", "skywalker", "sato"));
        when(satelliteService.isSatellitesExists(anyList())).thenReturn(true);

        var exception = assertThrows(FieldValidatorException.class, () -> validatorService.topSecretRequestValidation(requestDTO));

        assertEquals("Error processing messages", exception.getMessage());
    }

    @Test
    void testRequestMessageSplit_ValidSatellite() {
        var satelliteName = "kenobi";
        HashMap<String, Satellite> satelliteMap = new HashMap<>();
        satelliteMap.put("kenobi", Satellite.builder().name("kenobi").build());
        var satelliteNames = List.of("kenobi", "skywalker", "sato");

        when(satelliteService.isSatelliteExist(satelliteName)).thenReturn(true);
        when(satelliteService.getSatellitesMap()).thenReturn(satelliteMap);
        when(satelliteService.getSatelliteNames()).thenReturn(satelliteNames);

        var result = validatorService.requestMessageSplit(satelliteName);

        assertEquals(satelliteMap, result.getSatelliteHashMap());
        assertEquals(satelliteNames, result.getSatellitesNames());
    }

    @Test
    void testRequestMessageSplit_InvalidSatellite() {
        String satelliteName = "invalid";

        when(satelliteService.isSatelliteExist(satelliteName)).thenReturn(false);

        FieldValidatorException exception = assertThrows(FieldValidatorException.class,
                () -> validatorService.requestMessageSplit(satelliteName));

        assertEquals("Satellite with name invalid does not exist", exception.getMessage());
    }

}
