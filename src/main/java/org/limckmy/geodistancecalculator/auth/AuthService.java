package org.limckmy.geodistancecalculator.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    public AuthService(AuthRepository authRepository, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponse createUser(UserRegistrationRequest userRegistrationRequest) {

        if (authRepository.existsByEmail(userRegistrationRequest.email())) {
            throw new IllegalArgumentException("Email already in use.");
        }

        if (authRepository.existsByUsername(userRegistrationRequest.username())) {
            throw new IllegalArgumentException("Username already in use.");
        }

        User user = new User()
                .setEmail(userRegistrationRequest.email())
                .setUsername(userRegistrationRequest.username())
                .setPassword(passwordEncoder.encode(userRegistrationRequest.password()));
        User userSaved = authRepository.save(user);
        return mapper(userSaved);
    }

    public UserResponse findByUsername(String username) {
        User user = authRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return mapper(user);
    }

    public TokenResponse login(UserLoginRequest userLoginRequest) {
        User user = authRepository.findByUsername(userLoginRequest.username()).orElseThrow(() -> new RuntimeException("Login failed"));
        if (passwordEncoder.matches(userLoginRequest.password(), user.getPassword())) {
            return new TokenResponse(jwtUtil.generateToken(user.getUsername()));
        }
        throw new RuntimeException("Login failed");
    }

    private UserResponse mapper(User user) {
        return new UserResponse(user.getUsername(), user.getEmail(), user.getId());
    }
}
