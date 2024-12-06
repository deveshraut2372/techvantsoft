package com.tmkcomputers.csms.utlity;

import static java.lang.Math.*;

public class DistanceCalculator {

    private static final double EARTH_RADIUS = 6371.0; // Radius of the Earth in kilometers

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);
        double a = sin(dLat / 2) * sin(dLat / 2) + cos(toRadians(lat1))
                * cos(toRadians(lat2)) * sin(dLon / 2) * sin(dLon / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public static double[] calculateMidpoint(double lat1, double lon1, double lat2, double lon2) {
        // Convert latitude and longitude from degrees to radians
        lat1 = toRadians(lat1);
        lon1 = toRadians(lon1);
        lat2 = toRadians(lat2);
        lon2 = toRadians(lon2);

        // Calculate the midpoint using spherical trigonometry
        double dLon = lon2 - lon1;

        double Bx = cos(lat2) * cos(dLon);
        double By = cos(lat2) * sin(dLon);

        double midLat = atan2(sin(lat1) + sin(lat2), sqrt((cos(lat1) + Bx) * (cos(lat1) + Bx) + By * By));
        double midLon = lon1 + atan2(By, cos(lat1) + Bx);

        // Convert the midpoint from radians back to degrees
        return new double[] { toDegrees(midLat), toDegrees(midLon) };
    }
}
