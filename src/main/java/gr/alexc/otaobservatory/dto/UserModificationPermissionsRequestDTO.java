package gr.alexc.otaobservatory.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModificationPermissionsRequestDTO {
    private String email;
    private Boolean is_admin;
}
