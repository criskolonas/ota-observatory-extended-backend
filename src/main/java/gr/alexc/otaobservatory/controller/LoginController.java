package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.LoginRequestDTO;
import gr.alexc.otaobservatory.dto.LoginResponseDTO;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.exception.ExpiredTokenException;
import gr.alexc.otaobservatory.exception.RateLimitReachedException;
import gr.alexc.otaobservatory.exception.UserNotFoundException;
import gr.alexc.otaobservatory.service.JWTUtilService;
import gr.alexc.otaobservatory.service.LoginService;
import gr.alexc.otaobservatory.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final JWTUtilService jwtUtilService;
    private final RateLimiterService rateLimiterService;

    @Autowired
    public LoginController(RateLimiterService rateLimiterService, LoginService loginService, JWTUtilService jwtUtilService1) {
        this.loginService = loginService;
        this.jwtUtilService = jwtUtilService1;
        this.rateLimiterService = rateLimiterService;
    }

    @PostMapping("api/login")
    public ResponseEntity<LoginResponseDTO> postUser(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response) throws UserNotFoundException {

        // Authenticate the user
        User user = loginService.getUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null) {
            // Generate the JWT token
            String token = jwtUtilService.generateToken(user);

            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(user);

            // Set the token as an HttpOnly cookie
            this.jwtUtilService.setTokenAsHttpOnlyCookie(response, token);

            return ResponseEntity.ok(loginResponseDTO);
        } else {
            throw new UserNotFoundException("Incorrect email or password.");
        }
    }

    @PostMapping("api/user/token-check")
    public ResponseEntity<LoginResponseDTO> checkTokenValidity(@CookieValue(name = "jwtToken", required = false) String jwtToken) throws RateLimitReachedException, ExpiredTokenException {
        if (jwtToken != null ) {
            User user = loginService.getCurrentSession(jwtToken);
            if (user != null) {

                LoginResponseDTO loginResponseDTO = new LoginResponseDTO(user);
                return ResponseEntity.ok(loginResponseDTO);
            }
            throw new ExpiredTokenException("Expired token. Please try again.");
        }
        throw new ExpiredTokenException("You have been logged out.");
    }

    @PostMapping("/api/user/logout")
    public ResponseEntity<Object> invalidateToken(@CookieValue(name = "jwtToken", required = false) String token, HttpServletResponse response) {
        if (token != null) {
            // Invalidate the token
            loginService.invalidateCurrentSession(token);

            // Clear the JWT cookie
            this.jwtUtilService.clearTokenCookie(response);

            return ResponseEntity.ok(null);
        }
        throw new ExpiredTokenException("Expired token. Please try again.");
    }
}