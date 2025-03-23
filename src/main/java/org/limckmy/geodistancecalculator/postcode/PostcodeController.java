package org.limckmy.geodistancecalculator.postcode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/postcodes")
public class PostcodeController {

    private final PostcodeService postcodeService;

    public PostcodeController(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    @GetMapping
    public ResponseEntity<Postcode> getPostcode(@RequestParam String postcode) {
        return ResponseEntity.ok(postcodeService.findByPostcode(postcode));
    }

    @PatchMapping
    public ResponseEntity<Postcode> updatePostcode(@RequestParam String postcode, @RequestBody PostcodeUpdateRequest request) {
        return ResponseEntity.ok(postcodeService.updatePostcode(postcode, request));
    }
}
