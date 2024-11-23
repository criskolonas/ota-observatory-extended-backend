package gr.alexc.otaobservatory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GreeceAreaViewDTO {
    private Long id;
    private Long regionId;
    private Long prefectureId;
    private String name;
    private String type;

}
