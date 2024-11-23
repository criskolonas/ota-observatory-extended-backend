package gr.alexc.otaobservatory.dto.request.govapi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AreaRecord  extends GovApiOtaRecord implements Serializable {
    private Double area;

}
