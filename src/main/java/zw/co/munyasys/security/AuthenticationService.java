package zw.co.munyasys.security;

public interface AuthenticationService {

    String getUsernameFromToken(String token);

    AuthResponse generateToken(String email);

    AuthResponse refreshToken(final String authHeader);

}
