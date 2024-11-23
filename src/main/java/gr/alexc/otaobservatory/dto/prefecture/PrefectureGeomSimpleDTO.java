package gr.alexc.otaobservatory.dto.prefecture;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Geometry;

@Getter
@Setter
public class PrefectureGeomSimpleDTO {
    private Long id;
    private String nameGr;
    private String nameEn;
    private Long population;
    private Geometry geom;
    private PrefectureCapitalDTO capital;
    private Double shapeArea;
}
