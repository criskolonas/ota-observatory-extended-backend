package gr.alexc.otaobservatory.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Setter
@Getter
public class LoginDTO {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> role;

}
