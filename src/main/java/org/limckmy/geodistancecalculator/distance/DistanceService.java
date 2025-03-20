package org.limckmy.geodistancecalculator.distance;

import org.limckmy.geodistancecalculator.postcode.Postcode;
import org.limckmy.geodistancecalculator.postcode.PostcodeService;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

    private final PostcodeService postcodeService;

    public DistanceService(PostcodeService postcodeService) {
        this.postcodeService = postcodeService;
    }

    public DistanceResponse calculateDistance(String postcode1, String postcode2) {

        Postcode post1 = postcodeService.findByPostcode(postcode1);
        Postcode post2 = postcodeService.findByPostcode(postcode2);

        double latitude1 = post1.getLatitude();
        double longitude1 = post1.getLongitude();
        double latitude2 = post2.getLatitude();
        double longitude2 = post2.getLongitude();

        double distance = HaversinceUtil.calculateDistance(latitude1, longitude1 , latitude2, longitude2);
        return new DistanceResponse(postcode1, latitude1, longitude1, postcode2, latitude2, longitude2, distance);
    }
}
