package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.RegisterRequestDTO;
import gr.alexc.otaobservatory.dto.RegisterResponseDTO;
import gr.alexc.otaobservatory.dto.mapper.RegisterMapper;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.exception.UserAlreadyExistsException;
import gr.alexc.otaobservatory.repository.ota.LoginRepository;
import gr.alexc.otaobservatory.repository.ota.RegisterRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final RegisterRepository registerRepository;
    private final LoginRepository loginRepository;
    private final RegisterMapper registerMapper;
    private final JWTUtilService jwtUtilService;

    public RegisterResponseDTO createUser(RegisterRequestDTO registerRequestDTO) {
        Optional<User> foundUserOpt = Optional.ofNullable(loginRepository.getUserByEmail(registerRequestDTO.getEmail()));
        //if email not
        if (foundUserOpt.isEmpty()) {
            User newUserEntry = new User();
            String jwtToken = "";
            newUserEntry.setUsername(registerRequestDTO.getEmail());
            newUserEntry.setEmail(registerRequestDTO.getEmail());
            newUserEntry.setPassword(registerRequestDTO.getPassword());
            newUserEntry.setCreated_at(new Date(System.currentTimeMillis()));

            jwtToken = jwtUtilService.generateToken(newUserEntry);

            newUserEntry.setToken(jwtToken);
            newUserEntry.setExpirationDate(jwtUtilService.extractClaim(newUserEntry.getToken(), Claims::getExpiration));

            registerRepository.save(newUserEntry);

            return registerMapper.userToResponseDTO(newUserEntry);

        }
        throw new UserAlreadyExistsException("User with email " + registerRequestDTO + " already exists");
    }
}
