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
    private final JWTUtilService jwtUtilService;
    private final LoginService loginService;

    @PostMapping("register")
    public ResponseEntity<RegisterResponseDTO> postUser(@RequestBody RegisterRequestDTO request, HttpServletResponse response) {

        Optional<User> foundUser = this.registerService.getUser(request);
        //check if user with email exists
        if(foundUser.isPresent()){
            throw new UserAlreadyExistsException("User already exists");
        }
        //register the user
        RegisterResponseDTO registeredUser = registerService.createUser(request);

        foundUser = this.registerService.getUser(request);


        if(foundUser.isPresent()) {
            User user = foundUser.get();
            String token = jwtUtilService.generateToken(user);

            this.jwtUtilService.setTokenAsHttpOnlyCookie(response, token);
            return ResponseEntity.ok(registeredUser);
        }

        return ResponseEntity.badRequest().body(registeredUser);
    }
}
