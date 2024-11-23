package gr.alexc.otaobservatory.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OtaVariableDTO {
    private Long id;
    private String name;
    private String description;
    private String variableType;
    private LocalDateTime lastUpdate;
}
