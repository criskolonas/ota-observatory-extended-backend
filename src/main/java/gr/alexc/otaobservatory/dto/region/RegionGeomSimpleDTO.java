package gr.alexc.otaobservatory.dto.region;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter
public class RegionGeomSimpleDTO {
    private Long id;
    private String name;
    private Double unemploymentRate;
    private Long criminalityRate;
    private Geometry geom;
}
