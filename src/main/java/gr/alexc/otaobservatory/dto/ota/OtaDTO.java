package gr.alexc.otaobservatory.dto.ota;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter
public class OtaDTO {

    private Long id;
    private String otaId;
    private String name;
    private Geometry geom;

}
