package org.limckmy.geodistancecalculator.distance;

public class HaversinceUtil {

    private static final double EARTH_RADIUS = 6371.0; // Radius in kilometers

    public static double calculateDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        // Convert degrees to radians
        double lon1Radians = Math.toRadians(longitude1);
        double lon2Radians = Math.toRadians(longitude2);
        double lat1Radians = Math.toRadians(latitude1);
        double lat2Radians = Math.toRadians(latitude2);

        double a = haversine(lat1Radians, lat2Radians)
                + Math.cos(lat1Radians) * Math.cos(lat2Radians)
                * haversine(lon1Radians, lon2Radians);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 -a));

        return (EARTH_RADIUS * c);
    }

    private static double haversine(double deg1, double deg2) {
        return Math.pow(Math.sin((deg1 - deg2) / 2.0), 2);
    }
}
