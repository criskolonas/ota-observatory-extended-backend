package gr.alexc.otaobservatory.dto;

import gr.alexc.otaobservatory.entity.Role;
import gr.alexc.otaobservatory.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class LoginResponseDTO {

    private String email;
    private String username;
    private Collection<Role> role;

}