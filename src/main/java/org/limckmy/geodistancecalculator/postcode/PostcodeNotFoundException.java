package org.limckmy.geodistancecalculator.postcode;

public class PostcodeNotFoundException extends RuntimeException {
    public PostcodeNotFoundException(String message) {
        super(message);
    }
}
