package gr.alexc.otaobservatory.dto;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class RegisterRequestDTO {
    private String userName;
    private String email;
    private String password;
}
