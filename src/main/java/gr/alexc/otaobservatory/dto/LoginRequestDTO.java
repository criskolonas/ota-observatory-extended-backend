package gr.alexc.otaobservatory.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NonNull
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NonNull
    private String password;
}
