package org.limckmy.geodistancecalculator.distance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.limckmy.geodistancecalculator.postcode.Postcode;
import org.limckmy.geodistancecalculator.postcode.PostcodeService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DistanceServiceTest {

    @InjectMocks
    private DistanceService distanceService;  // Service to be tested

    @Mock
    private PostcodeService postcodeService;  // Mocked PostcodeService dependency

    @Mock
    private Postcode postcode1;  // Mocked Postcode for postcode1
    @Mock
    private Postcode postcode2;  // Mocked Postcode for postcode2

    @Before
    public void setUp() {
        // Initialize the mock objects if needed
    }

    @Test
    public void testCalculateDistance_success() {
        // Setup mock behavior for findByPostcode
        when(postcodeService.findByPostcode("12345")).thenReturn(postcode1);
        when(postcodeService.findByPostcode("67890")).thenReturn(postcode2);

        // Setup mock Postcode data (latitude and longitude)
        when(postcode1.getLatitude()).thenReturn(51.501008);
        when(postcode1.getLongitude()).thenReturn(-0.141588);
        when(postcode2.getLatitude()).thenReturn(51.524502);
        when(postcode2.getLongitude()).thenReturn(-0.112088);

        // Call the method under test
        DistanceResponse response = distanceService.calculateDistance("12345", "67890");

        // Verify the calculations and response
        assertNotNull(response);
        assertEquals("12345", response.postcode1());
        assertEquals(51.501008, response.latitude1(), 0.000001);
        assertEquals(-0.141588, response.longitude1(), 0.000001);
        assertEquals("67890", response.postcode2());
        assertEquals(51.524502, response.latitude2(), 0.000001);
        assertEquals(-0.112088, response.longitude2(), 0.000001);

        // Verify the distance calculation method was called
        verify(postcodeService, times(1)).findByPostcode("12345");
        verify(postcodeService, times(1)).findByPostcode("67890");
    }

    @Test(expected = RuntimeException.class)
    public void testCalculateDistance_postcodeNotFound() {
        // Simulate Postcode not found by making findByPostcode return null (or throw exception)
        when(postcodeService.findByPostcode("12345")).thenReturn(null);

        // Calling the method under test should throw a RuntimeException
        distanceService.calculateDistance("12345", "67890");
    }
}