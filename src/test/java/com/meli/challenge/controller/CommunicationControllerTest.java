package com.meli.challenge.controller;

import com.meli.challenge.dto.request.TopSecretRequestDTO;
import com.meli.challenge.dto.request.TopSecretSplitRequestDTO;
import com.meli.challenge.dto.response.TopSecretResponseDTO;
import com.meli.challenge.service.CommunicationService;
import com.meli.challenge.service.SatelliteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class CommunicationControllerTest {

    @Mock
    private CommunicationService communicationService;

    @Mock
    private SatelliteService satelliteService;

    @InjectMocks
    private CommunicationController communicationController;

    public CommunicationControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessCommunication() {
        TopSecretRequestDTO request = new TopSecretRequestDTO();
        TopSecretResponseDTO response = new TopSecretResponseDTO();
        when(communicationService.processMessage(request)).thenReturn(response);

        ResponseEntity<TopSecretResponseDTO> result = communicationController.processCommunication(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(communicationService, times(1)).processMessage(request);
    }

    @Test
    void testUpdateSatelliteData() {
        String satelliteName = "sato";
        TopSecretSplitRequestDTO request = new TopSecretSplitRequestDTO();

        ResponseEntity<Void> result = communicationController.updateSatelliteData(satelliteName, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(satelliteService, times(1)).updateSatellite(satelliteName, request);
    }

    @Test
    void testProcessMessageSplit() {
        String satelliteName = "kenobi";
        TopSecretResponseDTO response = new TopSecretResponseDTO();
        when(communicationService.processMessageSplit(satelliteName)).thenReturn(response);

        ResponseEntity<TopSecretResponseDTO> result = communicationController.processMessageSplit(satelliteName);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(communicationService, times(1)).processMessageSplit(satelliteName);
    }
}