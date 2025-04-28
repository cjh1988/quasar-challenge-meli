package com.meli.challenge.service;

import com.meli.challenge.dto.PointDTO;

import java.util.List;

public interface LocationService {

    PointDTO getLocation(List<Double> distances);
}
