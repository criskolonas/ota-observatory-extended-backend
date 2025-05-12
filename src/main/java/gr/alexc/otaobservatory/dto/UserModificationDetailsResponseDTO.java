package gr.alexc.otaobservatory.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserModificationDetailsResponseDTO {
    private String email;
    private String username;
    private Date created_at;
    private Boolean is_admin;
}
