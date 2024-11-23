package gr.alexc.otaobservatory.dto.request.govapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class GovApiOtaRecord implements Serializable {

    @JsonProperty("date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected Date date;

    @JsonProperty("ota_full_name")
    protected String otaFullName;

    @JsonProperty("ota_id")
    protected Long otaId;

    @JsonProperty("ota_name")
    protected String otaName;

    @JsonProperty("ota_name_en")
    protected String otaNameEn;

}
