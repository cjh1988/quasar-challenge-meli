package com.meli.challenge.utils;

import java.util.Locale;

public class DoubleUtil {

    public static double formatTwoDecimals(double value) {
        return Double.parseDouble(String.format(Locale.US,"%.2f", value));
    }
}
