package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.LoginDTO;
import gr.alexc.otaobservatory.dto.LoginRequestDTO;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService service;

    //TODO FIX TYPE
    @PostMapping("login")
    public ResponseEntity<User> postUser(@RequestBody LoginRequestDTO loginRequest) {
        return ResponseEntity.ok(service.getUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @GetMapping("token-check")
    public ResponseEntity<User> checkTokenValidity(@RequestParam String token) {
        return ResponseEntity.ok(service.getCurrentSession(token));
    }

    @GetMapping("logout")
    public ResponseEntity<User> invalidateToken(@RequestParam String token) {
        return ResponseEntity.ok(service.invalidateCurrentSession(token));
    }
}
