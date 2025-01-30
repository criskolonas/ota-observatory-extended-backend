package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.LoginRequestDTO;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.service.JWTUtilService;
import gr.alexc.otaobservatory.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JWTUtilService jwtUtilService;

    @PostMapping("/login")
    public ResponseEntity<User> postUser(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) {
        // Authenticate the user
        User user = loginService.getUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            // Generate the JWT token
            String token = jwtUtilService.generateToken(user);

            // Set the token as an HttpOnly cookie
            setTokenAsHttpOnlyCookie(response, token);

            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(401).build(); // Unauthorized
        }
    }

    @GetMapping("/token-check")
    public ResponseEntity<User> checkTokenValidity(@CookieValue(name = "jwtToken", required = false) String token) {
        if (token != null) {
            User user = loginService.getCurrentSession(token);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(401).build(); // Unauthorized
    }

    @GetMapping("/logout")
    public ResponseEntity<String> invalidateToken(@CookieValue(name = "jwtToken", required = false) String token, HttpServletResponse response) {
        if (token != null) {
            // Invalidate the token
            loginService.invalidateCurrentSession(token);

            // Clear the JWT cookie
            clearTokenCookie(response);

            return ResponseEntity.ok("Logout successful!");
        }
        return ResponseEntity.status(400).body("No token found");
    }

    private void setTokenAsHttpOnlyCookie(HttpServletResponse response, String token) {
        // Create a new cookie
        jakarta.servlet.http.Cookie cookie = new jakarta.servlet.http.Cookie("jwtToken", token);

        // Set the HttpOnly flag
        cookie.setHttpOnly(true);

        // Set the Secure flag (use this in production with HTTPS)
        cookie.setSecure(true);

        // Set the cookie path
        cookie.setPath("/");

        // Set the cookie expiration time (in seconds)
        cookie.setMaxAge((int) (jwtUtilService.getExpirationTime() / 1000));

        // Add the cookie to the response
        response.addCookie(cookie);
    }

    private void clearTokenCookie(HttpServletResponse response) {
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