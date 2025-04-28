package com.meli.challenge.repository;

import com.meli.challenge.dto.SatelliteDTO;
import com.meli.challenge.utils.QuasarUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.List;

@Component
public class SatelliteRepository {

    @Value("${satellite.names}")
    private String satelliteNames;

    @Value("${satellite.positions}")
    private String satellitePositions;

    private final HashMap<String, SatelliteDTO> satelliteMap = new HashMap<>();

    public Boolean isSatelliteExist(List<String> receivedNames) {
        var satelliteMap = QuasarUtil.buildSatelliteMap(Arrays.asList(satelliteNames.split(",")), Arrays.asList(satellitePositions.split(";")));
        return receivedNames.stream().allMatch(satelliteMap::containsKey);
    }

    public HashMap<String, String> getSatelliteReferenceMap() {
        return QuasarUtil.buildSatelliteMap(Arrays.asList(satelliteNames.split(",")), Arrays.asList(satellitePositions.split(";")));
    }

    public List<String> getSatelliteNames() {
        return Arrays.asList(satelliteNames.split(","));
    }

    public void updateSatellite(SatelliteDTO satelliteDTO) {
        satelliteMap.put(satelliteDTO.getName(), satelliteDTO);
    }

    public HashMap<String, SatelliteDTO> getSatellitesMap() {
        return satelliteMap;
    }

    public void addSatellitesReferenceMap(List<SatelliteDTO> satellitesMap) {
        for ( SatelliteDTO satelliteDTO : satellitesMap) {
            satelliteMap.put(satelliteDTO.getName(), satelliteDTO);
        }
    }

}
