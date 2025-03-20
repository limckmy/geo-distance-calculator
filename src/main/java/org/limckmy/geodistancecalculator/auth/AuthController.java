package org.limckmy.geodistancecalculator.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        UserResponse userResponse =  authService.createUser(userRegistrationRequest);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findUser(@PathVariable("username") String username) {
        UserResponse userResponse = authService.findByUsername(username);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userLoginRequest) {
        TokenResponse tokenDTO = authService.login(userLoginRequest);

        return ResponseEntity.ok(tokenDTO);
    }

}
