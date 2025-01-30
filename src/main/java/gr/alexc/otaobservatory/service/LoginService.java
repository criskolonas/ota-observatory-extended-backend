package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.mapper.LoginMapper;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.exception.ExpiredTokenException;
import gr.alexc.otaobservatory.exception.UserNotFoundException;
import gr.alexc.otaobservatory.exception.WrongPasswordException;
import gr.alexc.otaobservatory.repository.ota.LoginRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;
    private final LoginMapper loginMapper;
    private final JWTUtilService jwtUtilService;

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return The authenticated user with a JWT token and expiration date.
     * @throws UserNotFoundException  If the user is not found.
     * @throws WrongPasswordException If the password is incorrect.
     */
    public User getUser(String email, String password) {
        Optional<User> foundUserOpt = Optional.ofNullable(loginRepository.getUser(email, password));

        // Check if the user was found
        if (foundUserOpt.isEmpty()) {
            Optional<User> foundUserByEmail = Optional.ofNullable(loginRepository.getUserByEmail(email));
            if (foundUserByEmail.isPresent()) {
                throw new WrongPasswordException("Password of " + email + " is incorrect");
            }
            throw new UserNotFoundException("User with email " + email + " not found");
        }

        // Map the user details and generate a JWT token
        User userDetails = loginMapper.logintoUserDTO(loginRepository.getUser(email, password));
        String jwtToken = jwtUtilService.generateToken(userDetails);

        // Set the token and expiration date in the user object
        userDetails.setToken(jwtToken);
        userDetails.setExpirationDate(jwtUtilService.extractClaim(jwtToken, Claims::getExpiration));

        // Save the updated user details
        return loginRepository.save(userDetails);
    }

    /**
     * Validates the current session by checking the token's expiration.
     *
     * @param token The JWT token to validate.
     * @return The user associated with the token.
     * @throws UserNotFoundException  If the user is not found.
     * @throws ExpiredTokenException  If the token has expired.
     */
    public User getCurrentSession(String token) {
        // Extract the token's expiration date
        Date extractedClaimExpiration = jwtUtilService.extractClaim(token, Claims::getExpiration);

        // Find the user associated with the token
        Optional<User> foundUserOpt = Optional.ofNullable(loginRepository.getUserByExpirationToken(token));
        if (foundUserOpt.isEmpty()) {
            throw new UserNotFoundException("User with token " + token + " not found");
        }

        // Check if the token has expired
        if (extractedClaimExpiration.getTime() <= System.currentTimeMillis()) {
            foundUserOpt.ifPresent(foundUser -> {
                foundUser.setToken(null); // Invalidate the token
                foundUser.setExpirationDate(null);
                loginRepository.save(foundUser);
            });
            throw new ExpiredTokenException("Token " + token + " has expired");
        }

        return foundUserOpt.get();
    }

    /**
     * Invalidates the current session by removing the token from the user.
     *
     * @param token The JWT token to invalidate.
     * @return The user with the token invalidated.
     * @throws UserNotFoundException If the user is not found.
     */
    public User invalidateCurrentSession(String token) {
        // Find the user associated with the token
        Optional<User> foundUserOpt = Optional.ofNullable(loginRepository.getUserByExpirationToken(token));
        if (foundUserOpt.isEmpty()) {
            throw new UserNotFoundException("User with token " + token + " not found");
        }

        // Invalidate the token
        foundUserOpt.ifPresent(foundUser -> {
            foundUser.setToken(null);
            foundUser.setExpirationDate(null);
            loginRepository.save(foundUser);
        });

        return foundUserOpt.get();
    }
}