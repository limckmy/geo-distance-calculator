package org.limckmy.geodistancecalculator.distance;

public record DistanceResponse(String postcode1, double latitude1, double longitude1,
                               String postcode2, double latitude2, double longitude2,
                               double distance) {
}
