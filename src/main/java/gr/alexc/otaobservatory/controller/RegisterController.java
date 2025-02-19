package gr.alexc.otaobservatory.controller;

import gr.alexc.otaobservatory.dto.RegisterRequestDTO;
import gr.alexc.otaobservatory.dto.RegisterResponseDTO;
import gr.alexc.otaobservatory.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    //TODO Create type to not contain pass in return
    @PostMapping("register")
    public ResponseEntity<RegisterResponseDTO> postUser(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(registerService.createUser(request));
    }
}
