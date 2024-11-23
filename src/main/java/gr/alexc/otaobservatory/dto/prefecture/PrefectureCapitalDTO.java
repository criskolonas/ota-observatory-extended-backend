package gr.alexc.otaobservatory.dto.prefecture;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter
public class PrefectureCapitalDTO {
    private Long id;
    private String name;
    private String nameEn;
    private Long population;
    private Double area;
    private Geometry geom;

}
