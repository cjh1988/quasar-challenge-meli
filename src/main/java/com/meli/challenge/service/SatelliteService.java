package com.meli.challenge.service;

import com.meli.challenge.model.Satellite;
import com.meli.challenge.dto.request.TopSecretSplitRequestDTO;

import java.util.HashMap;
import java.util.List;


public interface SatelliteService {

    void updateSatellite(String nanme, TopSecretSplitRequestDTO requestDTO);

    HashMap<String, Satellite> getSatellitesMap();

    Boolean isSatelliteExist(String name);

    Boolean isSatellitesExists(List<String> receivedNames);

    List<String> getSatelliteNames();

    void addSatellitesMap(List<Satellite> satelliteReferenceList);

}
