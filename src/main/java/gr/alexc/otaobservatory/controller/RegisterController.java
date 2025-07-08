package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.RegisterRequestDTO;
import gr.alexc.otaobservatory.dto.RegisterResponseDTO;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.exception.UserAlreadyExistsException;
import gr.alexc.otaobservatory.service.JWTUtilService;
import gr.alexc.otaobservatory.service.LoginService;
import gr.alexc.otaobservatory.service.RegisterService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("api/register")
    public ResponseEntity<RegisterResponseDTO> postUser(@RequestBody RegisterRequestDTO request, HttpServletResponse response) {
        RegisterResponseDTO registeredUser = registerService.createUser(request,response);

        return ResponseEntity.ok(registeredUser);

    }
}
