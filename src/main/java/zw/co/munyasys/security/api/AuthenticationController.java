package zw.co.munyasys.security.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zw.co.munyasys.security.AuthRequest;
import zw.co.munyasys.security.AuthResponse;
import zw.co.munyasys.security.AuthenticationService;
import zw.co.munyasys.security.RefreshTokenRequest;

@RestController
@CrossOrigin
@Slf4j
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        try {
            log.info("Authenticating user: {}", authRequest.getUsername());
            return ResponseEntity.ok(login(authRequest));
        } catch (Exception ex) {
            log.error("Authentication error {}", ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            log.info("Request refresh token: {}", request.getToken());
            return ResponseEntity.ok(authenticationService.refreshToken(request.getToken()));
        } catch (Exception ex) {
            log.error("", ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

    }

    private AuthResponse login(AuthRequest authRequest) {
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(authentication);
        return authenticationService.generateToken(authRequest.getUsername());
    }

}
