package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.RegisterRequestDTO;
import gr.alexc.otaobservatory.dto.RegisterResponseDTO;
import gr.alexc.otaobservatory.dto.mapper.RegisterMapper;
import gr.alexc.otaobservatory.entity.Role;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.exception.UserAlreadyExistsException;
import gr.alexc.otaobservatory.repository.ota.RoleRepository;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RegisterMapper registerMapper;
    private final JWTUtilService jwtUtilService;
    private final PasswordEncoderService passwordEncoderService;


    public RegisterResponseDTO createUser(RegisterRequestDTO registerRequestDTO, HttpServletResponse response) {
            Optional<User> foundUser = userRepository.getUserByEmail(registerRequestDTO.getEmail());
            String jwtToken ;

            if(foundUser.isPresent()) {
                throw new UserAlreadyExistsException("Ο χρήστης με αυτό το email υπάρχει ήδη");
            }

            Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role 'user' not found"));

            User newUserEntry = new User();
            newUserEntry.setUsername(registerRequestDTO.getUsername());
            newUserEntry.setEmail(registerRequestDTO.getEmail());
            newUserEntry.setPassword(passwordEncoderService.encodePassword(registerRequestDTO.getPassword()));
            newUserEntry.setCreated_at(new Date(System.currentTimeMillis()));
            newUserEntry.setRole(Collections.singletonList(userRole));

            jwtToken = jwtUtilService.generateToken(newUserEntry);
            this.jwtUtilService.setTokenAsHttpOnlyCookie(response, jwtToken);
            newUserEntry.setToken(jwtToken);

            userRepository.save(newUserEntry);

            return registerMapper.userToResponseDTO(newUserEntry);
    }
}
