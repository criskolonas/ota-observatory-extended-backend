package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.RegisterRequestDTO;
import gr.alexc.otaobservatory.dto.RegisterResponseDTO;
import gr.alexc.otaobservatory.dto.mapper.RegisterMapper;
import gr.alexc.otaobservatory.entity.Role;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.repository.ota.RoleRepository;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
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

    public RegisterResponseDTO createUser(RegisterRequestDTO registerRequestDTO) {
            String jwtToken ;

            Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role 'user' not found"));

            User newUserEntry = new User();
            newUserEntry.setUsername(registerRequestDTO.getUsername());
            newUserEntry.setEmail(registerRequestDTO.getEmail());
            newUserEntry.setPassword(registerRequestDTO.getPassword());
            newUserEntry.setCreated_at(new Date(System.currentTimeMillis()));
            newUserEntry.setRole(Collections.singletonList(userRole));

            jwtToken = jwtUtilService.generateToken(newUserEntry);

            newUserEntry.setToken(jwtToken);

            userRepository.save(newUserEntry);

            return registerMapper.userToResponseDTO(newUserEntry);
    }

    public Optional<User> getUser(RegisterRequestDTO registerRequestDTO) {
        Optional<User> foundUserOpt = Optional.ofNullable(userRepository.getUserByEmail(registerRequestDTO.getEmail()));
        return foundUserOpt;
    }
}
