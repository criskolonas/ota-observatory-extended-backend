package gr.alexc.otaobservatory.dto.request.govapi;

import gr.alexc.otaobservatory.entity.CadastralRegistrationStatus;
import gr.alexc.otaobservatory.entity.ota.CadastralRegistrationStatusOTA;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class CadastralStatusRecord extends GovApiOtaRecord implements Serializable {
    private String status;


}
