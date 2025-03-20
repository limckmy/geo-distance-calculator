package org.limckmy.geodistancecalculator.auth;

public record UserRegistrationRequest(String username, String password, String email) {
}
