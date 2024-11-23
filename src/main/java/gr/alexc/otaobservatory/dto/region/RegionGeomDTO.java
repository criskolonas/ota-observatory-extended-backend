package gr.alexc.otaobservatory.dto.region;

import gr.alexc.otaobservatory.dto.prefecture.PrefectureSimpleDTO;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@Getter
@Setter
public class RegionGeomDTO {
    private Long id;
    private String name;
    private Double unemploymentRate;
    private Long criminalityRate;
    private Geometry geom;
    private List<PrefectureSimpleDTO> prefectures;
}
