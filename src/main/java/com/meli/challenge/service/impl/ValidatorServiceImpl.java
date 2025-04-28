package com.meli.challenge.service.impl;

import com.meli.challenge.dto.ParamsDTO;
import com.meli.challenge.dto.SatelliteDTO;
import com.meli.challenge.dto.request.TopSecretRequestDTO;
import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.service.SatelliteService;
import com.meli.challenge.service.ValidatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValidatorServiceImpl implements ValidatorService {

    private final SatelliteService satelliteService;

    @Override
    public void topSecretRequestValidation(TopSecretRequestDTO requestDTO) {
        log.info("::: Initializing the validation of the request :::");
        if (requestDTO.getSatellites().size() != satelliteService.getSatelliteNames().size()) {
            log.error("Not enough information to process the message");
            throw new FieldValidatorException("Not enough information to process the message", HttpStatus.NOT_FOUND);
        }

        if (requestDTO.getSatellites().stream().anyMatch(satellite -> satellite.getName() == null || satellite.getName().isEmpty())) {
            log.error("The name of the satellites cannot be null or empty");
            throw new FieldValidatorException("The name of the satellites cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        var receivedNames = requestDTO.getSatellites().stream()
                .map(SatelliteDTO::getName)
                .toList();

        // Check if the names of the satellites are valid
        if (Boolean.FALSE.equals(satelliteService.isSatellitesExists(receivedNames))) {
            log.error("The name of the satellite/s doesn't exist");
            throw new FieldValidatorException("The name of the satellite/s is not valid", HttpStatus.NOT_FOUND);
        }

        if (requestDTO.getSatellites().stream().anyMatch(satellite -> satellite.getDistance() <= 0)) {
            log.error("The distance of the satellites must be greater than 0");
            throw new FieldValidatorException("The distance of the satellites must be greater than 0", HttpStatus.NOT_FOUND);
        }

        if (requestDTO.getSatellites().stream().anyMatch(satellite -> satellite.getMessage() == null || satellite.getMessage().isEmpty())) {
            log.error("The message of the satellites cannot be null or empty");
            throw new FieldValidatorException("The message of the satellites cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        int messageSize = requestDTO.getSatellites().get(0).getMessage().size();
        if (messageSize == 0 || requestDTO.getSatellites().stream().anyMatch(satellite -> satellite.getMessage().size() != messageSize)) {
            log.error("The size of the message must be equal for all satellites");
            throw new FieldValidatorException("The size of the message must be equal for all satellites", HttpStatus.BAD_REQUEST);
        }
        var messages = requestDTO.getSatellites().stream()
                .map(SatelliteDTO::getMessage)
                .toList();

        if (Boolean.FALSE.equals(areMessagesValid(messages))) {
            log.error("The messages are not valid");
            throw new FieldValidatorException("Error processing messages", HttpStatus.NOT_FOUND);
        }

        log.info("::: Finishing the validation of the request :::");
    }

    @Override
    public ParamsDTO requestMessageSplit(String name) {
        log.info("::: Initializing the validation of the requestMessageSplit validation :::");
        if (Boolean.FALSE.equals(satelliteService.isSatelliteExist(name))) {
            log.error("Satellite with name {} does not exist", name);
            throw new FieldValidatorException("Satellite with name " + name + " does not exist", HttpStatus.NOT_FOUND);
        }
        log.info("::: Finishing the validation of the requestMessageSplit validation :::");
        return ParamsDTO.builder()
                .satelliteHashMap(satelliteService.getSatellitesMap())
                .satellitesNames(satelliteService.getSatelliteNames())
                .build();

    }

    private Boolean areMessagesValid(List<List<String>> messages) {

        var matrix = new String[messages.size()][messages.get(0).size()];

        for (int i = 0; i < messages.size(); i++) {
            for (int j = 0; j < messages.get(i).size(); j++) {
                matrix[i][j] = messages.get(i).get(j);
            }
        }

        List<List<String>> columnElements = new ArrayList<>();

        for (int j = 0; j < matrix[0].length; j++) {
            List<String> column = new ArrayList<>();
            for (int i = 0; i < matrix.length; i++) {
                column.add(matrix[i][j]);
            }
            columnElements.add(column);
        }
        var valid = false;
        for (List<String> columns : columnElements) {
            columns.removeIf(String::isEmpty);
            if (Boolean.FALSE.equals(columns.stream().distinct().count() == 1))
                return false;
            else
                valid = true;
        }
        return valid;
    }
}
