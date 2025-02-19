package gr.alexc.otaobservatory.dto;

import gr.alexc.otaobservatory.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String email;
    private String username;

    public LoginResponseDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getPassword();
    }
}