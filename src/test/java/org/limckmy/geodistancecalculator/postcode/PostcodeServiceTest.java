package org.limckmy.geodistancecalculator.postcode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostcodeServiceTest {

    @InjectMocks
    private PostcodeService postcodeService;  // The service we are testing

    @Mock
    private PostcodeRepository postcodeRepository;  // Mocking the repository

    private Postcode existingPostcode;
    private PostcodeUpdateRequest updateRequest;

    @Before
    public void setUp() {
        // Create a Postcode object to be used for testing
        existingPostcode = new Postcode();
        existingPostcode.setId(1L);
        existingPostcode.setPostcode("12345");
        existingPostcode.setLatitude(10.0);
        existingPostcode.setLongitude(20.0);

        // Create a PostcodeUpdateRequest object for testing update
        updateRequest = new PostcodeUpdateRequest(12.34, 56.78);
    }

    @Test
    public void testFindByPostcode_whenPostcodeExists() {
        // Mock the repository to return the existing postcode
        when(postcodeRepository.findByPostcode("12345")).thenReturn(Optional.of(existingPostcode));

        // Call the service method
        Postcode result = postcodeService.findByPostcode("12345");

        // Verify the result and ensure it matches the expected postcode
        assertNotNull(result);
        assertEquals("12345", result.getPostcode());
        assertEquals(10.0, result.getLatitude(), 0.0);
        assertEquals(20.0, result.getLongitude(), 0.0);

        // Verify that repository's findByPostcode method was called once
        verify(postcodeRepository, times(1)).findByPostcode("12345");
    }

    @Test(expected = RuntimeException.class)
    public void testFindByPostcode_whenPostcodeDoesNotExist() {
        // Mock the repository to return empty when postcode is not found
        when(postcodeRepository.findByPostcode("99999")).thenReturn(Optional.empty());

        // Call the service method, which should throw a RuntimeException
        postcodeService.findByPostcode("99999");
    }

    @Test
    public void testUpdatePostcode() {
        // Mock the repository to return the existing postcode
        when(postcodeRepository.findByPostcode("12345")).thenReturn(Optional.of(existingPostcode));
        when(postcodeRepository.save(existingPostcode)).thenReturn(existingPostcode);

        // Call the service method to update the postcode
        Postcode updatedPostcode = postcodeService.updatePostcode("12345", updateRequest);

        // Verify the updated postcode's latitude and longitude
        assertNotNull(updatedPostcode);
        assertEquals(12.34, updatedPostcode.getLatitude(), 0.0);
        assertEquals(56.78, updatedPostcode.getLongitude(), 0.0);

        // Verify that repository's findByPostcode and save methods were called
        verify(postcodeRepository, times(1)).findByPostcode("12345");
        verify(postcodeRepository, times(1)).save(existingPostcode);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdatePostcode_whenPostcodeDoesNotExist() {
        // Mock the repository to return empty when postcode is not found
        when(postcodeRepository.findByPostcode("99999")).thenReturn(Optional.empty());

        // Call the service method, which should throw a RuntimeException
        postcodeService.updatePostcode("99999", updateRequest);
    }

}