package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.LoginRequestDTO;
import gr.alexc.otaobservatory.dto.LoginResponseDTO;
import gr.alexc.otaobservatory.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("api/login")
    public ResponseEntity<LoginResponseDTO> postUser(@RequestBody LoginRequestDTO loginRequest, HttpServletResponse response){
        LoginResponseDTO user = loginService.getUser(loginRequest.getEmail(), loginRequest.getPassword(),response);
        return ResponseEntity.ok(user);
    }

    @PostMapping("api/user/token-check")
    public ResponseEntity<LoginResponseDTO> checkTokenValidity(@CookieValue(name = "jwtToken", required = false) String jwtToken){
            LoginResponseDTO user = loginService.getCurrentSession(jwtToken);
            return ResponseEntity.ok(user);
    }

    @PostMapping("/api/user/logout")
    public ResponseEntity<Boolean> invalidateToken(@CookieValue(name = "jwtToken", required = false) String token, HttpServletResponse response) {
            loginService.invalidateCurrentSession(token,response);
            return ResponseEntity.ok(true);
    }
}