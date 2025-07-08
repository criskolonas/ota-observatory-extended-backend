package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.configuration.PasswordEncoderConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordEncoderService {

    private final PasswordEncoderConfig passwordEncoderConfig;

    public String encodePassword(String password) {
        return passwordEncoderConfig.passwordEncoder().encode(password);
    }

    public Boolean comparePassword(String password, String encodedPassword) {
        return passwordEncoderConfig.passwordEncoder().matches(password,encodedPassword);
    }

}
