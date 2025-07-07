package gr.alexc.otaobservatory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;


@Setter
@Getter
public class RegisterRequestDTO {
    @NonNull
    private String username;
    @NonNull
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull
    private String password;
}
