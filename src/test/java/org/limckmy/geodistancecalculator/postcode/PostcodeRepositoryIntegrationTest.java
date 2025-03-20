package org.limckmy.geodistancecalculator.postcode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")  // Point to test DB config
public class PostcodeRepositoryIntegrationTest {

    @Autowired
    private PostcodeRepository postcodeRepository;

    @Test
    public void testFindByPostcode() {
        // Arrange: create a new Postcode and save it to the repository
        Postcode postcode = new Postcode();
        postcode.setPostcode("12345");
        postcode.setLatitude(51.5074);
        postcode.setLongitude(0.1278);
        postcodeRepository.save(postcode);

        // Act: fetch the Postcode by its postcode
        Optional<Postcode> foundPostcode = postcodeRepository.findByPostcode("12345");

        // Assert: verify that the postcode is correctly retrieved
        assertTrue(foundPostcode.isPresent());
        assertEquals("12345", foundPostcode.get().getPostcode());
        assertEquals(51.5074, foundPostcode.get().getLatitude(), 0.0001); // Assert latitude with a tolerance
        assertEquals(0.1278, foundPostcode.get().getLongitude(), 0.0001); // Assert longitude with a tolerance
    }

    @Test
    public void testFindByPostcodeNotFound() {
        // Act: attempt to fetch a postcode that doesn't exist
        Optional<Postcode> foundPostcode = postcodeRepository.findByPostcode("00000");

        // Assert: verify that no postcode is found
        assertFalse(foundPostcode.isPresent());
    }
}