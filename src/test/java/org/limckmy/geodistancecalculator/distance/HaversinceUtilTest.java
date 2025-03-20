package org.limckmy.geodistancecalculator.distance;

import org.junit.Test;

import static org.junit.Assert.*;

public class HaversinceUtilTest {
    @Test
    public void testCalculateDistance_validCoordinates() {
        // Test with two well-known locations:
        // (New York, USA) and (London, UK)

        double latitude1 = 40.7128; // New York
        double longitude1 = -74.0060;
        double latitude2 = 51.5074; // London
        double longitude2 = -0.1278;

        // Expected distance based on known geodesic distance between New York and London (in km)
        double expectedDistance = 5570.0; // Approximate value

        double actualDistance = HaversinceUtil.calculateDistance(latitude1, longitude1, latitude2, longitude2);

        // Assert the actual distance with a tolerance of 1 km
        assertEquals(expectedDistance, actualDistance, 1.0);
    }

    @Test
    public void testCalculateDistance_sameCoordinates() {
        // Test with same coordinates (distance should be 0)
        double latitude1 = 40.7128;
        double longitude1 = -74.0060;
        double latitude2 = 40.7128;
        double longitude2 = -74.0060;

        double expectedDistance = 0.0;
        double actualDistance = HaversinceUtil.calculateDistance(latitude1, longitude1, latitude2, longitude2);

        // Assert that the distance is 0
        assertEquals(expectedDistance, actualDistance, 0.0);
    }

    @Test
    public void testCalculateDistance_edgeCase_poles() {
        // Test with one point at the North Pole and another at the South Pole
        double latitude1 = 90.0;  // North Pole
        double longitude1 = 0.0;
        double latitude2 = -90.0; // South Pole
        double longitude2 = 0.0;

        // Expected distance between poles, approximately 20000 km
        double expectedDistance = 20015.1; // Half the Earth's circumference

        double actualDistance = HaversinceUtil.calculateDistance(latitude1, longitude1, latitude2, longitude2);

        // Assert that the distance is very close to 20015.1 km
        assertEquals(expectedDistance, actualDistance, 1.0);
    }

    @Test
    public void testCalculateDistance_veryClosePoints() {
        // Test with two very close points, distance should be very small
        double latitude1 = 40.7128; // New York
        double longitude1 = -74.0060;
        double latitude2 = 40.7129; // Just slightly north of New York
        double longitude2 = -74.0061;

        // Expected distance should be small (a few meters)
        double expectedDistance = 0.014; // Approximate value in kilometers (14 meters)

        double actualDistance = HaversinceUtil.calculateDistance(latitude1, longitude1, latitude2, longitude2);

        // Assert that the distance is close to the expected value
        assertEquals(expectedDistance, actualDistance, 0.001); // Small tolerance for small distances
    }

    @Test
    public void testCalculateDistance_largeCoordinates() {
        // Test with coordinates near the extreme ends (near 90 or -90 latitude, 180 or -180 longitude)

        double latitude1 = 89.0;
        double longitude1 = 179.0;
        double latitude2 = -89.0;
        double longitude2 = -179.0;

        // This would be approximately the longest distance possible on Earth (almost opposite sides of the globe)
        double expectedDistance = 19792.7; // Approximate value (half of Earth's circumference)

        double actualDistance = HaversinceUtil.calculateDistance(latitude1, longitude1, latitude2, longitude2);

        // Assert that the distance is approximately correct
        assertEquals(expectedDistance, actualDistance, 1.0); // Tolerance of 1 km
    }
}