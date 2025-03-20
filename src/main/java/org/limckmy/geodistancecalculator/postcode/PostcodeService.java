package org.limckmy.geodistancecalculator.postcode;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
@RequiredArgsConstructor
public class PostcodeService {

    private final PostcodeRepository postcodeRepository;

    private final DataSource dataSource;

    public Postcode findByPostcode(String postcode) {
        //Normalize postcodes (remove spaces, convert to uppercase)
        //postcode = postcode.replaceAll("\\s+" , "").toUpperCase();

        return postcodeRepository.findByPostcode(postcode).orElseThrow(() -> new PostcodeNotFoundException("Postcode not found"));
    }

    public Postcode updatePostcode(String postcode, PostcodeUpdateRequest request) {
        Postcode postcodeEntity = findByPostcode(postcode);
        postcodeEntity.setLatitude(request.latitude());
        postcodeEntity.setLongitude(request.longitude());
        return postcodeRepository.save(postcodeEntity);
    }
}
