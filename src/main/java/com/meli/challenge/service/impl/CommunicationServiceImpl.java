package com.meli.challenge.service.impl;

import com.meli.challenge.model.Satellite;
import com.meli.challenge.dto.request.TopSecretRequestDTO;
import com.meli.challenge.dto.response.TopSecretResponseDTO;
import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.dto.PointDTO;
import com.meli.challenge.service.CommunicationService;
import com.meli.challenge.service.LocationService;
import com.meli.challenge.service.MessageService;
import com.meli.challenge.service.SatelliteService;
import com.meli.challenge.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunicationServiceImpl implements CommunicationService {

    private final LocationService locationService;
    private final MessageService messageService;
    private final ValidatorService validatorService;
    private final SatelliteService satelliteService;

    @Override
    public TopSecretResponseDTO processMessage(TopSecretRequestDTO request) {
        log.info("TopSecretRequestDTO: {}", request);

        // Validate the request
        validatorService.topSecretRequestValidation(request);

        var distances = request.getSatellites().stream()
                .map(Satellite::getDistance)
                .toList();

        var messages = request.getSatellites().stream()
                .map(Satellite::getMessage)
                .toList();

        //calculate location
        var point = new PointDTO();
        try {
            point = locationService.getLocation(distances);
        } catch (Exception exception) {
            log.error("Error calculating the location");
            throw new FieldValidatorException("Error calculating the location", HttpStatus.NOT_FOUND);
        }

        //Building the message
        var message = "";
        try {
            message = messageService.getMessage(messages);
        } catch (Exception exception) {
            log.error("Error trying to build the message");
            throw new FieldValidatorException("Error trying to build the message", HttpStatus.NOT_FOUND);
        }

        //saving satellites reference map
        satelliteService.addSatellitesMap(request.getSatellites());

        return TopSecretResponseDTO.builder()
                .position(point)
                .message(message)
                .build();
    }

    @Override
    public TopSecretResponseDTO processMessageSplit(String name) {

        var params = validatorService.requestMessageSplit(name);

        var satellites = new ArrayList<Satellite>();
        if (params.getSatelliteHashMap().size() == params.getSatellitesNames().size()) {
            for (String satelliteName : params.getSatellitesNames()) {
                satellites.add(params.getSatelliteHashMap().get(satelliteName));
            }
        }
        return processMessage(TopSecretRequestDTO.builder().satellites(satellites).build());
    }
}
