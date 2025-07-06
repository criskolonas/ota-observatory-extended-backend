package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.mapper.LoginMapper;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final LoginMapper loginMapper;
    private final JWTUtilService jwtUtilService;


    public User getUser(String email, String password) {
        Optional<User> foundUserOpt = Optional.ofNullable(userRepository.getUser(email, password));

        // Check if the user was found
        if (foundUserOpt.isEmpty()) {
//            Optional<User> foundUserByEmail = Optional.ofNullable(loginRepository.getUserByEmail(email));
//            if (foundUserByEmail.isPresent()) {
//                return new User();
//            }
            return null;
        }

        // Map the user details and generate a JWT token
        User userDetails = loginMapper.logintoUserDTO(userRepository.getUser(email, password));
        String jwtToken = jwtUtilService.generateToken(userDetails);

        // Set the token and expiration date in the user object
        userDetails.setToken(jwtToken);

        // Save the updated user details
        return userRepository.save(userDetails);
    }

    public User getCurrentSession(String token) {
        // Extract the token's expiration date

        Date extractedClaimExpiration = jwtUtilService.extractClaim(token, Claims::getExpiration);

        // Find the user associated with the token
        Optional<User> foundUserOpt = Optional.ofNullable(userRepository.getUserByExpirationToken(token));
        if (foundUserOpt.isEmpty()) {
            return null;
        }

        // Check if the token has expired
        if (extractedClaimExpiration.getTime() <= System.currentTimeMillis()) {
            foundUserOpt.ifPresent(foundUser -> {
                foundUser.setToken(null); // Invalidate the token
                userRepository.save(foundUser);
            });
            return null;
        }

        return foundUserOpt.get();
    }

    public void invalidateCurrentSession(String token) {
        // Find the user associated with the token
        Optional<User> foundUserOpt = Optional.ofNullable(userRepository.getUserByExpirationToken(token));

        // Invalidate the token
        foundUserOpt.ifPresent(foundUser -> {
            foundUser.setToken(null);
            userRepository.save(foundUser);
        });

    }
}