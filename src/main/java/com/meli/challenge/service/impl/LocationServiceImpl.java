package com.meli.challenge.service.impl;

import com.meli.challenge.model.Point;
import com.meli.challenge.repository.SatelliteRepository;
import com.meli.challenge.service.LocationService;
import com.meli.challenge.utils.DoubleUtil;
import com.meli.challenge.utils.QuasarUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final SatelliteRepository satelliteRepository;

    @Override
    public Point getLocation(List<Double> distances) {
        log.info("::: Starting the getLocationProcess :::");
        double[] distancesArray = distances.stream().mapToDouble(Double::doubleValue).toArray();

        double[] positionArray = QuasarUtil.trilateration(getPositions(), distancesArray);
        log.info("::: Finishing the getLocationProcess :::");
        return Point.builder()
                .x(DoubleUtil.formatTwoDecimals(positionArray[0]))
                .y(DoubleUtil.formatTwoDecimals(positionArray[1]))
                .build();
    }


    private double[][] getPositions() {
        var satellites = satelliteRepository.getSatelliteReferenceMap();
        var satelliteNames = satelliteRepository.getSatelliteNames();

        var positions = new double[satellites.size()][];
        for (var i = 0; i < satellites.size(); i++) {
            String name = satelliteNames.get(i);
            String position = satellites.get(name);
            String[] coordinates = position.split(",");
            positions[i] = new double[]{Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1])};
        }
        return positions;
    }
}
