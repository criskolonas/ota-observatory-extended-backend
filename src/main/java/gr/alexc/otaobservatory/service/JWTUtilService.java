package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.entity.Role;
import gr.alexc.otaobservatory.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtilService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Collection<String> extractRoleNames(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("role", Collection.class); // Expects ["ADMIN", "USER"] in JWT
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);

            return true; // Token is valid
        } catch (Exception e) {
            // Token is invalid (expired, malformed, etc.)
            return false;
        }
    }

    public long getExpirationTime() {
        return jwtExpiration;
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            User userDetails,
            long expiration
    ) {
        // Extract role names from User entity (ignoring IDs)
        List<String> roleNames = userDetails.getRole().stream()
                .map(Role::getName)  // Only get the name field
                .collect(Collectors.toList());

        // Create final claims combining extraClaims and role names
        Map<String, Object> claims = new HashMap<>();
        if (extraClaims != null) {
            claims.putAll(extraClaims);
        }
        claims.put("role", roleNames);  // Add clean role names

        return Jwts
                .builder()
                .setClaims(claims)  // Use the combined claims
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void setTokenAsHttpOnlyCookie(HttpServletResponse response, String token) {
        // Create a new cookie
        jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwtToken", token);

        // Set the HttpOnly flag
        cookie.setHttpOnly(true);

        // Set the Secure flag (use this in production with HTTPS)
        cookie.setSecure(true);

        // Set the cookie path
        cookie.setPath("/");

        cookie.setAttribute("SameSite", "None");

        // Set the cookie expiration time (in seconds)
        cookie.setMaxAge((int) (getExpirationTime() / 1000));

        // Add the cookie to the response
        response.addCookie(cookie);
    }

    public void clearTokenCookie(HttpServletResponse response) {
        // Create a new cookie with the same name
        jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwtToken", null);

        // Set the cookie path
        cookie.setPath("/");

        // Set the cookie expiration time to 0 (to delete it)
        cookie.setMaxAge(0);

        // Add the cookie to the response
        response.addCookie(cookie);
    }
}