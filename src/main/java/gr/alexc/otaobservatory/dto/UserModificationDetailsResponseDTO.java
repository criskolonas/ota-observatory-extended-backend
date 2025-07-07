package gr.alexc.otaobservatory.dto;

import gr.alexc.otaobservatory.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;

@Getter
@Setter
public class UserModificationDetailsResponseDTO {
    private String email;
    private String username;
    private Date created_at;
    private Collection<Role> role;
}
