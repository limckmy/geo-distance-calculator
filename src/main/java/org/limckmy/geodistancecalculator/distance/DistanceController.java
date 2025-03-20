package org.limckmy.geodistancecalculator.distance;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/distance")
public class DistanceController {

    private final DistanceService distanceService;

    public DistanceController(DistanceService distanceService) {
        this.distanceService = distanceService;
    }

    @GetMapping
    public ResponseEntity<DistanceResponse> getDistance(@RequestParam String postcode1, @RequestParam String postcode2) {
        DistanceResponse response = distanceService.calculateDistance(postcode1, postcode2);
        return ResponseEntity.ok(response);
    }

}
