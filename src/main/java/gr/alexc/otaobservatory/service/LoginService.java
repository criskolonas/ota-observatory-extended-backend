package gr.alexc.otaobservatory.service;

import gr.alexc.otaobservatory.dto.LoginResponseDTO;
import gr.alexc.otaobservatory.dto.mapper.LoginMapper;
import gr.alexc.otaobservatory.entity.User;
import gr.alexc.otaobservatory.exception.ExpiredTokenException;
import gr.alexc.otaobservatory.exception.WrongPasswordException;
import gr.alexc.otaobservatory.repository.ota.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;
    private final JWTUtilService jwtUtilService;
    private final LoginMapper loginMapper;


    public LoginResponseDTO getUser(String email, String password, HttpServletResponse response) {

        User foundUser = userRepository.getUserByEmail(email).orElseThrow(()-> new EntityNotFoundException("Ο χρήστης δεν βρέθηκε."));
        System.out.println(foundUser.getEmail() +"1"+foundUser.getPassword());

        Boolean isPasswordMatch = passwordEncoderService.comparePassword(password, foundUser.getPassword());

        if (!isPasswordMatch) {
            throw new WrongPasswordException("Λάθος κωδικός πρόσβασης");
        }
        System.out.println(foundUser.getEmail() +"2"+foundUser.getPassword());

        String jwtToken = jwtUtilService.generateToken(foundUser);
        this.jwtUtilService.setTokenAsHttpOnlyCookie(response, jwtToken);
        System.out.println(foundUser.getEmail() +"3"+foundUser.getPassword());

        foundUser.setToken(jwtToken);
        userRepository.save(foundUser);
        System.out.println(foundUser.getEmail() +"4"+foundUser.getPassword());

        return loginMapper.toDto(foundUser);
    }

    public LoginResponseDTO getCurrentSession(String token) {
        // Extract the token's expiration date

        Date extractedClaimExpiration = jwtUtilService.extractClaim(token, Claims::getExpiration);

        // Find the user associated with the token
        User foundUser = userRepository.getUserByExpirationToken(token).orElseThrow(()-> new EntityNotFoundException("Ο χρήστης δεν βρέθηκε."));

        // Check if the token has expired
        if (extractedClaimExpiration.getTime() <= System.currentTimeMillis()) {

            foundUser.setToken(null); // Invalidate the token
            userRepository.save(foundUser);

            throw new ExpiredTokenException("Η συνεδρία έχει λήξει");
        }

        return loginMapper.toDto(foundUser);
    }

    public void invalidateCurrentSession(String token, HttpServletResponse response) {
        // Find the user associated with the token
        User foundUser = userRepository.getUserByExpirationToken(token).orElseThrow(()-> new EntityNotFoundException("Ο χρήστης δεν βρέθηκε."));
        this.jwtUtilService.clearTokenCookie(response);

        foundUser.setToken(null);
        userRepository.save(foundUser);

    }
}