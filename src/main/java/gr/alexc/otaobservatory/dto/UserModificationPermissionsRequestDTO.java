package gr.alexc.otaobservatory.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class UserModificationPermissionsRequestDTO {
    @NonNull
    private String email;
    @NonNull
    private Boolean isAdmin;
}
