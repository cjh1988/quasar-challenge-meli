package com.meli.challenge.service;

import com.meli.challenge.model.Point;

import java.util.List;

public interface LocationService {

    Point getLocation(List<Double> distances);
}
