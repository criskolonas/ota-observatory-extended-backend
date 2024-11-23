package gr.alexc.otaobservatory.dto.prefecture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrefectureSimpleDTO {
    private Long id;
    private String nameGr;
    private String nameEn;
    private Long population;
    private PrefectureCapitalDTO capital;
    private Double shapeArea;
}
