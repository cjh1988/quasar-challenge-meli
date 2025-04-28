package com.meli.challenge.service.impl;

import com.meli.challenge.model.Satellite;
import com.meli.challenge.dto.request.TopSecretSplitRequestDTO;
import com.meli.challenge.exception.FieldValidatorException;
import com.meli.challenge.repository.SatelliteRepository;
import com.meli.challenge.service.SatelliteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class SatelliteServiceImpl implements SatelliteService {

    private final SatelliteRepository repository;

    @Override
    public void updateSatellite(String name, TopSecretSplitRequestDTO requestDTO) {

        if (Boolean.FALSE.equals(repository.isSatelliteExist(List.of(name)))) {
            log.error("Satellite with name {} does not exist", name);
            throw new FieldValidatorException("Satellite with name " + name + " does not exist", HttpStatus.NOT_FOUND);
        }
        repository.updateSatellite(Satellite.builder()
                .name(name)
                .distance(requestDTO.getDistance())
                .message(requestDTO.getMessage())
                .build());
    }

    @Override
    public HashMap<String, Satellite> getSatellitesMap() {
        return repository.getSatellitesMap();
    }

    @Override
    public Boolean isSatelliteExist(String name) {
        return repository.isSatelliteExist(List.of(name));
    }

    @Override
    public Boolean isSatellitesExists(List<String> receivedNames) {
        return repository.isSatelliteExist(receivedNames);
    }

    @Override
    public List<String> getSatelliteNames() {
        return repository.getSatelliteNames();
    }

    @Override
    public void addSatellitesMap(List<Satellite> satelliteDTOList) {

        repository.addSatellitesReferenceMap(satelliteDTOList);
    }
}
