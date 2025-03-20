package org.limckmy.geodistancecalculator.distance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.limckmy.geodistancecalculator.exception.GlobalExceptionHandler;
import org.limckmy.geodistancecalculator.postcode.PostcodeNotFoundException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class DistanceControllerTest {

    @InjectMocks
    private DistanceController distanceController;  // The controller we are testing

    @Mock
    private DistanceService distanceService;  // Mocking the DistanceService dependency

    private MockMvc mockMvc;  // MockMvc object for simulating HTTP requests

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(distanceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void testGetDistance_success() throws Exception {
        // Mock the service to return a specific response when the calculateDistance method is called
        DistanceResponse mockResponse = new DistanceResponse(
                "12345", 40.7128, -74.0060,  // Postcode1 (New York)
                "67890", 51.5074, -0.1278,  // Postcode2 (London)
                5570.0);  // Distance between New York and London in kilometers (approx.)
        when(distanceService.calculateDistance("12345", "67890")).thenReturn(mockResponse);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/v1/distance")
                        .param("postcode1", "12345")
                        .param("postcode2", "67890"))
                .andExpect(status().isOk())  // Check if status code is 200 OK
                .andExpect(jsonPath("$.postcode1").value("12345"))  // Check postcode1 value
                .andExpect(jsonPath("$.latitude1").value(40.7128))  // Check latitude1
                .andExpect(jsonPath("$.longitude1").value(-74.0060))  // Check longitude1
                .andExpect(jsonPath("$.postcode2").value("67890"))  // Check postcode2 value
                .andExpect(jsonPath("$.latitude2").value(51.5074))  // Check latitude2
                .andExpect(jsonPath("$.longitude2").value(-0.1278))  // Check longitude2
                .andExpect(jsonPath("$.distance").value(5570.0));  // Check the distance value


        // Verify that the service method was called exactly once
        verify(distanceService, times(1)).calculateDistance("12345", "67890");
    }


    @Test
    public void testGetDistance_invalidPostcodes() throws Exception {
        // Mock the service to throw an exception for invalid postcodes
        when(distanceService.calculateDistance("invalid1", "invalid2")).thenThrow(new PostcodeNotFoundException("Postcode not found"));

        // Perform the GET request and verify that the status is 400 BAD REQUEST
        mockMvc.perform(get("/v1/distance")
                        .param("postcode1", "invalid1")
                        .param("postcode2", "invalid2"))
                .andExpect(status().isNotFound())  // Expecting a 404 response
                .andExpect(jsonPath("$.code").value(404))  // Check if the response has the correct error code
                .andExpect(jsonPath("$.message").value("Postcode not found"));  // Check the error message


        // Verify that the service method was called exactly once with the invalid postcodes
        verify(distanceService, times(1)).calculateDistance("invalid1", "invalid2");
    }

    @Test
    public void testGetDistance_missingPostcode() throws Exception {
        // Perform the GET request with missing postcode parameters and verify that the status is 400 BAD REQUEST
        mockMvc.perform(get("/v1/distance"))
                .andExpect(status().isBadRequest());
    }
}