package com.meli.challenge.utils;


import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.HashMap;
import java.util.List;

public class QuasarUtil {


    public static HashMap<String, String> buildSatelliteMap(List<String> satelliteNames, List<String> satellitePositions) {
        HashMap<String, String> satelliteMap = new HashMap<>();
        for (int i = 0; i < satelliteNames.size(); i++) {
            satelliteMap.put(satelliteNames.get(i), satellitePositions.get(i));
        }
        return satelliteMap;
    }

    public static double[] trilateration(double[][] position, double[] distances) {

        TrilaterationFunction trilaterationFunction = new TrilaterationFunction(position, distances);
        NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(trilaterationFunction, new LevenbergMarquardtOptimizer());
        return nSolver.solve().getPoint().toArray();

    }

}
