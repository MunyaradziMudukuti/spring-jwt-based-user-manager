package zw.co.munyasys.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import zw.co.munyasys.users.model.User;
import zw.co.munyasys.users.service.read.UserReaderService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static zw.co.munyasys.common.Constants.BEARER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final String REFRESH = "refresh";
    private static final String TOKEN_TYPE = "type";
    private final UserReaderService userService;
    private final JwtService jwtService;
    @Value("${access.token.expirationTimeInSec}")
    private int accessExpirationInSec;
    @Value("${refresh.token.expirationTimeInSec}")
    private int refreshExpirationInSec;

    @Override
    public String getUsernameFromToken(String token) {
        return jwtService.extractUsername(token);
    }

    private Claims getClaims(String token) {
        return jwtService.extractAllClaims(token);
    }

    public AuthResponse refreshToken(final String authHeader) {

        final boolean hasBearerToken = !ObjectUtils.isEmpty(authHeader)
                && authHeader.startsWith(BEARER);

        if (!hasBearerToken) {
            throw new RuntimeException("Invalid token provided");
        }

        String refresh = authHeader.substring(BEARER.length());

        final Claims claims = getClaims(refresh);

        final String type = claims.get(TOKEN_TYPE, String.class);

        if (!REFRESH.equalsIgnoreCase(type)) {
            throw new RuntimeException("Invalid token provided");
        }

        final UUID id = UUID.fromString(claims.get("rid").toString());

        log.info("Refresh user if: {}", id);

        final User user = userService.findById(id);

        return getAuthResponse(user);

    }

    @Override
    public AuthResponse generateToken(String username) {
        final User user = userService.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));
        return getAuthResponse(user);
    }

    private AuthResponse getAuthResponse(User user) {
        final String tokenId = UUID.randomUUID().toString();
        final String refreshToken = jwtService.generateToken(tokenId, getRefreshClaims(user.getId().toString()), user);
        final String token = jwtService.generateToken(tokenId, getUserClaims(user, tokenId), user);

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(BEARER)
                .refreshExpiresIn(refreshExpirationInSec)
                .expiresIn(accessExpirationInSec)
                .build();
    }

    private Map<String, Object> getRefreshClaims(String refreshId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, REFRESH);
        claims.put("rid", refreshId);
        return claims;
    }

    private Map<String, Object> getUserClaims(User user, String tokenId) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("user_id", user.getId());

        claims.put("username", user.getEmail());

        claims.put("given_name", user.getEmail());

        claims.put("email", user.getEmail());

        claims.put(TOKEN_TYPE, "access");

        claims.put("rid", tokenId);

        return claims;
    }


}
