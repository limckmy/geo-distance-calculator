package org.limckmy.geodistancecalculator.postcode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PostcodeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostcodeService postcodeService;

    @InjectMocks
    private PostcodeController postcodeController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(postcodeController).build();
    }

    @Test
    public void getPostcode_shouldReturnPostcode() throws Exception {
        String postcode = "12345";
        Postcode mockPostcode = new Postcode();
        mockPostcode.setId(1L);
        mockPostcode.setPostcode(postcode);
        mockPostcode.setLatitude(12.34);
        mockPostcode.setLongitude(56.78);

        // Simulate service returning a postcode
        when(postcodeService.findByPostcode(postcode)).thenReturn(mockPostcode);

        // Perform GET request
        mockMvc.perform(get("/v1/postcodes")
                        .param("postcode", postcode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.postcode").value(postcode))
                .andExpect(jsonPath("$.latitude").value(12.34))
                .andExpect(jsonPath("$.longitude").value(56.78));

        // Verify service interaction
        verify(postcodeService, times(1)).findByPostcode(postcode);
    }

    @Test
    public void updatePostcode_shouldReturnUpdatedPostcode() throws Exception {
        String postcode = "12345";
        PostcodeUpdateRequest request = new PostcodeUpdateRequest(12.34, 56.78);
        Postcode updatedPostcode = new Postcode();
        updatedPostcode.setId(1L);
        updatedPostcode.setPostcode(postcode);
        updatedPostcode.setLatitude(12.34);
        updatedPostcode.setLongitude(56.78);

        // Simulate service updating the postcode
        when(postcodeService.updatePostcode(postcode, request)).thenReturn(updatedPostcode);

        // Perform POST request
        mockMvc.perform(post("/v1/postcodes")
                        .param("postcode", postcode)
                        .contentType("application/json")
                        .content("{\"latitude\": 12.34, \"longitude\": 56.78}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.postcode").value(postcode))
                .andExpect(jsonPath("$.latitude").value(12.34))
                .andExpect(jsonPath("$.longitude").value(56.78));

        // Verify service interaction
        verify(postcodeService, times(1)).updatePostcode(postcode, request);
    }

}