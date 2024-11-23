package gr.alexc.otaobservatory.dto.prefecture;

import gr.alexc.otaobservatory.dto.ota.OtaDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PrefectureGeomDTO {
    private Long id;
    private String nameGr;
    private String nameEn;
    private Long population;
    private PrefectureCapitalDTO capital;
    private Set<OtaDTO> OTAs;
    private Double shapeArea;
}
